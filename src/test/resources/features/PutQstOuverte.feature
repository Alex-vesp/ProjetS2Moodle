Feature: put QstOuverte

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentC"
    And a qst named "qst1"

  Scenario: Teacher put QstOuverte
    When "teacherX" put Qst named  "qst1" to "qstX2"
    Then "qst1" is nammed "qstX2"
    And Then last put request status iss 200

  Scenario: student put QstOuverte
    When "studentC" put Qstt named "qstH" to "qstHI"
    Then "qstH" is nott changed to "qstHI"
    And Then last put request status iss 403