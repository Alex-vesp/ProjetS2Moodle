Feature: put Text

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentC"
    And a text named "text1"

  Scenario: Teacher put Text
    When "teacherX" put text named  "text1" to "textX2"
    Then "text1" is nameed "textX2"
    And Then last put request statuss is 200

  Scenario: student put Text
    When "studentC" put textt named  "textH" to "textHI"
    Then "textH" is not changedd to "textHI"
    And Then last put request statuss is 403
