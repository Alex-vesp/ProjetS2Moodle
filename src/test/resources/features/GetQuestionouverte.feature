Feature: Get Questionnaire

  Background:
    Given a question ouverte with a text "Quel est le meilleur pattern possible dans cette situation" and a reponse "Le pattern Observer" and an id of 3

  Scenario: a student wants to visualize a question ouverte
    When "studentX" get the question ouverte of id 3
    Then he can visualize the text "Quel est le meilleur pattern possible dans cette situation" of the question of id 3
