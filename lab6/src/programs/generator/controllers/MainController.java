package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Map;
import org.json.JSONObject;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import listeners.InputStreamListener;

class Number {
  private final SimpleIntegerProperty index;
  private final SimpleDoubleProperty number;

  public Number(int index, double number) {
    this.index = new SimpleIntegerProperty(index);
    this.number = new SimpleDoubleProperty(number);
  }
  public SimpleIntegerProperty getIndex() { return index; }
  public SimpleDoubleProperty getNumber() { return number; }
}

public class MainController {
  @FXML
  private TableView<Number> tableView;

  @FXML
  private TableColumn<Number, Integer> columnIndex;

  @FXML
  private TableColumn<Number, Double> columnNumber;

  private double random(double min, double max) {
    return min + Math.random() * (max - min);
  }  

  @FXML
  private void initialize() {
    columnIndex.setCellValueFactory((cellData) -> cellData.getValue().getIndex().asObject());
    columnNumber.setCellValueFactory((cellData) -> cellData.getValue().getNumber().asObject());
    final ObservableList<Number> numbers = FXCollections.observableArrayList();
    tableView.setItems(numbers);
    final var listener = InputStreamListener.getInstance();
    listener.on("data", (json) -> {
      numbers.clear();
      final var min = json.getDouble("min");
      final var max = json.getDouble("max");
      final var n = json.getNumber("n").intValue();
      for (int index = 0; index < n; index++) {
        numbers.add(new Number(index + 1, random(min, max)));
      }
      Platform.runLater(() -> {
        final var data = numbers.stream().map((num) -> num.getNumber().getValue()).toList();
        final var clipboard = Clipboard.getSystemClipboard();
        final var content = new ClipboardContent();
        final var clipboardContent = new JSONObject(Map.of("data", data));
        content.putString(clipboardContent.toString());
        clipboard.clear();
        clipboard.setContent(content);
      });
    });
  }
}
