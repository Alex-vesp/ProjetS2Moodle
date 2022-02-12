Feature: Delete Questionnaire

  Background:
    Given a teacher with login "teacherX"
    And And a module named "module1"
    And a questionnaire named "questionnaireX"

  Scenario: Teacher delete questionnaire
    When "teacherX" delete questionnaire named  "questionnaireX" in "module1"
    Then "questionnaireX" is  not registered to questionnaire in "module1"
