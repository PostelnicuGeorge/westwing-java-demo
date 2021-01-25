package westwingnow.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class SeleniumHooks {
	private final Controller controller;

	public SeleniumHooks(Controller controller) {
		this.controller = controller;
	}

	@Before
	public void setup() {
		controller.setup();
	}

	@After
	public void destroy(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) controller.getWebDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "name");
		}
		controller.destroy();
	}
}
