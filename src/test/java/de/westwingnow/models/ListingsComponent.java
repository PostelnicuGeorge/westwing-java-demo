package de.westwingnow.models;

import de.westwingnow.selenium.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.regex.Pattern;

public class ListingsComponent extends SeleniumBase {

	private static final By SELF_LOCATOR = By.xpath(".//div[contains(@class, 'ListingPageElements__ListingPageContentWrapper')]");
	private static final By TITLE_LOCATOR = By.xpath(".//h1[contains(@class, 'RegularTitle__StyledPageTitle')]");
	private static final By SUBTITLE_LOCATOR = By.xpath(".//div[contains(@class, 'RegularTitle__StyledProductCount')]");
	private static final By GENERIC_PRODUCT_LOCATOR = By.xpath(".//div[@data-testid='generic-product']");
	private static final By WISH_LIST_LOCATOR = By.xpath(".//div[@data-testid='wishlist-icon']");
	private static final By WISH_LIST_SELECTED_ICON_LOCATOR = By.xpath(".//*[name()='svg' and @data-is-selected='true']");
	private final WebElement parent;
	private WebElement self;
	private final String name;
	private final String href;

	public ListingsComponent(WebDriver driver, WebElement parent, String name, String href) {
		super(driver);
		this.parent = parent;
		this.name = name;
		this.href = href;
		init();
	}

	private void init() {
		untilCondition(ExpectedConditions.urlContains(href), true);
		waitUntilNumberOfChildElementsToBe(parent, SELF_LOCATOR, 1, true);
		self = getChildElement(parent, SELF_LOCATOR);
		waitUntilNumberOfChildElementsToBe(self, SUBTITLE_LOCATOR, 1, true);
		waitUntilNumberOfChildElementsToBe(self, TITLE_LOCATOR, 1, true);
		waitUntilTextMatches(SUBTITLE_LOCATOR, Pattern.compile(".* Produkte"), true);
		waitUntilTextMatches(TITLE_LOCATOR, Pattern.compile(".*" + name + ".*"), true);
	}

	private List<WebElement> getGenericProducts() {
		return getChildElements(self, GENERIC_PRODUCT_LOCATOR);
	}

	public void wishListFirstGenericProduct() {
		wishListGenericProduct(0);
	}

	public void wishListGenericProduct(int number) {
		List<WebElement> genericProducts = getGenericProducts();
		if (number < 0 || number > genericProducts.size()) {
			throw new RuntimeException("Wish list number: " + number + " is out of bounds: " + genericProducts.size());
		}
		clickButton(getChildElement(genericProducts.get(number), WISH_LIST_LOCATOR));
	}

	public void waitUntilFirstGenericProductIsWishListed() {
		waitUntilNumberOfChildElementsToBe(self, WISH_LIST_SELECTED_ICON_LOCATOR, 1, true);
	}
}
