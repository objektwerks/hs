package hs.pane

import hs.{Model, Store}
import hs.dialog.AssignmentDialog
import hs.repository.Assignment

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentPane extends VBox {
  val assignmentLabel = new Label { text = "Assignments:" }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentList = new ListView[Assignment] { prefWidth = 333; items = ObservableBuffer[Assignment](); cellFactory = assignmentCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val assignedDate = new Label { text = "00/00/0000" }
  val toLabel = new Label { text = " - " }
  val completedDate = new Label { text = "00/00/0000" }
  val scoreLabel = new Label { text = "0.0" }
  val splitLabel = new Label { text = " / " }
  val totalLabel = new Label { text = "0.0" }
  val assignmentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val assignmentAddButton = new Button { text = "+"; prefHeight = 25 }
  val assignmentToolBar = new HBox { spacing = 6; children = List(assignmentPropsButton, assignmentAddButton) }
  val assignmentDetailsPane = new HBox { spacing = 6; children = List(assignedDate, toLabel, completedDate, scoreLabel, splitLabel, totalLabel, assignmentToolBar) }

  spacing = 6
  children = List(assignmentLabel, assignmentList, assignmentDetailsPane)

  assignmentList.selectionModel().selectedItemProperty().onChange { (_, _, selectedAssignment) =>
    assignmentPropsButton.disable = false
    Model.assignmentid = selectedAssignment.id
  }
  assignmentPropsButton.onAction = { _ => handleAction(assignmentList.selectionModel().getSelectedItem) }
  assignmentAddButton.onAction = { _ => handleAction(Assignment(courseid = Model.courseid)) }

  def handleAction(assignment: Assignment): Unit = {
    import Store.repository._
    val result = new AssignmentDialog(assignment).showAndWait()
    result match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) => assignments.save(Assignment(id, courseid, task, assigned, completed, score))
      case _ => println("Assignment dialog failed!")
    }
  }
}