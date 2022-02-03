Feature: questionsFeature

  Background:
    Given a student of name "AAAA"
    And a question of id 2324   of type QCM
    And a question of id 2325   of type QCM
    And "AAAA" has aldrady answered the question of id 2325

  Scenario: "AAAA" had not answered
    When "AAAA" choose an option 0 for question 2324
    Then There is an answer of 0 value  for question of id 2324  and student "AAAA"  in  answers
