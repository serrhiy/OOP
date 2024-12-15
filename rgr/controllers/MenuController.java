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
import javafx.scene.control.MenuItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import settings.Color;
import settings.Fill;
import shapes.*;
import editors.Editor;

public class MenuController {

  @FXML private BorderPane borderPane;
  @FXML private BorderPane pane;
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
    try (BufferedWriter writer = new BufferedWriter(filewriter)) {
      writer.write(stage.getWidth() + " " + stage.getHeight());
      writer.newLine();
      final var shapes = Editor.getInstance().shapes();
      for (final var shape: shapes) {
        final var name = shape.getClass().getSimpleName();
        writer.write(name + " ");
        final var coords = shape.getCoords();
        for (final var coord: coords) {
          writer.write(String.valueOf(coord) + " ");
        }
        writer.newLine();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void open() throws IOException {
    final var stage = (Stage)borderPane.getScene().getWindow();
    final var fileChooser = new FileChooser();
    final var extention = new FileChooser.ExtensionFilter("Text Files", "*.txt");
    fileChooser.getExtensionFilters().add(extention);
    final var file = fileChooser.showOpenDialog(stage);
    if (file == null) return;
    final var editor = Editor.getInstance().restore();
    try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
      final var header = reader.readLine().split("\\s+");
      final var width = Double.parseDouble(header[0]);
      final var height = Double.parseDouble(header[1]);
      stage.setWidth(width);
      stage.setHeight(height);
      while (true) {
        final var line = reader.readLine();
        if (line == null) return;
        final var columns = line.split("\\s+");
        final var name = columns[0];
        final var numbers = new ArrayList<Double>();
        for (int index = 1; index < columns.length; index++) {
          final var column = columns[index];
          final var number = Double.parseDouble(column);
          numbers.add(number);
        }
        final var constructor = this.shapes.get(name.toString().toLowerCase());
        final var declared = constructor.getDeclaredConstructor(List.class);
        final var shape = declared.newInstance(numbers);
        editor.add(shape);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void initialize() {
    choiceWidth.getItems().addAll(widths);
    choiceWidth.setValue(widths.get(0));
    canvas.widthProperty().bind(borderPane.widthProperty());
    canvas.heightProperty().bind(borderPane.heightProperty());
    final var items = toolBar.getItems();
    final var editor = Editor.getInstance().start(canvas);
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

  @FXML
  private void colors(final ActionEvent event) {
    final var item = (MenuItem)event.getTarget();
    final var text = item.getText();
    Color.getInstance().setColor(text);
  }

  @FXML
  private void fill() {
    final var stage = (Stage)borderPane.getScene().getWindow();
    System.out.println(stage.getWidth());
    final var fill =  Fill.getInstance().getFill();
    Fill.getInstance().setFill(!fill);
  }
}
