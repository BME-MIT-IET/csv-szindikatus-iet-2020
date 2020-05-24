package cucumber.testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "C:\\Users\\MrGamble\\Downloads\\git\\csv-szindikatus-iet-2020\\features\\CSV2RDF.feature",
        glue = "cucumber\\stepDefinitions")
public class cucumberTestRunner {
}
