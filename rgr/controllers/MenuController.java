package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.ToolBar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import javafx.application.Platform;
import shapes.*;
import editors.Editor;

public class MenuController {

  @FXML private BorderPane borderPane;
  @FXML private Canvas canvas;
  @FXML private Menu colors;
  @FXML private ToolBar toolBar;
  @FXML private ColorPicker colorPicker;
  @FXML private ChoiceBox<Integer> choiceWidth;

  @FXML
  private void exit() {
    Platform.exit();
  }

  private final Map<String, Class<? extends Shape>> shapes = Map.of(
    "line", Line.class,
    "ellipse", Ellipse.class,
    "rectangle", Rectangle.class,
    "brush", Brush.class
  );

  private final List<Integer> widths = List.of(1, 2, 3, 4, 5, 6, 7, 8);

  @FXML
  private void changeWidth(final ActionEvent event) {
    final var width = choiceWidth.getValue();
    Editor.getInstance().changeWidth(width);
  }

  @FXML
  private void changeColor(final ActionEvent event) {
    final var color = colorPicker.getValue();
    Editor.getInstance().changeColor(color);
  }

  @FXML
  private void saveAs() throws IOException {
    final var stage = (Stage)borderPane.getScene().getWindow();
    final var savefile = new FileChooser();
    savefile.setTitle("Save File");
    final var file = savefile.showSaveDialog(stage);
    if (file == null) return;
    final var filewriter = new FileWriter(file, false);
    try (final var writer = new BufferedWriter(filewriter)) {
      final var shapes = Editor.getInstance().shapes();
      final var result = new JSONObject();
      final var window = new JSONObject();
      final var data = ShapeParser.serialise(shapes);
      window.put("width", stage.getWidth());
      window.put("height", stage.getHeight());
      result.put("window", window);
      result.put("shapes", data);
      writer.write(result.toString());
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }

  @FXML
  private void open() {
    final var stage = (Stage)borderPane.getScene().getWindow();
    final var fileChooser = new FileChooser();
    final var extention = new FileChooser.ExtensionFilter("JSON Files", "*.json");
    fileChooser.getExtensionFilters().add(extention);
    final var file = fileChooser.showOpenDialog(stage);
    if (file == null) return;
    try {
      final var bytes = Files.readAllBytes(file.toPath());
      final var text = new String(bytes);
      final var json = new JSONObject(text);
      final var window = json.getJSONObject("window");
      final var shapes = json.getJSONArray("shapes");
      final var width = window.getDouble("width");
      final var height = window.getDouble("height");
      final var newshapes = ShapeParser.parse(shapes);
      stage.setWidth(width);
      stage.setHeight(height);
      Editor.getInstance().replace(newshapes);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void initialize() {
    final var editor = Editor.getInstance().start(canvas, borderPane);
    choiceWidth.getItems().addAll(widths);
    choiceWidth.setValue(widths.get(0));
    canvas.widthProperty().bind(borderPane.widthProperty());
    canvas.heightProperty().bind(borderPane.heightProperty());
    final var items = toolBar.getItems();
    for (final var pair: shapes.entrySet()) {
      final var name = pair.getKey();
      final var Constructor = pair.getValue();
      Button button = null;
      for (final var item: items) {
        if (!item.getId().equals(name)) continue;
        button = (Button)item;
      }
      if (button == null) continue;
      button.setOnAction((_) -> {
        editor.newShape(Constructor);
      });
    }
  }
}
