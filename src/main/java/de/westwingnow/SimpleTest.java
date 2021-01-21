package de.westwingnow;

import de.westwingnow.models.WestwingNowHomePage;
import de.westwingnow.selenium.SeleniumHooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SimpleTest {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/home/devlin/temp/selenium/drivers/chrome/chromedriver");
		WebDriver webDriver = new ChromeDriver(SeleniumHooks.getChrome());
		webDriver.get("https://www.westwingnow.de");
		WestwingNowHomePage homePage = new WestwingNowHomePage(webDriver);
		homePage.clickNavigation("Möbel");
	}
}
