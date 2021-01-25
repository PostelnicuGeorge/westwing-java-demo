package westwingnow.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class SeleniumConditions {
	public static ExpectedCondition<Boolean> numberOfChildElementsToBe(WebElement element,
	                                                                   By locator, Integer number) {
		return new ExpectedCondition<Boolean>() {
			private Integer currentNumber = 0;

			public Boolean apply(WebDriver webDriver) {
				List<WebElement> elements = element.findElements(locator);
				this.currentNumber = elements.size();
				return this.currentNumber.equals(number);
			}

			public String toString() {
				return String.format("number of elements found by %s to be \"%s\". Current number: \"%s\"", locator,
						number, this.currentNumber);
			}
		};
	}
}
