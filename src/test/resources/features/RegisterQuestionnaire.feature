Feature: Register Questionnaire

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"

  Scenario: Teacher register new questionnaire
    When "teacherX" registers  questionnaire named  "questionnaireX" in "module1"
    Then "questionnaireX" is registered to questionnaire in "module1"
