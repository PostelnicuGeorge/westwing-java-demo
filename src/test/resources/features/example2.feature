Feature: Task-2

  Scenario: Click on wish list from first generic product in Möbel then login and remove item from wish list
    Given I am on the WestwingNow home page "https://www.westwingnow.de​"
    When I click on "Möbel"
    Then I should see product listing page with a list of products
    When I click on wish list icon of the first found product
    Then I should see the login or registration overlay
    When I switch to login form of the overlay and I log in with "postelnicu.george@gmail.com" credentials
    Then the product should be added to the wish list
    And I go to the wish list page ​"https://www.westwingnow.de/customer/wishlist/index/​"
    And I delete the first product from my wish list