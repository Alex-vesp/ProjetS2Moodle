Feature: Register new Course

  Background:
    Given a teacher with login "teacherZ"
    And a student with login "studentnXVD"
    And a module named "ModuleY"



  Scenario: Teacher register new course
    When "teacherZ" registers course "course12131Z" in module "ModuleY"
    Then "course12131Z" is registered to courses
    And Then last request status  is 200


  Scenario: student register new course
    When "studentnXVD" registers course "courseXVZY" in module "ModuleY"
    Then "courseXVZY" is not registered to courses
    And Then last request status  is 403
