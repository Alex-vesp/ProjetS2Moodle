Feature: Get Questionnaire

  Background:
    Given a questionnaire with name "Evaluation" and id 1 and description "Voici le questionnaire d'évaluation du S1"

  Scenario: Teacher wants to visualize the questionnaire
    When "teacherX" get the questionnaire of id 1
    Then he can visualize the name "Evaluation", the id 1 and the description "Voici le questionnaire d'évaluation du S1"
