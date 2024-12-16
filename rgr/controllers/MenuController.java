package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.ToolBar;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import shapes.*;
import editors.Editor;

public class MenuController {

  @FXML private BorderPane borderPane;
  @FXML private Canvas canvas;
  @FXML private ToolBar toolBar;
  @FXML private ColorPicker colorPicker;
  @FXML private ChoiceBox<Integer> choiceWidth;
  @FXML private Menu savers;
  @FXML private BorderPane canvasPain;

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

  private final Map<String, Pair<TriConsumer<File, Stage, Canvas>,TriConsumer<File, Stage, Canvas>>> extensions = Map.of(
    "json", new Pair<>(FileSaver::jsonSave, FileSaver::jsonOpen),
    "png", new Pair<>(FileSaver::pngSave, FileSaver::pngOpen)
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

  private List<FileChooser.ExtensionFilter> getExtentionFilters() {
    final var result = new ArrayList<FileChooser.ExtensionFilter>();
    for (final var extention: extensions.keySet()) {
      final var name = "Select " + extention.toUpperCase() + " File";
      final var filter = new FileChooser.ExtensionFilter(name, "*." + extention);
      result.add(filter);
    }
    return result;
  }

  @FXML
  private void saveAs() throws IOException {
    final var stage = (Stage)borderPane.getScene().getWindow();
    final var fileChooser = new FileChooser();
    final var filters = getExtentionFilters();
    fileChooser.getExtensionFilters().addAll(filters);
    final var file = fileChooser.showSaveDialog(stage);
    if (file == null) return;
    final var extention = FileSaver.extention(file.getName());
    if (!extensions.containsKey(extention)) return;
    final var saver = extensions.get(extention).getKey();
    saver.accept(file, stage, canvas);
  }

  @FXML
  private void open() {
    final var stage = (Stage)borderPane.getScene().getWindow();
    final var fileChooser = new FileChooser();
    final var filters = getExtentionFilters();
    fileChooser.getExtensionFilters().addAll(filters);
    final var file = fileChooser.showOpenDialog(stage);
    if (file == null) return;
    final var extention = FileSaver.extention(file.getName());
    if (!extensions.containsKey(extention)) return;
    final var opener = extensions.get(extention).getValue();
    opener.accept(file, stage, canvas);
  }

  @FXML
  private void initialize() {
    final var editor = Editor.getInstance().start(canvas, borderPane);
    choiceWidth.getItems().addAll(widths);
    choiceWidth.setValue(widths.get(0));
    canvas.widthProperty().bind(canvasPain.widthProperty());
    canvas.heightProperty().bind(canvasPain.heightProperty());
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
