package de.westwingnow;

import de.westwingnow.glue.Controller;
import de.westwingnow.models.WestwingNowHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class SimpleTest {
	public static void main(String[] args) {
		WebDriver webDriver = null;
		Properties properties = null;
		try {
			properties = initProperties();
			System.setProperty("webdriver.chrome.driver", "/home/devlin/temp/selenium/drivers/chrome/chromedriver");
			webDriver = new ChromeDriver(new Controller().getChrome());
			webDriver.manage().window().maximize();
//			webDriver.get("https://www.westwingnow.de");
			WestwingNowHomePage homePage = new WestwingNowHomePage(webDriver, properties);
			homePage.init("https://www.westwingnow.de");
			homePage.clickNavigation("Möbel");
			homePage.wishListFirstGenericProduct();
			System.out.println("ASDF");
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			if (Objects.nonNull(webDriver)) {
				webDriver.quit();
			}
		}
	}

	public static Properties initProperties() {
		Properties prop = new Properties();
		try (InputStream input = SimpleTest.class.getResourceAsStream("/secrets/test.properties")) {
			// load a properties file
			prop.load(input);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return prop;
	}
}
