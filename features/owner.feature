Feature: Maintain Owners

  Scenario: list all owners
    When I search for all pet owners
    Then a list of all owners is displayed