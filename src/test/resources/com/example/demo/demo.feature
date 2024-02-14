#Feature: Creating an Actor
#  Scenario: Creating actor with first and last name
#    Given the Actor has values for first name and last name
#    When I create a new Actor
#    Then Actor should have first and last name

Feature: Adding a new film to database
  Scenario: Creating new film
    Given A film with the title "Machoman" does not exist in database
    When I add a new film with the title "Machoman"
    Then The film "Machoman" exists in the database
Feature: the version can be retrieved
  Scenario: client makes call to GET /version
    When the client calls /version
    Then the client receives status code of 200
    And the client receives server version 1.0