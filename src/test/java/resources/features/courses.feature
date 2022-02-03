Feature: Courses and resources 

Background: 
Given "Qcm" resources of type "Questionnaire"
And "Cours 1" resources of type "Cours"
    

Scenario: Add resources of type Questionnaire to course
    When "Jerome" want to add resources "Qcm" to "programmation"
    Then "Qcm" is added to "programmation"
    
Scenario: Add resources of type Cours to course
    When "Jerome" want to add resources "Cours 1" to "programmation"
    Then "Cours 1" is added to "programation"

Scenario: Visibility of resources is visible
    When "Jerome" set visibility of "Cours 1" to "visible"
    Then "Cours 1" is visible 

Scenario: Visibility of resources is hide
    When "Jerome" set visibility of "Qcm" to "hide"
    Then "Qcm" is hidden 
