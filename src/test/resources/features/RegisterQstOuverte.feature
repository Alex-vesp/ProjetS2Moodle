Feature: Register new QstOuverte

  Background:
    Given a teacher with login "teacherX"
    And a questionnaire "questX" in module "moduleX"

  Scenario: Teacher register new questionOuverte
    When "teacherX" registers questionOuverte "qstA" in "questX"
    Then "qstA" is registered to questionnaire "questX"
    And Then last requestt statuss is 200
