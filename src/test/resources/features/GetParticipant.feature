Feature: Get participants of a cours                                                                                                                                                                                                                       Feature: Get Module

  Background:
    Given a teacher with login "teacherX"


  Scenario: Teacher get participants
    When "teacherX" get participants of module "ModuleA"
    Then participants of module "ModuleA" are read
    And Then last request status is 200

