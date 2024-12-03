package controllers;

import java.util.Map;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import shapes.Shape;
import javafx.scene.control.ScrollPane;
import tableview.PointPair;
import javafx.scene.control.TableColumn;
import editors.Editor;

public class TableController {

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private TableView<PointPair> tableView;

  @FXML
  private TableColumn<PointPair, String> nameColumn;
  
  @FXML
  private TableColumn<PointPair, Double> x1Column;

  @FXML
  private TableColumn<PointPair, Double> y1Column;

  @FXML
  private TableColumn<PointPair, Double> x2Column;

  @FXML
  private TableColumn<PointPair, Double> y2Column;

  private ObservableList<PointPair> points = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    tableView.prefWidthProperty().bind(scrollPane.widthProperty());
    tableView.prefHeightProperty().bind(scrollPane.heightProperty());
    nameColumn.setCellValueFactory((cellData) -> cellData.getValue().getName());
    x1Column.setCellValueFactory((cellData) -> cellData.getValue().getX1().asObject());
    y1Column.setCellValueFactory((cellData) -> cellData.getValue().getY1().asObject());
    x2Column.setCellValueFactory((cellData) -> cellData.getValue().getX2().asObject());
    y2Column.setCellValueFactory((cellData) -> cellData.getValue().getY2().asObject());
    final var editor = Editor.getInstance();
    tableView.setItems(points);
    final Map<Shape, PointPair> shapes = new HashMap<>();
    editor.on("create", (shape) -> {
      final var pair = shape.getDisplayCoords();
      final var first = pair.getKey();
      final var second = pair.getValue();
      final var point = new PointPair(
        shape.getName(),
        first.getKey(),
        first.getValue(),
        second.getKey(),
        second.getValue()
      );
      points.add(point);
      shapes.put(shape, point);
    });
    editor.on("delete", (shape) -> {
      final var point = shapes.get(shape);
      points.remove(point);
    });
  }
}
