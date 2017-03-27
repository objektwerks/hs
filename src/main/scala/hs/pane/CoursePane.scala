package hs.pane

import hs.{Model, Store}
import hs.dialog.CourseDialog
import hs.repository.Course

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane() extends VBox {
  val courseLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseList = new ListView[Course] { prefWidth = 333; items = Model.courses; cellFactory = courseCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val coursePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val courseAddButton = new Button { text = "+"; prefHeight = 25; disable = true }
  val courseToolBar = new HBox { spacing = 6; children = List(coursePropsButton, courseAddButton) }

  spacing = 6
  children = List(courseLabel, courseList, courseToolBar)

  courseList.selectionModel().selectedItemProperty().onChange { (_, _, selectedCourse) =>
    coursePropsButton.disable = false
    courseAddButton.disable = false
    Model.selectedCourse.value = selectedCourse
  }
  coursePropsButton.onAction = { _ => save(courseList.selectionModel().getSelectedItem) }
  courseAddButton.onAction = { _ => save(Course(gradeid = Model.selectedGrade.value.id)) }

  def save(course: Course): Unit = {
    import Store.repository._
    val result = new CourseDialog(course).showAndWait()
    result match {
      case Some(Course(id, gradeid, name)) =>
        val course = Course(id, gradeid, name)
        val courseId = await(courses.save(course))
        if (id == 0) Model.courses += course.copy(id = courseId.get)
      case _ => println("Course dialog failed!")
    }
  }
}