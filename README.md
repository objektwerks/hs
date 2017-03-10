Homeschool
----------
>Homeschool app.

Object Model
------------
* Student (id, name, born)
* Grade (id, studentid, grade, started, completed)
* Course (id, gradid, name, website?)
* Assignment (id, courseid, task, assigned, completed, score)

Relational Model
----------------
* Student 1 ---> * Grade 1 ---> * Course 1 ---> * Assignement

Commands
--------
* Change[T]

Events
------
* Changed[T]

Queries
-------
1. List[T]
2. Calculate Course Assignments Score

Test
----
1. sbt clean test

Run
---
1. sbt clean test run