package de.westwingnow;

import de.westwingnow.models.WestwingNowHomePage;
import de.westwingnow.selenium.SeleniumHooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

public class SimpleTest {

	public static void main(String[] args) {
		WebDriver webDriver = null;
		try {
			System.setProperty("webdriver.chrome.driver", "/home/devlin/temp/selenium/drivers/chrome/chromedriver");
			webDriver = new ChromeDriver(SeleniumHooks.getChrome());
			webDriver.manage().window().maximize();
			webDriver.get("https://www.westwingnow.de");
			WestwingNowHomePage homePage = new WestwingNowHomePage(webDriver);
			homePage.clickNavigation("Möbel");
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			if (Objects.nonNull(webDriver)) {
				webDriver.quit();
			}
		}
	}
}
