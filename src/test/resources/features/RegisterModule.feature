Feature: Register new Module

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXVXXC"

  Scenario: Teacher register new module
    When "teacherX" registers  module "ModuleA"
    Then "ModuleA" is registered to modules
    And Then last request status is 200


  Scenario: student register new module
    When "studentnXVXXC" registers  module "ModuleNVCV"
    Then "ModuleNVCV" is  not registered to modules
    And Then last request status is 403

