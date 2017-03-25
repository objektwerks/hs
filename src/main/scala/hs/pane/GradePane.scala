package hs.pane

import hs.Store
import hs.dialog.GradeDialog
import hs.repository.Grade

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class GradePane(studentPane: StudentPane) extends HBox {
  val gradeLabel = new Label { text = "Grade:" }
  val gradeCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Grade](g => g.year) )
  val gradeComboBox = new ComboBox[Grade] { prefHeight = 25; prefWidth = 203; items = ObservableBuffer[Grade](); cellFactory = gradeCellFactory }
  val gradePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val gradeAddButton = new Button { text = "+"; prefHeight = 25; disable = true }

  spacing = 6
  children = List(gradeLabel, gradeComboBox, gradePropsButton, gradeAddButton)

  gradeComboBox.selectionModel().selectedItemProperty().onChange { (_, _, selectedGrade) =>
    gradePropsButton.disable = false
    gradeAddButton.disable = false
    println(selectedGrade)
  }
  gradePropsButton.onAction = { _ => handleAction(gradeComboBox.value.value) }
  gradeAddButton.onAction = { _ => handleAction(Grade(studentid = 1)) }

  def handleAction(grade: Grade): Unit = {
    import Store.repository._
    val result = new GradeDialog(grade).showAndWait()
    result match {
      case Some(Grade(id, studentid, year, started, completed)) => grades.save(Grade(id, studentid, year, started, completed))
      case _ => println("Grade dialog failed!")
    }
  }
}