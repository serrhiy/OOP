package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane;
import tableview.Point;

public class TableController {

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private TableView<Point> tableView;

  @FXML
  private void initialize() {
    tableView.prefWidthProperty().bind(scrollPane.widthProperty());
    tableView.prefHeightProperty().bind(scrollPane.heightProperty());
  }
}
