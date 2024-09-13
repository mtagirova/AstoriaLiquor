Feature: Search
@search
Scenario: Search for vodka
Given I am on the Astoria Liquor homepage
When I search for vodka
Then I should see search results for vodka

