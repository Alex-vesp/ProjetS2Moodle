Feature: Register new Questionnaire

  Background:
    Given a teacher with login "teacherZ"
    And a module named "ModuleUY"


  Scenario: Teacher register new questionnaire
    When "teacherZ" registers questionnaire "questionnaireZZ" in module "ModuleUY"
    Then "questionnaireZZ" is registered to questionnaires
    And Then last request status is  200
