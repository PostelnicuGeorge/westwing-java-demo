package de.westwingnow.models;

import de.westwingnow.selenium.SeleniumBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class WestwingNowWishlistPage extends SeleniumBase {

	private static final By SELF_LOCATOR = By.xpath("//div[@id='page']");
	private static final By WISH_LIST_ROOT_LOCATOR = By.xpath(".//div[@id='wishlistRoot']");
	private static final By WISH_LIST_ITEM_LOCATOR = By.xpath(".//ul/li[contains(@class, 'qaBlockListProduct')]");
	private static final By LOADER_LOCATOR = By.xpath(".//div[contains(@class, 'qaWishlistPageLoader')]");
	private static final By DELETE_LOCATOR = By.xpath(".//button[contains(@class, 'qaBlockListProduct__delete')]");
	private WebElement root;

	public WestwingNowWishlistPage(WebDriver driver) {
		super(driver);
		init();
	}

	private void init() {
		untilElement(ExpectedConditions.visibilityOfElementLocated(SELF_LOCATOR), true);
		WebElement self = getElement(SELF_LOCATOR);
		waitUntilNumberOfChildElementsToBe(self, WISH_LIST_ROOT_LOCATOR, 1, true);
		root = getChildElement(self, WISH_LIST_ROOT_LOCATOR);
		waitUntilNumberOfChildElementsToBe(root, LOADER_LOCATOR, 1, true);
		WebElement loader = getChildElement(root, LOADER_LOCATOR);
		waitUntilInvisibilityOfElement(loader, true);
	}

	public void delete() {
		delete(0);
	}

	public void delete(int number) {
		List<WebElement> wishListedItems = getChildElements(root, WISH_LIST_ITEM_LOCATOR);
		if (number > wishListedItems.size() || number < 0) {
			throw new RuntimeException("The number of wish list: " + number + " chosen to be deleted is out of bounds: "
					+ wishListedItems.size());
		}
		clickButton(getChildElement(wishListedItems.get(number), DELETE_LOCATOR));
	}
}
