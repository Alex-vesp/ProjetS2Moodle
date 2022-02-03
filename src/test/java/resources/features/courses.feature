Fonctionnalité: Positionnement Prof 

  Background:
    Given un prof du nom "Max"
    And un prof du nom "Jerome"
    And un module "gestion de projAnd" sans prof positionné sur lui 
    And un module "programmation" 
    And "Jerome" positioné sur "programmation"
    And un etudiant du nom "Ahmed" 
    And "Ahmed"  positionné sur "AIGame"

  Scenario: Prof peut se positionner sur un cours 
    When "Max" veut se positionner sur "gestion de projAnd"
    Then "Max" est positionné sur "gestion de projAnd"

Scenario: Prof peut ajouter un enseignant a sont cours 
    When "Jerome" veut ajouter "Max" sur le cours "programmation"
    Then "Max" est positionné sur "programmation"

Scenario: Prof peut ajouter un etudiant sa sont cours
    When "Jerome" veut ajouter "Ahmed" sur le cours "programmation"
    And "Ahmed" n'as pas de cours
    Then "Ahmed" est positionné sur "programmation"

Scenario: Un etudiant veut ajouter un prof sur un cours 
    When "Ahmed" veut ajouter "Jerome" sur le cours "AIGame"
    Then "Ahmed" ne peut pas le faire
    Et "Jerome" n'est pas positionné sur "AIGame" 
