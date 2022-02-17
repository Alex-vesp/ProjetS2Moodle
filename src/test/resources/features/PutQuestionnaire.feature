Feature: PUT Questionnaire

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXVXCZ"
    And a module named "module1"
    And a questionnaire named aaa "questionnaireXX"

  Scenario: Teacher PUT Questionnaire
    When "teacherX" put questionnaire named  "questionnaireXX" to "questionnaireXX2"
    Then "questionnaireXX"   is named    "questionnaireXX2"
    And Then last  delete  request  statusgs  is  200

  Scenario: student PUT Questionnaire
    When "studentnXVXC" put questionnaire named  "questionnaireXX" to "questionnaireXX2"
    Then "questionnaireXX" is not   changed to "questionnaireXX2"
    And Then last  put  request  status  is 403
