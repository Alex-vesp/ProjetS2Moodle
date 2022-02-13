Feature: Register new QstOuverte

  Background:
    Given a teacher with login "teacherX"
    And a module named "ModuleABCX"
    And a questionnairee "questionnaireZZ" in module "ModuleABCX"
    And a student with login "studentnXVXXC"


  Scenario: Teacher register new Qcm
    When "teacherX" registers Qcm "qcmA" in "questionnaireZZ"
    Then  qcm "qcmA" is registered to questionnaire "questionnaireZZ"
    And Then last requestt statussx is 200


  Scenario: student register new Qcm
    When "studentnXVXXC" registers Qcm "qcmB" in "questionnaireZZ"
    Then  qcm "qcmB" is  not registered to questionnaire "questionnaireZZ"
    And Then last requestt statussx is 403
