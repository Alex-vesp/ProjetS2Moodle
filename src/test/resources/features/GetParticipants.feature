Feature: Get participants of a cours                                                                                                                                                                                                                       Feature: Get Module

  Background:
    Given a teacher with login "teacherX"
    And a module named "ModuleB"
    And a student with login "studentnXVXCW"



  Scenario: Teacher get participants
    When "teacherX" get participants of module "ModuleB"
    Then participants of module "ModuleB" are read for "teacherX"



  Scenario: Student get participants
    When "studentnXVXCW" get participants of module "ModuleB"
    Then participants of module "ModuleB" are not  for "teacherX"

