Feature: Get qstOuverte                                                                                                                                                                                                                        Feature: Get Module

  Background:
    Given a teacher with login "teacherX"
    And a qst named "qst1"


  Scenario: Teacher get qst
    When "teacherX" get qst named "qst1"
    Then "qst1" is read from qsts
    And Then last requestt statuss iss 200

