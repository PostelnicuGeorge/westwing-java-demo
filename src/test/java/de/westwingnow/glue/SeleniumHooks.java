package de.westwingnow.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.net.MalformedURLException;
import java.util.Objects;

public class SeleniumHooks {
	private final Controller controller;

	public SeleniumHooks(Controller controller) {
		this.controller = controller;
	}

	@Before
	public void setup() throws MalformedURLException {
		controller.setup();
	}

	@After
	public void destroy(Scenario scenario) {
		if (scenario.isFailed()) {
			if (Objects.nonNull(controller.getWebDriver())) {
				byte[] screenshot = ((TakesScreenshot) controller.getWebDriver()).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", "name");
			}
		}
		controller.destroy();
	}
}
