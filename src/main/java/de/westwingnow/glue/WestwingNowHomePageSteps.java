package de.westwingnow.glue;

import de.westwingnow.models.WestwingNowHomePage;
import de.westwingnow.selenium.SeleniumHooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class WestwingNowHomePageSteps extends SeleniumHooks {
	private WestwingNowHomePage homePage;

	@Given("I am on the WestwingNow home page {string}")
	public void iAmOnTheWestwingNowHomePage(String arg0) {
		homePage = new WestwingNowHomePage(webDriver, properties);
	}

	@When("I click on {string}")
	public void iClickOn(String navigationName) {
		homePage.clickNavigation(navigationName);
	}
}
