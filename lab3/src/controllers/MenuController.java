package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import java.util.ArrayList;
import settings.Color;
import settings.Fill;
import editors.*;
import java.util.Map;

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
  private ToolBar toolBar;

  private final Map<String, Class<? extends Editor>> editors = Map.of(
    "rectangleCenter", RectangleCenterEditor.class,
    "rectangleCorner", RectangleCornerEditor.class,
    "ellipseCenter", ElipseCenterEditor.class,
    "ellipseCorner", ElipseCornerEditor.class,
    "line", LineEditor.class,
    "point", PointEditor.class,
    "brush", BrushEditor.class
  );

  private boolean isPrimary(final MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY);
  }

  private void processEvent(final Editor editor, final RadioMenuItem item) {
    anchorPane.setOnMousePressed((event) -> {
      if (isPrimary(event) && item.isSelected()) {
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
        processEvent(getEditor(item.getId()), item);
      }
    });
  }

  @FXML
  private void exit() {
    Platform.exit();
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

  private Editor getEditor(final String id) {
    final var constructor = editors.get(id);
    try {
      final var declared = constructor.getDeclaredConstructor();
      final var editor = declared.newInstance();
      return editor;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

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
        final var editor = getEditor(selected.getId());
        processEvent(editor, selected);
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
    addColors();
    addItemsEvenets(objectsMenu);
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