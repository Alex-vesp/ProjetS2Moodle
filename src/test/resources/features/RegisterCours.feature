Feature: Register Cours

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"

  Scenario: Teacher register new cours
    When "teacherX" registers  cours named  "coursX" in "module1"
    Then "coursX" is registered to cours in "module1"
