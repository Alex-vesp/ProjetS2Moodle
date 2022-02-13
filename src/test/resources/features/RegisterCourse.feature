Feature: Register new Course

  Background:
    Given a teacher with login "teacherZ"
    And a student with login "studentnXV"
    And a module named "ModuleY"



  Scenario: Teacher register new course
    When "teacherZ" registers course "course1" in module "ModuleY"
    Then "course1" is registered to courses
    And Then last request status  is 200


  Scenario: student register new course
    When "studentnXV" registers course "courseXV" in module "ModuleY"
    Then "courseXV" is not registered to courses
    And Then last request status  is 403
