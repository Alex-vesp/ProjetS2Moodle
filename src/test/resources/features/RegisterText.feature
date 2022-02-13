Feature: Register new Text

  Background:
    Given a teacher with login "teacherX"
    And a module named "ModuleABC"
    And a cours "courseABCD" in module "moduleABC"

  Scenario: Teacher register new text
    When "teacherX" registers text "textABC" in "courseABCD"
    Then "textABC" is registered to "courseABCD"
    And Then last requestt status is 200
