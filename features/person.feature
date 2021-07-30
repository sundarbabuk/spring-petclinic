Feature: input validation

  Scenario: Empty name should not validate
    Given a person with empty first name
      And "Smith" as last name
     When person is validated
     Then invalid field must be "firstName
      And message must be ”must not be empty"