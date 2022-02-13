Feature: Register new Questionnaire

  Background:
    Given a teacher with login "teacherZ"
    And a module named "ModuleUY"
    And a student with login "studentnXVXXC"


  Scenario: Teacher register new questionnaire
    When "teacherZ" registers questionnaire "questionnaireZZ" in module "ModuleUY"
    Then "questionnaireZZ" is registered to questionnaires
    And Then last request status is  200

  Scenario: student register new questionnaire
    When "studentnXVXXC" registers questionnaire "questionnaireZZ" in module "ModuleY"
    Then "questionnaireZZ" is not registered to questionnaires
    And Then last request status is  403
