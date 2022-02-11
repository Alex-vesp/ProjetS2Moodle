Feature: Get Cours

  Background:
    Given a cours with name "Gestion de Projet" and id 2 and description "Cours de gestion de projet en Java"

  Scenario: A student wants to visualize the cours he is registered in
    When "studentX" get the cours of name "Gestion de Projet" and id 2
    Then he can visualize the name "Gestion de Projet" and the description "Cours de gestion de projet en Java"
