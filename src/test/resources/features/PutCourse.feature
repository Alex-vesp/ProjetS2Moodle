Feature: PUT Course

  Background:
    Given a teacher with login "teacherX"
    And a student with login "studentnXVXCZ"
    And a module named "module1"
    And a course named a "coursXXP"

  Scenario: Teacher PUT Course
    When "teacherX" put course named  "coursXXP" to "coursXXP1"
    Then "coursXXP" is named  "coursXXP1"
    And Then last delete request statusgs is  200

  Scenario: student delete course
    When "studentnXVXC" put course named  "coursXX" to "coursXXP2"
    Then "courseXX" is not changed to "coursXXP2"
    And Then last  put  request  status is 403
