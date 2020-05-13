package cucumber.stepDefinitions;

import com.complexible.common.csv.CSV2RDF;
import com.complexible.common.csv.generator.BNodeGenerator;
import com.complexible.common.csv.generator.TemplateLiteralGenerator;
import com.complexible.common.csv.provider.RowNumberProvider;
import com.complexible.common.csv.provider.RowValueProvider;
import com.complexible.common.csv.provider.ValueProvider;
import io.cucumber.java.en.*;
import org.apache.velocity.Template;
import org.junit.jupiter.api.Assertions;
import org.openrdf.model.BNode;
import org.openrdf.model.Literal;

import java.util.List;

public class csv2rdf_stepDefinitions {

    CSV2RDF converter;
    List<String> files;
    boolean result;
    BNodeGenerator bNodeGeneratorenerator;
    TemplateLiteralGenerator templateLiteralGenerator;
    @Given("Have a template: {string}, CSV file: {string} and output file: {string}")
    public void have_a_CSV_file(String templateName, String csvName, String outputName) {
        if(!files.isEmpty())
            files.clear();
        files.add(templateName); files.add(csvName); files.add(outputName);
    }

    @Given("Create CSVRDF instance")
    public void create_CSVRDF_instance() {
         converter=new CSV2RDF();
    }

    @When("Empty file")
    public void empty_file() {
        try{
            converter.run();
        }catch (NullPointerException e)
        {
            result=true;
        }
        result=false;
    }

    @When("All Precondition fulfilled")
    public void all_Precondition_fulfilled() {
        try{
            converter.run();
        }catch (NullPointerException e)
        {
            result=false;
        }
        result=true;
    }

    @Then("Precondition not fulfilled")
    public void precondition_not_fulfilled() {
        Assertions.assertEquals(true,result);
    }

    @Then("CSV is converted into RDF")
    public void csv_is_converted_into_RDF() {
        Assertions.assertEquals(true,result);
    }
    @Given("Create BNode instance")
    public void createBNodeGenerator() {
       bNodeGeneratorenerator=new BNodeGenerator();
    }
    @When("Generate BNode with rowIndex {int} and string row")
    public void generateBNode(int rowIndex) {
       String[] row=null;
       BNode value = bNodeGeneratorenerator.generate(rowIndex,row);
       if(value == null)
       {
           result=false;
       }
       result = true;
    }
    @Then("Check if generation was successfull")
    public void successfulBNodeGeneration() {
        Assertions.assertEquals(true,result);
    }
    @Given("Create TemplateLiteral instance")
    public void createTemplateLiteralGenerator() {
        Literal literal=null;
        ValueProvider[] provider = null;
        templateLiteralGenerator=new TemplateLiteralGenerator( literal,  provider);
    }
    @When("Generate TemplateLiteral with rowIndex {int} and string row")
    public void generateTemplateLiteral(int rowIndex) {
        String[] row= null;
        Literal value = templateLiteralGenerator.generate(rowIndex,row);
        if(value == null)
        {
            result=false;
        }
        result = true;
    }
}
