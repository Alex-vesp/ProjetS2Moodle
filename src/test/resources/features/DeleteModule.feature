Feature: Delete Module

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXVXCZ"
    And a module named "module1"

  Scenario: Teacher delete Module
    When "teacherX" delete  module named  "module1"
    Then "module1" is deleted from modules
    And Then last delete request status is 200

  Scenario: Student delete Module
    When "studentnXVXCZ" delete  module named  "module1"
    Then "module1" is not deleted from modules
    And Then last delete request status is 403
