// Copyright (c) 2014, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.complexible.common.csv;

import com.complexible.common.csv.generator.*;
import com.complexible.common.csv.logger.ProcessBehaviourLogger;
import com.complexible.common.csv.provider.RowNumberProvider;
import com.complexible.common.csv.provider.RowValueProvider;
import com.complexible.common.csv.provider.UUIDProvider;
import com.complexible.common.csv.provider.ValueProvider;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVParser;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.*;
import org.openrdf.rio.helpers.BasicParserSettings;
import org.openrdf.rio.helpers.RDFHandlerBase;


/**
 * Converts a CSV file to RDF based on a given template.
 *
 * @author Evren Sirin
 */
@Command(name = "convert", description = "Runs the conversion.")
public class CSV2RDF implements Runnable {

    /**
     * The charset used to read the input file.
     */
    private static final Charset INPUT_CHARSET = Charset.defaultCharset();

    /**
     * The charset used to write the output file.
     */
    public static final Charset OUTPUT_CHARSET = StandardCharsets.UTF_8;


    public static final ValueFactory FACTORY = ValueFactoryImpl.getInstance();
    public static final ProcessBehaviourLogger processLogger = new ProcessBehaviourLogger();

    @Option(name = "--no-header", arity = 0, description = "If csv file does not contain a header row")
    private static final boolean NOHEADER = false;

    @Option(name = { "-s", "--separator" }, description = "Separator character used in the csv file or ',' by default.")
    private final String separator = String.valueOf(CSVParser.DEFAULT_SEPARATOR);

    @Option(name = { "-q", "--quote" }, description = "Quote character used in the csv file or '\"' by default.")
    private final String quote = String.valueOf(CSVParser.DEFAULT_QUOTE_CHARACTER);

    @Option(name = { "-e", "--escape" }, description = "Escape character used in the csv file or '\\' by default.")
    private final String escape = String.valueOf(CSVParser.DEFAULT_ESCAPE_CHARACTER);

    @Arguments(required = true, description = "File arguments. The extension of template file and output file determines the RDF format that will be used for them (.ttl = Turtle, .nt = N-Triples, .rdf = RDF/XML)",
            title = "templateFile, csvFile, outputFile" )

    private List<String> files;
    private int inputRows = 0;
    private int outputTriples = 0;

    public void run() {
        Preconditions.checkArgument(files.size() >= 3, "Missing arguments");
        Preconditions.checkArgument(files.size() <= 3, "Too many arguments");

        final File templateFile = new File(files.get(0));
        final File inputFile = new File(files.get(1));
        final File outputFile =  new File(files.get(2));
        processLogger.logInfo("CSV to RDF conversion started...");
        processLogger.logInfo("Template: " + templateFile);
        processLogger.logInfo("Input   : " + inputFile);
        processLogger.logInfo("Output  : " + outputFile);
        
        try (Reader in = Files.newReader(inputFile, INPUT_CHARSET);
             CSVReader reader = new CSVReader(in, toChar(separator), toChar(quote), toChar(escape));
             Writer out = Files.newWriter(outputFile, OUTPUT_CHARSET)){
            String[] row = reader.readNext();

            Preconditions.checkNotNull(row, "Input file is empty!");

            final RDFFormat format = Rio.getParserFormatForFileName(outputFile.getName()).orElse(RDFFormat.TURTLE);
            final RDFWriter writer = Rio.createWriter(format, out);

            final Template template = new Template(Arrays.asList(row), templateFile, writer);

            if (NOHEADER) {
                template.generate(row, writer);
            }

            while ((row = reader.readNext()) != null) {
                template.generate(row, writer);
            }

            writer.endRDF();
            processLogger.logInfo(String.format("Converted %,d rows to %,d triples%n", inputRows, outputTriples));

        }
        catch (IOException e) {
            processLogger.logError("File was not found");
        }
        catch (NullPointerException e) {
            processLogger.logError("File was empty");
        }
        catch(final RDFHandlerException e)
        {
            processLogger.logError("RDFHandlerException occurred during run");
        }
    }

    private static char toChar(final String value) {
        Preconditions.checkArgument(value.length() == 1, "Expecting a single character but got %s", value);
        return value.charAt(0);
    }

    private class Template {
        private final List<StatementGenerator> stmts = Lists.newArrayList();
        private final List<ValueProvider> valueProviders = Lists.newArrayList();

        Template(final List<String> cols, final File templateFile, final RDFWriter writer) throws IOException {
            parseTemplate(cols, templateFile, writer);
        }

        private String insertPlaceholders(final List<String> cols, final File templateFile) throws IOException {
            final Pattern p = Pattern.compile("([\\$|\\#]\\{[^}]*\\})");

            final Matcher m = p.matcher(Files.toString(templateFile, INPUT_CHARSET));
            final StringBuffer sb = new StringBuffer();
            while (m.find()) {
                final String var = m.group(1);
                final String varName = var.substring(2, var.length() - 1);
                final ValueProvider valueProvider = valueProviderFor(varName, cols);
                Preconditions.checkArgument(valueProvider != null, "Invalid template variable", var);
                valueProvider.setHashed((var.charAt(0) == '#'));
                m.appendReplacement(sb, valueProvider.getPlaceholder());
                valueProviders.add(valueProvider);
            }
            m.appendTail(sb);

            return sb.toString();
        }
        private ParserConfig getParserConfig() {
            final ParserConfig config = new ParserConfig();
    
            final Set<RioSetting<?>> aNonFatalErrors = Sets.newHashSet(
                            BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES);
    
            config.setNonFatalErrors(aNonFatalErrors);
    
            config.set(BasicParserSettings.FAIL_ON_UNKNOWN_DATATYPES, false);
            config.set(BasicParserSettings.FAIL_ON_UNKNOWN_LANGUAGES, false);
            config.set(BasicParserSettings.VERIFY_DATATYPE_VALUES, false);
            config.set(BasicParserSettings.VERIFY_LANGUAGE_TAGS, false);
            config.set(BasicParserSettings.VERIFY_RELATIVE_URIS, false);
    
            return config;
        }

        private ValueProvider valueProviderFor(final String varName, final List<String> cols) {
            if (varName.equalsIgnoreCase("_ROW_")) {
                return new RowNumberProvider();
            }
            if (varName.equalsIgnoreCase("_UUID_")) {
                return new UUIDProvider();
            }
            
            int index = -1;            
            if (!NOHEADER) {
                index = cols.indexOf(varName);
            }
            else {
                try {
                    index = Integer.parseInt(varName);
                }
                catch (final NumberFormatException e) {
                    if (varName.length() == 1) {
                        final char c = Character.toUpperCase(varName.charAt(0));
                        if (c >= 'A' && c <= 'Z') {
                            index = c - 'A';
                        }
                    }
                }
            }
            return index == -1 ? null : new RowValueProvider(index);
        }

        private void parseTemplate(final List<String> cols, final File templateFile, final RDFWriter writer) throws IOException {
            final String templateStr = insertPlaceholders(cols, templateFile);

            final RDFFormat format = Rio.getParserFormatForFileName(templateFile.getName()).orElse(RDFFormat.TURTLE);
            final RDFParser parser = Rio.createParser(format);

            parser.setParserConfig(getParserConfig());
            parser.setRDFHandler(new RDFHandlerBase() {
                @SuppressWarnings("rawtypes")
                private final Map<Value, ValueGenerator> generators = Maps.newHashMap();

                @Override
                public void startRDF() {
                    writer.startRDF();
                }

                @Override
                public void handleNamespace(final String prefix, final String uri) {
                    writer.handleNamespace(prefix, uri);
                }

                @Override
                public void handleStatement(final Statement st) {
                    final ValueGenerator<Resource> subject = generatorFor(st.getSubject());
                    final ValueGenerator<URI> predicate = generatorFor(st.getPredicate());
                    final ValueGenerator<Value> object = generatorFor(st.getObject());
                    stmts.add(new StatementGenerator(subject, predicate, object));
                }

                @SuppressWarnings({ "unchecked"})
                private <V extends Value> ValueGenerator<V> generatorFor(final V value) {
                    ValueGenerator<V> generator = generators.get(value);
                    if (generator != null) {
                        return generator;
                    }
                    if (value instanceof BNode) {
                        generator = (ValueGenerator<V>) new BNodeGenerator();
                    }
                    else {
                        final String str = value.toString();
                        final ValueProvider[] providers = providersFor(str);
                        if (providers.length == 0) {
                            generator = new ConstantValueGenerator<>(value);
                        }
                        else if (value instanceof URI) {
                            generator = (ValueGenerator<V>) new TemplateURIGenerator(str, providers);
                        }
                        else {
                            final Literal literal = (Literal) value;
                            generator = (ValueGenerator<V>) new TemplateLiteralGenerator(literal, providers);
                        }
                    }
                    generators.put(value, generator);
                    return generator;
                }

                private ValueProvider[] providersFor(final String str) {
                    final List<ValueProvider> result = Lists.newArrayList();
                    for (final ValueProvider provider : valueProviders) {
                        if (str.contains(provider.getPlaceholder())) {
                            result.add(provider);
                        }  
                    }
                    return result.toArray(new ValueProvider[0]);
                }
            });

            parser.parse(new StringReader(templateStr), "urn:");
        }

        void generate(final String[] row, final RDFHandler handler) {
            inputRows++;
            for (final StatementGenerator stmt : stmts) {
                outputTriples++;
                handler.handleStatement(stmt.generate(inputRows, row));
            }
        }
    }

}
