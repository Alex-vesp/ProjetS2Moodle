Feature: Register new Text

  Background:
    Given a teacher with login "teacherX"
    And a module named "ModuleY"
    And a cours "course12131Z" in module "ModuleY"

  Scenario: Teacher register new text
    When "teacherX" registers text "textABC" in "course12131Z"
    Then "textABC" is registered to "course12131Z"
    And Then last requestt status is 200
