Feature: Register new Text

  Background:
    Given a teacher with login "teacherX"
    And a cours "courseA" in module "moduleX"

  Scenario: Teacher register new text
    When "teacherX" registers text "textA" in "courseA"
    Then "textA" is registered to "courseA"
    And Then last requestt status is 200
