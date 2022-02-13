Feature: Delete questionnaire

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXVXCX"
    And a module named "module1"
    And a questionnaire named aaa "questionnaireXX"

  Scenario: Teacher delete questionnaire
    When "teacherX" delete  questionnaire named  "questionnaireXX" in module "module1"
    Then "questionnaireXX" is deleted from questionnaires
    And Then last  delete request  status is 200

  Scenario: student delete questionnaire
    When "studentnXVXCX" delete  questionnaire named  "questionnaireXX" in module "module1"
    Then "questionnaireXX" is not deleted from questionnaire
    And Then last  delete request  status is 403
