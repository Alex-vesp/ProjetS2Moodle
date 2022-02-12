Feature: Delete Cours

  Background:
    Given a teacher with login "teacherX"
    And And a  module named "module1"
    And a cours named "coursN"

  Scenario: Teacher delete  cours
    When "teacherX" delete  cours named  "coursN" in "module1"
    Then "coursN" is  not registered to cours in "module1"
