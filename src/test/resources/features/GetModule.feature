Feature: Get Module

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"


  Scenario: Teacher get Module
    When "teacherX" get  module named  "module1"
    Then "module1" is read from modules
    And Then last request status iss 200

