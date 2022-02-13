Feature: Register new Course

  Background:
    Given a teacher with login "teacherZ"
    And a module named "ModuleX"


  Scenario: Teacher register new course
    When "teacherZ" registers course "course1" in module "ModuleX"
    Then "course1" is registered to courses
    And Then last request status  is 200
