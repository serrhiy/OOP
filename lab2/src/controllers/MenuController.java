package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import settings.Color;
import settings.Fill;
import editors.*;
import java.util.Map;
import java.util.HashMap;

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

  private final List<Pair<Menu, List<RadioMenuItem>>> menuItems = new ArrayList<>();
  private final Map<RadioMenuItem, Class<? extends Editor>> editors = new HashMap<>();

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

  @FXML
  private void initialize() {
    createObjectsMenu();
    addColors();
    for (final var pair: menuItems) {
      final var menu = pair.getKey();
      menu.setOnAction((event) -> {
        final var target = event.getTarget();
        if (target instanceof Menu) return;
        final var selected = (RadioMenuItem)target;
        if (lastSelected != null) lastSelected.setSelected(false);
        selected.setSelected(true);
        lastSelected = selected;
        final var window = (Stage)borderPane.getScene().getWindow();
        final var fullPath = getFullName(selected, objectsMenu);
        window.setTitle(fullPath);
        final var constructor = editors.get(selected);
        try {
          Editor editor = constructor.getDeclaredConstructor(Pane.class).newInstance(anchorPane);
          processEvent(editor, selected);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }
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

  private void createObjectsMenu() {
    final var rectangleMenu = new Menu("Rectangle");
    final var rectangleCenter = new RadioMenuItem("From center");
    final var rectangleCorner = new RadioMenuItem("From corner");
    rectangleMenu.getItems().addAll(rectangleCenter, rectangleCorner);
    final var elipseMenu = new Menu("Rectangle");
    final var elipseCenter = new RadioMenuItem("From center");
    final var elipseCorner = new RadioMenuItem("From corner");
    elipseMenu.getItems().addAll(elipseCenter, elipseCorner);
    final var line = new RadioMenuItem("Line");
    final var point = new RadioMenuItem("Point");
    final var brush = new RadioMenuItem("Brush");
    objectsMenu.getItems().addAll(rectangleMenu, elipseMenu, line, point, brush);
    menuItems.add(new Pair<Menu, List<RadioMenuItem>>(rectangleMenu, List.of(rectangleCenter, rectangleCorner)));
    menuItems.add(new Pair<Menu, List<RadioMenuItem>>(elipseMenu, List.of(elipseCenter, elipseCorner)));
    menuItems.add(new Pair<Menu, List<RadioMenuItem>>(objectsMenu, List.of(line, point, brush)));
    editors.put(rectangleCenter, RectangleCenterEditor.class);
    editors.put(rectangleCorner, RectangleCornerEditor.class);
    editors.put(elipseCenter, ElipseCenterEditor.class);
    editors.put(elipseCorner, ElipseCornerEditor.class);
    editors.put(line, LineEditor.class);
    editors.put(point, PointEditor.class);
    editors.put(brush, BrushEditor.class);
  }
}
