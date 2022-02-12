Feature: Register new Course

  Background:
    Given a teacher with login "teacherZ"
    And a module named "ModuleX"


  Scenario: Teacher register new course
    When "teacherZ" registers course "courseX" in module "ModuleX"
    Then "courseX" is registered to courses
    And Then last request status  is 200
