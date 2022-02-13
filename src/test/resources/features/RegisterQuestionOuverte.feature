Feature: Register new QstOuverte

  Background:
    Given a teacher with login "teacherX"
    And a module named "ModuleABCX"
    And a questionnaire "questionnaireZZ" in module "ModuleABCX"

  Scenario: Teacher register new questionOuverte
    When "teacherX" registers questionOuverte "qstA" in "questionnaireZZ"
    Then "qstA" is registered to questionnaire "questionnaireZZ"
    And Then last requestt statuss is 200
