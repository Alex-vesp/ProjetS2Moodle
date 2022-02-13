Feature: Register new Text

  Background:
    Given a teacher with login "teacherX"
    And a cours "courseX" in module "moduleX"

  Scenario: Teacher register new text
    When "teacherX" registers text "textA" in "courseX"
    Then "textA" is registered to "courseX"
    And Then last requestt status is 200
