package cucumber.stepDefinitions;

import com.complexible.common.csv.CSV2RDF;
import com.complexible.common.csv.generator.BNodeGenerator;
import com.complexible.common.csv.generator.TemplateLiteralGenerator;
import com.complexible.common.csv.logger.ProcessBehaviourLogger;
import io.airlift.command.Cli;
import io.airlift.command.Help;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openrdf.model.BNode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class csv2rdf_stepDefinitions {

    CSV2RDF converter;
    List<String> files;
    String outputExpected;
    String output;
    BufferedReader outputInspectorReader;
    File outputExpectedFile;
    boolean result;
    ByteArrayOutputStream byteArrayOutputStream;
    StreamHandler streamHandler;
    BNodeGenerator bNodeGeneratorenerator;
    TemplateLiteralGenerator templateLiteralGenerator;

    public String getLog() {
        streamHandler.flush();
        String error = byteArrayOutputStream.toString();
        return error;
    }

    @Given("Have a template: {string}, CSV file: {string} and output file: {string}")
    public void have_a_CSV_file(String templateName, String csvName, String outputName) {
        files = new ArrayList<String>();
        byteArrayOutputStream = new ByteArrayOutputStream();
        streamHandler = new StreamHandler(byteArrayOutputStream, new SimpleFormatter());
        ProcessBehaviourLogger.addHandler(streamHandler);
        String template = "examples/cars/" + templateName + ".ttl";
        String csv = "examples/cars/" + csvName + ".csv";
        String output = "examples/cars/" + outputName + ".ttl";
        files.add("convert");
        files.add(template);
        files.add(csv);
        files.add(output);
    }

    @Given("Create CSVRDF instance")
    public void create_CSVRDF_instance() {
        converter = new CSV2RDF();
    }

    @When("Running the conversion, file was empty")
    public void empty_file() {
        result = false;
        String[] args = new String[files.size()];
        args = files.toArray(args);
        Cli.<Runnable>builder("csv2rdf").withDescription("Converts a CSV file to RDF based on a given template")
                .withDefaultCommand(CSV2RDF.class).withCommand(CSV2RDF.class).withCommand(Help.class)
                .build().parse(args).run();
        if (getLog().contains("File was empty")) {
            result = true;
        }


    }

    @When("All Precondition fulfilled")
    public void all_Precondition_fulfilled() {
        result = false;
        String[] args = new String[files.size()];
        args = files.toArray(args);
        Cli.<Runnable>builder("csv2rdf").withDescription("Converts a CSV file to RDF based on a given template")
                .withDefaultCommand(CSV2RDF.class).withCommand(CSV2RDF.class).withCommand(Help.class)
                .build().parse(files).run();
        if (getLog().contains("Converted")) {
            result = true;
        }
    }


    @When ("Running the conversion, file was not found")
    public void file_was_not_found(){
        result = false;
        String[] args = new String[files.size()];
        args = files.toArray(args);
        Cli.<Runnable>builder("csv2rdf").withDescription("Converts a CSV file to RDF based on a given template")
                .withDefaultCommand(CSV2RDF.class).withCommand(CSV2RDF.class).withCommand(Help.class)
                .build().parse(args).run();
        if (getLog().contains("File was not found")) {
            result = true;
        }
    }

    @Then("Precondition not fulfilled")
    public void precondition_not_fulfilled() {
        Assertions.assertEquals(true, result);
    }

    @Then("CSV is converted into RDF")
    public void csv_is_converted_into_RDF() {
        Assertions.assertEquals(true, result);
    }

    @Given("Create BNode instance")
    public void createBNodeGenerator() {
        bNodeGeneratorenerator = new BNodeGenerator();
    }

    @When("Generate BNode with rowIndex {int} and string row")
    public void generateBNode(int rowIndex) {
        String[] row = null;
        BNode value = bNodeGeneratorenerator.generate(rowIndex, row);
        if (value == null) {
            result = false;
        }
        result = true;
    }

    @Then("Check if generation was successfull")
    public void successfulBNodeGeneration() {
        Assertions.assertEquals(true, result);
    }
    @Given("Run the conversion")
    public void running_conversion() {
        String[] args = new String[files.size()];
        outputExpectedFile = new File("examples\\cars\\carsExpected.txt");
        try {
            outputInspectorReader = new BufferedReader(new FileReader(outputExpectedFile));
            String line;
            while ((line=outputInspectorReader.readLine())!=null){
                outputExpected+=line;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        args = files.toArray(args);
        Cli.<Runnable>builder("csv2rdf").withDescription("Converts a CSV file to RDF based on a given template")
                .withDefaultCommand(CSV2RDF.class).withCommand(CSV2RDF.class).withCommand(Help.class)
                .build().parse(args).run();

    }
    @When("Result is equal to the expected")
    public void generateBNode() {
        result=false;
        outputExpectedFile = new File("examples\\cars\\cars.ttl");
        try {
            outputInspectorReader = new BufferedReader(new FileReader(outputExpectedFile));
            String line;
            while ((line=outputInspectorReader.readLine())!=null){
                output+=line;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile(outputExpected);
        Matcher matcher = pattern.matcher(output);
        if(matcher.find()){
            result=true;
        }
    }

}
