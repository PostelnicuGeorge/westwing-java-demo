package de.westwingnow.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class SeleniumBase {
	private WebDriver driver;
	private WebDriverWait wait;

	public SeleniumBase(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 15);
//		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(NullPointerException.class);
		wait.ignoring(WebDriverException.class);
	}

//	some methods go here

	protected WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	protected List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	protected WebElement getChildElement(WebElement parent, By child) {
		return parent.findElement(child);
	}

	protected List<WebElement> getChildElements(WebElement parent, By children) {
		return parent.findElements(children);
	}

	protected String getElementText(WebElement element) {
		return element.getText().trim();
	}

	protected void clickButton(WebElement button) {
		scrollToElement(button);
		button.click();
	}

	protected void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	protected boolean waitUntilNumberOfChildElementsToBe(WebElement element, By locator, Integer number,
	                                                     boolean throwException) {
		return untilCondition(SeleniumConditions.numberOfChildElementsToBe(element, locator, number),
				throwException);
	}

	protected boolean untilCondition(ExpectedCondition<Boolean> expectedCondition,
	                                      boolean throwException) {

		try {
			wait.until(expectedCondition);
		} catch (Exception e) {
			if (throwException) {
				throw e;
			} else {
				System.err.println("Condition: " + expectedCondition + " reached the maximum timeout: " + 15 + "[s]");
				System.err.println(e.getMessage());
			}
			return false;
		}

		return true;
	}

	protected boolean untilElement(ExpectedCondition<WebElement> expectedCondition,
	                                    boolean throwException) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
//		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(NullPointerException.class);
		wait.ignoring(WebDriverException.class);
		try {
			wait.until(expectedCondition);
		} catch (Exception e) {
			if (throwException) {
				throw e;
			} else {
				System.err.println("Condition: " + expectedCondition + " reached the maximum timeout: " + 15 + "[s]");
				System.err.println(e.getMessage());
			}
			return false;
		}

		return true;
	}
}
