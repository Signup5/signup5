Feature: Person can respond to an Invitation
  Scenario: Person sets Attendance to "ATTENDING" on Invitation
    Given Person is invited to an Event
    When Person responds with "ATTENDING"
    Then Person gets a confirmation message about new status
    And Invitation status is updated to "ATTENDING"

  Scenario: Person sets Attendance to "MAYBE" on Invitation
    Given Person is invited to an Event
    When Person responds with "MAYBE"
    Then Person gets a confirmation message about new status
    And Invitation status is updated to "MAYBE"

  Scenario: Person sets Attendance to "NOT_ATTENDING" on Invitation
    Given Person is invited to an Event
    When Person responds with "NOT_ATTENDING"
    Then Person gets a confirmation message about new status
    And Invitation status is updated to "NOT_ATTENDING"