package de.westwingnow.models;

import de.westwingnow.selenium.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPopup extends SeleniumBase {

	private static final String POPUP_TYPE_ATTRIBUTE = "data-identifier";
	private static final String POPUP_PROMO_VALUE = "SHOP_TO_CLUB_REGISTRATION_SINGLE_STEP_OVERLAY";
	private static final String POPUP_LOGIN_VALUE = "LOGIN_AND_REGISTER";
	private static final By SWITCH_TO_LOGIN_LOCATOR = By.xpath(".//button[@data-testid='login-button']");
	private static final By POPUP_PROMO_LOCATOR = By.xpath(".//div[@data-identifier='SHOP_TO_CLUB_REGISTRATION_SINGLE_STEP_OVERLAY']");
	private static final By DISMISS_POPUP_LOCATOR = By.xpath(".//button[@data-identifier='LOGIN_AND_REGISTER']");
	private static final By EMAIL_LOCATOR = By.xpath(".//input[@data-testid='long-register-email-field']");
	private static final By PASS_LOCATOR = By.xpath(".//input[@data-testid='long-register-password-field']");
	private static final By LOGIN_LOCATOR = By.xpath(".//button[@data-testid='login_reg_submit_btn']");
	private final WebElement backdrop;

	public RegistrationPopup(WebDriver driver, WebElement backdrop) {
		super(driver);
		this.backdrop = backdrop;
	}

	public void login() {

	}

	public void dismiss() {
		String dataIdentifier = getElementAttributeValue(backdrop, POPUP_TYPE_ATTRIBUTE);
		switch (dataIdentifier) {
			default:
			case POPUP_PROMO_VALUE:
				clickButton(getElement(SWITCH_TO_LOGIN_LOCATOR));
				waitUntilInvisibilityOfElement(backdrop, true);
				break;
			case POPUP_LOGIN_VALUE:
				clickButton(getElement(DISMISS_POPUP_LOCATOR));
				waitUntilInvisibilityOfElement(backdrop, true);
				break;
		}
	}
}
