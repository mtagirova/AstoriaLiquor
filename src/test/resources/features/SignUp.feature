Feature: Create an Account

  Scenario: User creates an account successfully
    Given I am on the Astoria Liquor registration page
    When I enter valid account details
    And I submit the registration form
    Then I should see a confirmation message