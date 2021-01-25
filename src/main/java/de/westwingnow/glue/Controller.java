package de.westwingnow.glue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Controller {
	private Properties properties;
	private WebDriver webDriver;

	public Properties getProperties() {
		return properties;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setup() {
		setupWebDriver();
		setupProperties();
	}

	public void destroy() {
		destroyWebDriver();
		destroyProperties();
	}

	private void destroyWebDriver() {
		if (Objects.nonNull(webDriver)) {
			webDriver.quit();
		}
	}

	private void destroyProperties() {
		if (Objects.nonNull(properties)) {
			properties = new Properties();
		}
	}

	private void setupWebDriver() {
		System.setProperty("webdriver.chrome.driver", "/home/devlin/temp/selenium/drivers/chrome/chromedriver");
		webDriver = new ChromeDriver(getChrome());
		webDriver.manage().window().maximize();
	}

	private void setupProperties() {
		properties = new Properties();
		try (InputStream input = this.getClass().getResourceAsStream("/secrets/test.properties")) {
			// load a properties file
			properties.load(input);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	private ChromeOptions getChrome() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certificate-errors");
		return options;
	}
}
