package westwingnow;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/"}, glue = {"de.westwingnow.glue"})
public class JUnitCucumberMain {
}