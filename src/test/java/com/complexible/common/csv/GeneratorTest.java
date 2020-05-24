package com.complexible.common.csv;

import org.junit.Test;
import com.complexible.common.csv.generator.*;
import com.complexible.common.csv.provider.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openrdf.model.Value;
import com.complexible.common.csv.provider.ValueProvider;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.impl.LiteralImpl;

public class GeneratorTest {
    private final int rowIndex = 1;
    private final String[] array = new String[] { "Hello", "this", "is", "a", "test" };
    private ValueFactory FACTORY = ValueFactoryImpl.getInstance();

    /**
     * Tests the TemplateURIGenerator subclass of the TemplateValueGenerator
     */
    @Test
    public void TemplateURIGeneratorWithRowNumberProviderTest() {
        ValueProvider[] providers = new ValueProvider[1];
        RowNumberProvider rowNumberProvider = new RowNumberProvider();
        String placeHolder = rowNumberProvider.getPlaceholder();
        String template = "http://" + placeHolder + "hello" + placeHolder + placeHolder + ".com";

        providers[0] = rowNumberProvider;

        TemplateURIGenerator templateURIGenerator = new TemplateURIGenerator(template, providers);
        URI uri = templateURIGenerator.generate(rowIndex, array);

        String expectedString = "http://1hello11.com";
        URI expectedURI = FACTORY.createURI(expectedString);

        assertEquals(true, expectedURI.equals(uri));
    }

    /**
     * Tests if the TemplateURIGenerator functions well with more than one given
     * provider
     */
    @Test
    public void TemplateURIGeneratorWithMoreProvidersTest() {
        ValueProvider[] providers = new ValueProvider[2];
        RowNumberProvider rowNumberProvider = new RowNumberProvider();
        RowValueProvider rowValueProvider = new RowValueProvider(1);
        String rowNumberProviderPlaceholder = rowNumberProvider.getPlaceholder();
        String rowValueProviderPlaceholder = rowValueProvider.getPlaceholder();

        providers[0] = rowNumberProvider;
        providers[1] = rowValueProvider;

        String template = "http://" + rowNumberProviderPlaceholder + rowValueProviderPlaceholder + "hello"
                + rowNumberProviderPlaceholder + ".com";
        String expectedString = "http://1thishello1.com";

        TemplateURIGenerator templateURIGenerator = new TemplateURIGenerator(template, providers);
        URI uri = templateURIGenerator.generate(rowIndex, array);
        URI expectedURI = FACTORY.createURI(expectedString);

        assertEquals(true, expectedURI.equals(uri));
    }

    /**
     * Tests the TemplateLiteralGenerator subclass of the TemplateValueGenerator
     */
    @Test
    public void TemplateLiteralGeneratorTest() {
        URI uri = FACTORY.createURI("http://testuri.com");
        Literal literal = new LiteralImpl("testLabel", uri);
        String template = literal.getLabel();
        ValueProvider[] providers = new ValueProvider[1];
        RowNumberProvider rowNumberProvider = new RowNumberProvider();

        providers[0] = rowNumberProvider;

        String expected = template.replace(rowNumberProvider.getPlaceholder(), "1");
        TemplateLiteralGenerator templateLiteralGenerator = new TemplateLiteralGenerator(literal, providers);

        Literal result = templateLiteralGenerator.generate(rowIndex, array);
        Literal expectedLiteral;

        if (literal.getDatatype() != null) {
            expectedLiteral = FACTORY.createLiteral(expected, literal.getDatatype());
        } else if (literal.getLanguage().orElse(null) != null) {
            expectedLiteral = FACTORY.createLiteral(expected, literal.getLanguage().orElse(null));
        } else {
            expectedLiteral = FACTORY.createLiteral(expected);
        }

        assertEquals(true, expectedLiteral.equals(result));
    }

    /**
     * Tests the TemplateLiteralGenerator class, if it gets more provider and an URI
     * with placeholders
     */
    @Test
    public void TemplateLiteralGeneratorWithMoreProvidersTest() {
        ValueProvider[] providers = new ValueProvider[2];
        RowNumberProvider rowNumberProvider = new RowNumberProvider();
        RowValueProvider rowValueProvider = new RowValueProvider(1);

        URI uri = FACTORY.createURI(
                "http://testuri" + rowNumberProvider.getPlaceholder() + rowValueProvider.getPlaceholder() + ".com");
        Literal literal = new LiteralImpl("testLabel", uri);
        String template = literal.getLabel();
        String expected = template.replace(rowNumberProvider.getPlaceholder(), "1");

        providers[0] = rowNumberProvider;
        providers[1] = rowValueProvider;

        TemplateLiteralGenerator templateLiteralGenerator = new TemplateLiteralGenerator(literal, providers);
        Literal result = templateLiteralGenerator.generate(rowIndex, array);
        Literal expectedLiteral;

        if (literal.getDatatype() != null) {
            expectedLiteral = FACTORY.createLiteral(expected, literal.getDatatype());
        } else if (literal.getLanguage().orElse(null) != null) {
            expectedLiteral = FACTORY.createLiteral(expected, literal.getLanguage().orElse(null));
        } else {
            expectedLiteral = FACTORY.createLiteral(expected);
        }

        assertEquals(true, expectedLiteral.equals(result));
    }

    /**
     * Tests the ConstantValueGenerator class
     */

    @Test
    public void ConstantValueGeneratorTest() {
        URI uri = FACTORY.createURI("http://1thishello1.com");
        ConstantValueGenerator<URI> constantValueGenerator = new ConstantValueGenerator<URI>(uri);
        Value generatedUri = constantValueGenerator.generate(rowIndex, array);

        assertEquals(true, generatedUri.equals(uri));
    }

    /**
     * Tests the BlankNodeGenerator generator method with same row index the two
     * node must be same
     */
    @Test
    public void BNodeGeneratorOnSameRowTest() {
        BNodeGenerator bNodeGenerator = new BNodeGenerator();
        final String[] rows = { "Second", "rows" };

        BNode firstGeneratedNode = bNodeGenerator.generate(rowIndex, array);
        BNode secondGeneratedNode = bNodeGenerator.generate(rowIndex, rows);

        assertEquals(true, firstGeneratedNode.equals(secondGeneratedNode));
    }

    /**
     * Tests the BlankNodeGenerator generator method with same row index the two
     * node must be different
     */
    @Test
    public void BNodeGeneratorOnDifferentRowTest() {
        BNodeGenerator bNodeGenerator = new BNodeGenerator();
        int firstRowIndex = 1;
        int secondRowIndex = 2;
        String[] firstRows = { "First", "rows" };
        String[] secondRows = { "Second", "rows" };

        BNode firstGeneratedNode = bNodeGenerator.generate(firstRowIndex, firstRows);
        BNode secondGeneratedNode = bNodeGenerator.generate(secondRowIndex, secondRows);

        assertEquals(false, firstGeneratedNode.equals(secondGeneratedNode));
    }

}