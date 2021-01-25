package de.westwingnow.models;

import de.westwingnow.selenium.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class WestwingNowHomePage extends SeleniumBase {

	private static final By SELF_LOCATOR = By.xpath("//div[@class='pageContainer']");
	private static final By COOKIES_ACCEPT_BUTTON_LOCATOR = By.xpath("//button[@id='onetrust-accept-btn-handler']");
	private static final By POPUP_LOCATOR = By.xpath(".//div[@data-testid='popup-backdrop']");
	private static final By HEADER_LOCATOR = By.xpath(".//div[@id='wwOneHeader']");
	private static final By APP_CONTENT_LOCATOR = By.xpath(".//div[contains(@class, 'App__StyledContentWrapper')]");
	private static final By NAV_BUTTONS_LOCATOR = By.xpath(".//nav//li/a");
	private static final By MY_ACCOUNT_LOCATOR = By.xpath(".//div[@data-testid='one-header-icon-my-account']");
	private final Properties properties;
	private WebElement self;
	private WebElement header;
	private WebElement appContent;
	private List<WebElement> navButtons = new ArrayList<>();
	private ListingsComponent listingsComponent;
	private boolean isLoggedIn;
	private String categoryResource;
	private String categoryName;

	public WestwingNowHomePage(WebDriver driver, Properties properties) {
		super(driver);
		this.properties = properties;
		isLoggedIn = false;
		init();
	}

	private void init() {
		untilElement(ExpectedConditions.visibilityOfElementLocated(SELF_LOCATOR), true);
		self = getElement(SELF_LOCATOR);
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
			categoryName = getElementText(navigationButton);
			if (categoryName.equalsIgnoreCase(navigationName)) {
				categoryResource = getElementAttributeValue(navigationButton, "href");
				clickButton(navigationButton);
				dismissPromoPopup();
				listingsComponent = new ListingsComponent(driver, appContent, categoryName, categoryResource);
				return;
			}
		}

		throw new NoSuchElementException("Haven't found button with name: " + navigationName);
	}

	public void wishListFirstGenericProduct() {
		if (Objects.nonNull(listingsComponent)) {
			listingsComponent.wishListFirstGenericProduct();
			if (!isLoggedIn) {
				if (hasPopupAppeared(false)) {
					RegistrationPopup popup = new RegistrationPopup(driver, getChildElement(self, POPUP_LOCATOR), properties);
					popup.login();
					waitUntilTextMatches(MY_ACCOUNT_LOCATOR, Pattern.compile(".*" +
							properties.getProperty("test.user.first_name") + ".*"), true);
					isLoggedIn = true;
				}
			}
			listingsComponent = new ListingsComponent(driver, appContent, categoryName, categoryResource);
			listingsComponent.waitUntilFirstGenericProductIsWishListed();
		} else {
			throw new NoSuchElementException("Haven't clicked on any category!");
		}
	}

	private void dismissPromoPopup() {
		if (hasPopupAppeared(false)) {
			RegistrationPopup popup = new RegistrationPopup(driver, getChildElement(self, POPUP_LOCATOR), properties);
			popup.dismiss();
			hasPopupAppeared(true);
			popup = new RegistrationPopup(driver, getChildElement(self, POPUP_LOCATOR), properties);
			popup.dismiss();
		}
	}

	private boolean hasPopupAppeared(boolean throwException) {
		return waitUntilNumberOfChildElementsToBe(self, WestwingNowHomePage.POPUP_LOCATOR, 1, throwException);
	}
}
