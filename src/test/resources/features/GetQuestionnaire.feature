Feature: Get Questionnaire

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"
    And a questionnaire named aa "questionnaireXX"


  Scenario: Teacher get questionnaire
    When "teacherX" get  questionnaire named  "questionnaireXX" in "module1"
    Then "questionnaireXX" is read from questionnaires
    And Then last   request status iss  200

