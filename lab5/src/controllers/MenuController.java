package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import settings.Color;
import settings.Fill;
import shapes.*;
import java.util.Map;

import editors.Editor;

public class MenuController {

  @FXML
  private BorderPane borderPane;

  @FXML
  private Menu objectsMenu;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private Menu colors;

  @FXML
  private RadioMenuItem lastSelected = null;

  @FXML
  private Canvas canvas;

  @FXML
  private ToolBar toolBar;

  private final Map<String, Class<? extends Shape>> editors = Map.of(
    "rectangleCenter", RectangleCenter.class,
    "rectangleCorner", RectangleCorner.class,
    "ellipseCenter", EllipseCenter.class,
    "ellipseCorner", EllipseCorner.class,
    "line", Line.class,
    "point", Point.class,
    "brush", Brush.class,
    "line-ellipse", LineEllipse.class,
    "cube", Cube.class
  );

  private final Map<String, Class<? extends Shape>> shapes = Map.of(
    "Brush", Brush.class,
    "Cube", Cube.class,
    "Ellipse", EllipseCorner.class,
    "Line", Line.class,
    "LineEllipse", LineEllipse.class,
    "Point", Point.class,
    "Rectangle", RectangleCorner.class
  );

  private boolean isPrimary(final MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY);
  }

  private void processEvent(final Shape shape, final RadioMenuItem item) {
    final var editor = Editor.getInstance();
    anchorPane.setOnMousePressed((event) -> {
      if (isPrimary(event) && item.isSelected()) {
        editor.add(shape);
        editor.onLeftButtonDown(event.getX(), event.getY());
      }
    });
    anchorPane.setOnMouseDragged((event) -> {
      if (isPrimary(event) && item.isSelected()) {
        editor.onMouseMove(event.getX(), event.getY());
      }
    });
    anchorPane.setOnMouseReleased((event) -> {
      if (isPrimary(event) && item.isSelected()) {
        editor.onLeftButtonUp(event.getX(), event.getY());
        processEvent(getShape(item.getId()), item);
      }
    });
  }

  @FXML
  private void exit() {
    Platform.exit();
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
      final var shapes = Editor.getInstance().shapes();
      for (final var shape: shapes) {
        writer.write(shape.getName() + " ");
        final var coords = shape.getCoords();
        for (final var coord: coords) {
          writer.write(String.valueOf(coord) + " ");
        }
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
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
    final var fill =  Fill.getInstance().getFill();
    Fill.getInstance().setFill(!fill);
  }

  private void drawShape(String name, final List<Double> coords) {
    final var exists = shapes.containsKey(name);
    if (!exists) return;
    final var constructor = shapes.get(name);
    try {
      final var declared = constructor.getDeclaredConstructor(List.class);
      final var shape = declared.newInstance(coords);
      Editor.getInstance().addToCanvas(shape);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void open() {
    final var stage = (Stage)borderPane.getScene().getWindow();
    final var fileChooser = new FileChooser();
    final var extention = new FileChooser.ExtensionFilter("Text Files", "*.txt");
    fileChooser.getExtensionFilters().add(extention);
    final var file = fileChooser.showOpenDialog(stage);
    if (file == null) return;
    try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
      Editor.getInstance().reset();
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
        drawShape(name, numbers);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void addColors() {
    final var items = new ArrayList<MenuItem>();
    for (final var color: Color.getInstance().getStringColors()) {
      items.addLast(new MenuItem(color));
    }
    colors.getItems().addAll(items);
  }

  private Shape getShape(final String id) {
    final var constructor = editors.get(id);
    try {
      final var declared = constructor.getDeclaredConstructor();
      final var shape = declared.newInstance();
      return shape;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unused")
  private void addItemsEvenets(final Menu root) {
    for (final var item: root.getItems()) {
      if (item instanceof Menu menu) {
        addItemsEvenets(menu);
        continue;
      }
      final var selected = (RadioMenuItem)item;
      final var fullPath = getFullName(selected, objectsMenu);
      item.setOnAction((event) -> {
        if (lastSelected != null) lastSelected.setSelected(false);
        selected.setSelected(true);
        lastSelected = selected;
        final var window = (Stage)borderPane.getScene().getWindow();
        window.setTitle(fullPath);
        final var shape = getShape(selected.getId());
        processEvent(shape, selected);
      });
      final var buttonId = selected.getId() + "-button";
      final var button = (Button)toolBar.getItems().filtered((node) -> {
        return node.getId().equals(buttonId);
      }).getFirst();
      button.setOnAction((event) -> item.fire());
    }
  }

  @FXML
  private void initialize() {
    canvas.widthProperty().bind(anchorPane.widthProperty());
    canvas.heightProperty().bind(anchorPane.heightProperty());
    addColors();
    addItemsEvenets(objectsMenu);
    final var editor = Editor.getInstance().setCanvas(canvas);
    borderPane.sceneProperty().addListener((_) -> {
      final var scene = borderPane.getScene();
      scene.setOnKeyPressed((event) -> {
        if (event.isControlDown() && (event.getCode() == KeyCode.Z)) editor.pop();
    });
    });
  }

  private String getFullName(final MenuItem selected, final Menu root) {
    final StringBuilder result = new StringBuilder(root.getText() + " -> ");
    boolean find = false;
    for (final MenuItem item: root.getItems()) {
      if (item instanceof final Menu menu) {
        final var subpath = getFullName(selected, menu);
        if (subpath.length() == 0) continue;
        find = true;
        result.append(subpath);
        break;
      }
      if (!item.equals(selected)) continue;
      find = true;
      result.append(item.getText());
      break;
    }
    return find ? result.toString() : "";
  }
}
