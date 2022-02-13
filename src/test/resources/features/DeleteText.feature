Feature: Delete Text

  Background:
    Given a teacher with login "teacherX"
    And a text named "textX"

  Scenario: Teacher delete Text
    When "teacherX" delete  text named  "textX"
    Then "textX" is deleted from texts
    And Then last deletee request status is 200
