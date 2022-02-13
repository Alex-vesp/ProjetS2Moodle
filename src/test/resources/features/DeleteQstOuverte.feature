Feature: Delete qstOuverte

  Background:
    Given a teacher with login "teacherX"
    Given a student with login "studentZZZZ"
    And a qstOuverte named "qstX"

  Scenario: Teacher delete qstOuverte
    When "teacherX" delete qstOuverte named "qstX"
    Then "qstX" is deleted from qstOuvertes
    And Then last delete requestt status is 200

  Scenario: Student register new questionOuverte
    When "studentZZZZ" delete qstOuverte named "qstX"
    Then "qstX" is not deleted to questionnaire "course12131Z"
    And Then last delete requestt status is 403



