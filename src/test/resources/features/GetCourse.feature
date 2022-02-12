Feature: Get Course

  Background:
    Given a teacher with login "teacherX"
    And a module named "module1"
    And a course named aa "coursXX"


  Scenario: Teacher get course
    When "teacherX" get  course named  "coursXX" in "module1"
    Then "coursXX" is read from courses
    And Then last request status iss  200

