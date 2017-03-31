package objektwerks.hs.model

import objektwerks.hs.entity.{Assignment, Course, Grade, Student}
import objektwerks.hs.repository.Repository

import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

class Model(repository: Repository) {
  import repository._

  val studentList = ObservableBuffer[Student]()
  val selectedStudent = ObjectProperty[Int](0)

  def listStudents(): Unit = {
    studentList.clear()
    gradeList.clear()
    courseList.clear()
    assignmentList.clear()
    studentList ++= await(students.list())
  }

  def updateStudent(selectedIndex: Int, student: Student): Unit = {
    await(students.save(student))
    studentList.update(selectedIndex, student)
  }

  def addStudent(student: Student): Student = {
    val newId = await(students.save(student))
    val newStudent = student.copy(id = newId.get)
    studentList += newStudent
    selectedStudent.value = newStudent.id
    newStudent
  }

  val gradeList = ObservableBuffer[Grade]()
  val selectedGrade = ObjectProperty[Int](0)

  def listGrades(studentId: Int): Unit = {
    gradeList.clear()
    courseList.clear()
    assignmentList.clear()
    gradeList ++= await(grades.list(studentId))
  }

  def updateGrade(selectedIndex: Int, grade: Grade): Unit = {
    await(grades.save(grade))
    gradeList.update(selectedIndex, grade)
  }

  def addGrade(grade: Grade): Grade = {
    val newId = await(grades.save(grade))
    val newGrade = grade.copy(id = newId.get)
    gradeList += newGrade
    selectedGrade.value = newGrade.id
    newGrade
  }

  val courseList = ObservableBuffer[Course]()
  val selectedCourse = ObjectProperty[Int](0)

  def listCourses(gradeId: Int): Unit = {
    courseList.clear()
    assignmentList.clear()
    courseList ++= await(courses.list(gradeId))
  }

  def updateCourse(selectedIndex: Int, course: Course): Unit = {
    await(courses.save(course))
    courseList.update(selectedIndex, course)
  }

  def addCourse(course: Course): Course = {
    val newId = await(courses.save(course))
    val newCourse = course.copy(id = newId.get)
    courseList += newCourse
    selectedCourse.value = newCourse.id
    newCourse
  }

  val assignmentList = ObservableBuffer[Assignment]()
  val selectedAssignment = ObjectProperty[Int](0)

  def listAssignments(courseId: Int): Unit = {
    assignmentList.clear()
    assignmentList ++= await(assignments.list(courseId))
  }

  def updateAssignment(selectedIndex: Int, assignment: Assignment): Unit = {
    await(assignments.save(assignment))
    assignmentList.update(selectedIndex, assignment)
  }

  def addAssignment(assignment: Assignment): Assignment = {
    val newId = await(assignments.save(assignment))
    val newAssignment = assignment.copy(id = newId.get)
    assignmentList += newAssignment
    selectedAssignment.value = newAssignment.id
    newAssignment
  }

  def scoreAssignments(courseId: Int): Double = await(assignments.score(courseId)).getOrElse(0.0)
}