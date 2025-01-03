package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToolBar;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import shapes.*;
import editors.Editor;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ScrollPane;

public class MainController {
  @FXML private BorderPane borderPane;
  @FXML private Canvas canvas;
  @FXML private ToolBar toolBar;
  @FXML private ColorPicker colorPicker;
  @FXML private ChoiceBox<Integer> choiceWidth;
  @FXML private ToggleButton fillButton;
  @FXML private ScrollPane scrollPane;
  @FXML private Text widthField;
  @FXML private Text heightField;
  @FXML private Text shapeField;
  private File background = null;

  private Editor editor;
  private ChangeListener<? super Number> widthListener = (_, _, width) -> {
    final var canvasWidth = width.intValue() - 2;
    canvas.setWidth(canvasWidth);
    widthField.setText(String.valueOf(canvasWidth));
  };
  private ChangeListener<? super Number> heightListener = (_, _, height) -> {
    final var canvasHeight = height.intValue() - 2;
    canvas.setHeight(canvasHeight);
    heightField.setText(String.valueOf(canvasHeight));
  };

  private final Map<String, Class<? extends Shape>> shapes = Map.of(
    "line", Line.class,
    "ellipse", Ellipse.class,
    "rectangle", Rectangle.class,
    "brush", Brush.class,
    "directedLine", DirectedLine.class,
    "bidirectedLine", BiDirectedLine.class
  );

  private final Map<String, Pair<BiConsumer<File, Editor>, BiFunction<File, Editor, Pair<Double, Double>>>> extensions = Map.of(
    "json", new Pair<>(FileSaver::jsonSave, FileSaver::jsonOpen),
    "png", new Pair<>(FileSaver::pngSave, FileSaver::binaryOpen),
    "jpg", new Pair<>(FileSaver::jpgSave, FileSaver::binaryOpen)
  );

  private final List<Integer> widths = List.of(1, 2, 3, 4, 5, 6, 7, 8);

  @FXML
  private void exit() {
    Platform.exit();
  }

  @FXML
  private void create() {
    try {
      final var view = "../resources/EnterCanvasSize.fxml";
      final var root = borderPane.getScene().getWindow();
      final var gui = (Parent)FXMLLoader.load(getClass().getResource(view));
      final var scene = new Scene(gui);
      final var stage = new Stage();
      stage.setScene(scene);
      stage.initOwner(root);
      stage.initModality(Modality.WINDOW_MODAL);
      stage.setTitle("Enter Canvas Size");
      stage.show();
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }

  public void setCanvasSize(final int width, final int height, final boolean fixed) {
    canvas.setWidth(width);
    canvas.setHeight(height);
    heightField.setText(Integer.toString(height));
    widthField.setText(Integer.toString(width));
    if (!fixed) return;
    scrollPane.widthProperty().removeListener(widthListener);
    scrollPane.heightProperty().removeListener(heightListener);
  }

  @FXML
  private void changeWidth(final ActionEvent event) {
    final var width = choiceWidth.getValue();
    editor.changeWidth(width);
  }

  @FXML
  private void onFill(final ActionEvent event) {
    final var selected = fillButton.isSelected();
    final var text = selected ? "Fill" : "No fill";
    fillButton.setText(text);
    editor.setFill(selected);
  }

  @FXML
  private void changeColor(final ActionEvent event) {
    final var color = colorPicker.getValue();
    editor.changeColor(color);
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
  private void save() throws IOException {
    if (background == null) {
      saveAs();
      return;
    }
    final var extention = FileSaver.extention(background.getName());
    if (!extensions.containsKey(extention)) return;
    final var saver = extensions.get(extention).getKey();
    saver.accept(background, editor);
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
    saver.accept(file, editor);
    this.background = file;
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
    scrollPane.widthProperty().removeListener(widthListener);
    scrollPane.heightProperty().removeListener(heightListener);
    final var opener = extensions.get(extention).getValue();
    final var size = opener.apply(file, editor);
    widthField.setText(String.valueOf(size.getKey().intValue()));
    heightField.setText(String.valueOf(size.getValue().intValue()));
    this.background = file;
  }

  @FXML
  private void initialize() {
    scrollPane.widthProperty().addListener(widthListener);
    scrollPane.heightProperty().addListener(heightListener);
    this.editor = new Editor(canvas, borderPane);
    choiceWidth.getItems().addAll(widths);
    choiceWidth.setValue(widths.get(0));
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
        shapeField.setText(name);
      });
    }
  }
}
