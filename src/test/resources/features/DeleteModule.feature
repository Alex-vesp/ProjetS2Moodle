Feature: Delete Module

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"

  Scenario: Teacher delete Module
    When "teacherX" delete  module named  "module1"
    Then "module1" is deleted from modules
    And Then last delete request status is 200