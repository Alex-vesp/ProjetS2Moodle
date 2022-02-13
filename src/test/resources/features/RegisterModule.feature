Feature: Register new Module

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXV"

  Scenario: Teacher register new module
    When "teacherX" registers  module "ModuleA"
    Then "ModuleA" is registered to modules
    And Then last request status is 200


  Scenario: student register new module
    When "studentnXV" registers  module "ModuleNV"
    Then "ModuleNV" is  not registered to modules
    And Then last request status is 403

