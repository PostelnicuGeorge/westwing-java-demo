package de.westwingnow.glue;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Controller {
	private Properties properties;
	private WebDriver webDriver;

	public Properties getProperties() {
		return properties;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setup() throws MalformedURLException {
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

	private void setupWebDriver() throws MalformedURLException {
		String browser = System.getProperty("browser");
		String gridUrl = System.getProperty("gridUrl");
		Capabilities capabilities;
		switch (browser) {
			case "chrome":
			default:
				capabilities = getChrome();
				break;
			case "firefox":
				capabilities = getFirefox();
				break;
		}
		if (Objects.nonNull(gridUrl)) {
			if (gridUrl.contains("http")) {
				webDriver = new RemoteWebDriver(new URL(gridUrl), capabilities);
			} else {
				switch (browser) {
					case "chrome":
					default:
						System.setProperty("webdriver.chrome.driver", gridUrl);
						webDriver = new ChromeDriver((ChromeOptions) getChrome());
						break;
					case "firefox":
						System.setProperty("webdriver.gecko.driver", gridUrl);
						webDriver = new FirefoxDriver((FirefoxOptions) getFirefox());
				}
			}
		} else {
			throw new RuntimeException("gridUrl mustn't be NULL!");
		}

		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		webDriver.manage().timeouts().setScriptTimeout(360, TimeUnit.SECONDS);
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

	public Capabilities getChrome() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certificate-errors");
		return options;
	}

	private Capabilities getFirefox() {
		FirefoxOptions options = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("ec.lock.homepage", false);
		profile.setPreference("browser.startup.homepage", "about:blank");
		options.setProfile(profile);
		options.setAcceptInsecureCerts(true);
		return options;
	}
}
