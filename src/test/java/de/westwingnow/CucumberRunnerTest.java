package de.westwingnow;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/"},
		glue = {"de.westwingnow.glue"},
		plugin = {"pretty", "html:target/index.html"},
		snippets = CAMELCASE)
public class CucumberRunnerTest {
}
