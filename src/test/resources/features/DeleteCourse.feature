Feature: Delete Course

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"
    And a course named a "coursXX"

  Scenario: Teacher delete course
    When "teacherX" delete  course named  "coursXX" in module "module1"
    Then "courseXX" is deleted from courses
    And Then last delete request  status is 200
