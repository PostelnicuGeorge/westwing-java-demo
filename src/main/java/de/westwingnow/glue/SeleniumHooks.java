package de.westwingnow.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumHooks {
	private final Controller controller;

	public SeleniumHooks(Controller controller) {
		this.controller = controller;
	}

	@Before
	public void setup() {
		controller.setup();
	}

	public static ChromeOptions getChrome() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certificate-errors");
		return options;
	}

	private static FirefoxOptions getFirefox() {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("ec.lock.homepage", false);
		profile.setPreference("browser.startup.homepage", "about:blank");
		options.setProfile(profile);
		options.setAcceptInsecureCerts(true);
		return options;
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
