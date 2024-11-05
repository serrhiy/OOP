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
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.util.ArrayList;
import settings.Color;
import settings.Fill;
import shapes.*;
import java.util.Map;

import javax.imageio.ImageIO;

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

  private Editor editor;

  private final Map<String, Class<? extends Shape>> editors = Map.of(
    "rectangleCenter", RectangleCenter.class,
    "rectangleCorner", RectangleCorner.class,
    "ellipseCenter", EllipseCenter.class,
    "ellipseCorner", EllipseCorner.class,
    "line", Line.class,
    "point", Point.class,
    "brush", Brush.class,
    "line-ellipse", LineEllipse.class
  );

  private boolean isPrimary(final MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY);
  }

  private void processEvent(final Shape shape, final RadioMenuItem item) {
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
    final var writableImage = new WritableImage((int)stage.getWidth(), (int)stage.getHeight());
    canvas.snapshot(null, writableImage);
    final var renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
    ImageIO.write(renderedImage, "png", file);
  }

  @FXML
  private void colors(final ActionEvent event) {
    final var item = (MenuItem)event.getTarget();
    final var text = item.getText();
    Color.setColor(text);
  }

  @FXML
  private void fill() {
    final var fill =  Fill.getFill();
    Fill.setFill(!fill);
  }

  private void addColors() {
    final var items = new ArrayList<MenuItem>();
    for (final var color: Color.getStringColors()) {
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

  @SuppressWarnings("unused")
  @FXML
  private void initialize() {
    addColors();
    addItemsEvenets(objectsMenu);
    editor = new Editor(canvas);
    borderPane.sceneProperty().addListener((property) -> {
      final var scene = borderPane.getScene();
      scene.setOnKeyPressed((event) -> {
      if (event.isControlDown() && (event.getCode() == KeyCode.Z)) {
        editor.pop();
        editor.clear();
        editor.drawAll();
      };
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
