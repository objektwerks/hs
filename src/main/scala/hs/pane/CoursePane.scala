package hs.pane

import com.typesafe.config.Config
import hs.dialog.CourseDialog
import hs.entity.Course
import hs.model.Model

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane(conf: Config, model: Model) extends VBox {
  val courseLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseList = new ListView[Course] { prefWidth = 333; items = model.courseList; cellFactory = courseCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val coursePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val courseAddButton = new Button { text = "+"; prefHeight = 25; disable = true }
  val courseToolBar = new HBox { spacing = 6; children = List(coursePropsButton, courseAddButton) }

  spacing = 6
  children = List(courseLabel, courseList, courseToolBar)

  model.selectedGrade.onChange { (_, _, selectedGrade) =>
    model.listCourses(selectedGrade.id)
  }

  model.selectedCourse <== courseList.selectionModel().selectedItemProperty()

  model.courseList.onChange {
    coursePropsButton.disable = model.courseList.isEmpty
    courseAddButton.disable = model.courseList.isEmpty
  }

  coursePropsButton.onAction = { _ => update(courseList.selectionModel().getSelectedIndex, courseList.selectionModel().getSelectedItem) }

  courseAddButton.onAction = { _ => add(Course(gradeid = model.selectedGrade.value.id)) }

  def update(selectedIndex: Int, course: Course): Unit = {
    new CourseDialog(course).showAndWait() match {
      case Some(Course(id, gradeid, name)) => model.updateCourse(selectedIndex, Course(id, gradeid, name))
      case _ =>
    }
  }

  def add(course: Course): Unit = {
    new CourseDialog(course).showAndWait() match {
      case Some(Course(id, gradeid, name)) => model.addCourse(Course(id, gradeid, name))
      case _ =>
    }
  }
}