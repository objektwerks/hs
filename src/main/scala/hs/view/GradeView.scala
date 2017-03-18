package hs.view

import hs.domain.Grade

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

object GradeView {
  val gradeLabel = new Label { text = "Grade:" }
  val gradeCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Grade](g => g.grade.toString) )
  val gradeComboBox = new ComboBox[Grade] { prefHeight = 25; prefWidth = 60; items = ObservableBuffer[Grade](); cellFactory = gradeCellFactory }
  val gradePropsButton = new Button { text = "*"; prefHeight = 25 }
  val gradeAddButton = new Button { text = "+"; prefHeight = 25 }
  val gradePane = new HBox { spacing = 6; children = List(gradeLabel, gradeComboBox, gradePropsButton, gradeAddButton) }
}