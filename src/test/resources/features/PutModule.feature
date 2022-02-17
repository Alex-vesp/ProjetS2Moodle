Feature: put Module

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXVXCZ"
    And a module named "module1"

  Scenario: Teacher delete Module
    When "teacherX" put  module named  "module1" to "moduleX2"
    Then "module1" is named "moduleX2"
    And Then last delete request statusgs is 200
