
@tag
Feature: Purchase order from ecommerce site

	Background:
	Given I landed on ecommerce page
		
	
  @Regression
  Scenario Outline: Submitting order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to the cart
    And checkout product <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on the ConfirmationPage

    Examples: 
      | name           | password   | productName  |
      | nimsui@rsa.com | Baral#2024 | ZARA COAT 3  |

