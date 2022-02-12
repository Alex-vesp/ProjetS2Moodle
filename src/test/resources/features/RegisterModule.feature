Feature: Register new Module

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentn"

  Scenario: Teacher register new module
    When "teacherX" registers  module "ModuleA"
    Then "ModuleA" is registered to modules
    And Then last request status is 200
