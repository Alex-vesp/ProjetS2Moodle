Feature: Delete qstOuverte

  Background:
    Given a teacher with login "teacherX"
    And a qstOuverte named "qstX"

  Scenario: Teacher delete qstOuverte
    When "teacherX" delete qstOuverte named "qstX"
    Then "qstX" is deleted from qstOuvertes
    And Then last delete requestt status is 200
