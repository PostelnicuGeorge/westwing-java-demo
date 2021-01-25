package de.westwingnow.glue;

import de.westwingnow.models.WestwingNowHomePage;
import de.westwingnow.models.WestwingNowWishlistPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class WestwingNowHomePageSteps {
	private WestwingNowHomePage homePage;
	private WestwingNowWishlistPage wishListPage;
	private final WebDriver webDriver;
	private final Properties properties;

	public WestwingNowHomePageSteps(Controller controller) {
		this.webDriver = controller.getWebDriver();
		this.properties = controller.getProperties();
		this.homePage = new WestwingNowHomePage(webDriver, properties);
	}

	@Given("I am on the WestwingNow home page {string}")
	public void iAmOnTheWestwingNowHomePage(String url) {
		homePage.init(url);
	}

	@When("I click on {string}")
	public void iClickOn(String navigationName) {
		homePage.clickNavigation(navigationName);
	}

	@Then("I should see product listing page with a list of products")
	public void iShouldSeeProductListingPageWithAListOfProducts() {
		homePage.initProductListing();
	}


	@When("I click on wish list icon of the first found product")
	public void iClickOnWishListIconOfTheFirstFoundProduct() {
		homePage.wishListFirstGenericProduct();
	}

	@Then("I should see the login or registration overlay")
	public void iShouldSeeTheLoginOrRegistrationOverlay() {
		homePage.loginOverlayHasAppeared();
	}

	@When("I switch to login form of the overlay and I log in with {string} credentials")
	public void iSwitchToLoginFormOfTheOverlayAndILogInWithCredentials(String userName) {
		homePage.login(userName);
	}

	@Then("the product should be added to the wish list")
	public void theProductShouldBeAddedToTheWishList() {
		homePage.waitUntilFirstGenericProductIsWishListed();
	}

	@And("I go to the wish list page ​{string}")
	public void iGoToTheWishListPage​(String url) {
		webDriver.get(url);
		wishListPage = new WestwingNowWishlistPage(webDriver);
	}

	@And("I delete the first product from my wish list")
	public void iDeleteTheFirstProductFromMyWishList() {
		wishListPage.delete();
	}
}
