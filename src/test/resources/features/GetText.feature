Feature: Get Text                                                                                                                                                                                                                                      Feature: Get Module

  Background:
    Given a teacher with login "teacherX"
    And a text named "text1"


  Scenario: Teacher get text
    When "teacherX" get text named "text1"
    Then "text1" is read from texts
    And Then last requestt status iss 200

