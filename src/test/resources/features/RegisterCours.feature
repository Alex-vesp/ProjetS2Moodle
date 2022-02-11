Feature: Register Cours

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"

  Scenario: Teacher register new cours
    When "teacherX" registers  cours named  "cours1" in "module1"
    Then "cours1" is registered to cours in "module1"
