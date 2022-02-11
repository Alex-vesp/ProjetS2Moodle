Feature: Get Module Participants

  Background:
    Given a module with name "Gestion de Projet" and id 2

  Scenario: A teacher wants to visualize the list of the students registered to his module
    When "studentX" get the participants of the module of name "Gestion de Projet" and id 2
    Then he can visualize the name of the participants of the module of id 2
