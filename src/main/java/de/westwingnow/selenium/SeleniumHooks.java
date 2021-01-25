package de.westwingnow.selenium;

import de.westwingnow.SimpleTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.Objects;
import java.util.Properties;

public abstract class SeleniumHooks {
	protected WebDriver webDriver;
	protected Properties properties;

	@Before
	public void initWebDriver() {
		webDriver = new ChromeDriver(getChrome());
		properties = SimpleTest.initProperties();
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
			byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "name");
		}
		if (Objects.nonNull(webDriver)) {
			webDriver.quit();
		}
	}
}
