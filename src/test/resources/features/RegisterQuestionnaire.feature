Feature: Register new Questionnaire

  Background:
    Given a teacher with login "teacherZ"
    And a module named "ModuleUY"


  Scenario: Teacher register new questionnaire
    When "teacherZ" registers questionnaire "questionnaireZ" in module "ModuleUY"
    Then "questionnaireZ" is registered to questionnaires
    And Then last request status is  200
