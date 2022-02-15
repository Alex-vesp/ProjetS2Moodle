Feature: Get participants of a cours                                                                                                                                                                                                                       Feature: Get Module

  Background:
    Given a teacher with login "teacherX"
    And a module named "ModuleB"


  Scenario: Teacher get participants
    When "teacherX" get participants of module "ModuleB"
    Then participants of module "ModuleB" are read for "teacherX"

