Feature: Delete questionnaire

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"
    And a questionnaire named aaa "questionnaireXX"

  Scenario: Teacher delete questionnaire
    When "teacherX" delete  questionnaire named  "questionnaireXX" in module "module1"
    Then "questionnaireXX" is deleted from questionnaires
    And Then last  delete request  status is 200
