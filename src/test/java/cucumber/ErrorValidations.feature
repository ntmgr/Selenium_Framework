
@tag
Feature: Error validation

  @ErrorValidation
  Scenario Outline: Incorrect email or password
    Given I landed on ecommerce page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." login error message is displayed

    Examples: 
      | name           | password  |
      | nimsui@rsa.com | Baral2024 |
