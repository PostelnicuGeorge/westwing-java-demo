package westwingnow.models;

import westwingnow.selenium.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.regex.Pattern;

public class HeaderIconsComponent extends SeleniumBase {
	private final WebElement self;
	private static final By ACCOUNT_LOCATOR = By.xpath(".//div[@data-testid='one-header-icon-my-account']");
	private static final By WISH_LIST_LOCATOR = By.xpath(".//div[@data-testid='wishlist-bubble']");
	private static final By WISH_LIST_COUNT_LOCATOR = By.xpath(".//span[@data-testid='wishlist-counter']");

	public HeaderIconsComponent(WebDriver driver, WebElement self) {
		super(driver);
		this.self = self;
		init();
	}

	private void init() {
		waitUntilNumberOfChildElementsToBe(self, ACCOUNT_LOCATOR, 1, true);
		waitUntilNumberOfChildElementsToBe(self, WISH_LIST_LOCATOR, 1, true);
	}

	public void waitUntilItemsAreWishListed(String itemsCount) {
		waitUntilTextMatches(WISH_LIST_COUNT_LOCATOR, Pattern.compile(".*" + itemsCount + ".*"), true);
	}
}
