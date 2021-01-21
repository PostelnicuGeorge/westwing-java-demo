package de.westwingnow.models;

import de.westwingnow.selenium.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class WestwingNowHomePage extends SeleniumBase {

	private static final By SELF_LOCATOR = By.xpath("//div[@class='page']");
	private static final By COOKIES_ACCEPT_BUTTON_LOCATOR = By.xpath("//button[@id='onetrust-accept-btn-handler']");
	private static final By HEADER_LOCATOR = By.xpath(".//div[@id='wwOneHeader']");
	private static final By APP_CONTENT_LOCATOR = By.xpath(".//div[contains(@class, 'App__StyledContentWrapper')]");
	private static final By NAV_BUTTONS_LOCATOR = By.xpath(".//nav//li/a");
	private WebElement header;
	private WebElement appContent;
	private List<WebElement> navButtons = new ArrayList<>();
	private ListingsComponent listingsComponent;

	public WestwingNowHomePage(WebDriver driver) {
		super(driver);
		init();
	}

	private void init() {
		untilElement(ExpectedConditions.visibilityOfElementLocated(SELF_LOCATOR), true);
		WebElement self = getElement(SELF_LOCATOR);
		waitUntilNumberOfChildElementsToBe(self, HEADER_LOCATOR, 1, true);
		untilElement(ExpectedConditions.visibilityOfElementLocated(COOKIES_ACCEPT_BUTTON_LOCATOR), true);
		clickButton(getElement(COOKIES_ACCEPT_BUTTON_LOCATOR));

		waitUntilNumberOfChildElementsToBe(self, APP_CONTENT_LOCATOR, 1, true);
		header = getChildElement(self, HEADER_LOCATOR);
		appContent = getChildElement(self, APP_CONTENT_LOCATOR);
	}

	public void clickNavigation(String navigationName) {
		if (navButtons.isEmpty()) {
			navButtons = getChildElements(header, NAV_BUTTONS_LOCATOR);
		}

		for (WebElement navigationButton : navButtons) {
			String buttonName = getElementText(navigationButton);
			if (buttonName.equalsIgnoreCase(navigationName)) {
				String href = getElementAttributeValue(navigationButton, "href");
				clickButton(navigationButton);
				listingsComponent = new ListingsComponent(driver, appContent, buttonName, href);
				return;
			}
		}

		throw new NoSuchElementException("Haven't found button with name: " + navigationName);
	}
}
