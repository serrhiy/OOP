package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import settings.Color;
import settings.Fill;
import editors.*;

public class MenuController {

  @FXML
  private BorderPane borderPane;

  @FXML
  private Menu objectsMenu;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private Menu colors;
  
  private final List<Pair<Menu, List<RadioMenuItem>>> menuItems = new ArrayList<>();

  private boolean isPrimary(final MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY);
  }

  private void processEvent(final Editor editor, final ActionEvent parentEvent) {
    final var item = (RadioMenuItem)(parentEvent.getSource());
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
  private void rectangleCenter(final ActionEvent event) {
    processEvent(new RectangleCenterEditor(anchorPane), event);
  }

  @FXML
  private void rectangleAngle(final ActionEvent event) {
    processEvent(new RectangleCornerEditor(anchorPane), event);
  }

  @FXML
  private void elipseCenter(final ActionEvent event) {
    processEvent(new ElipseCenterEditor(anchorPane), event);
  }

  @FXML
  private void elipseAngle(final ActionEvent event) {
    processEvent(new ElipseCornerEditor(anchorPane), event);
  }

  @FXML
  private void line(final ActionEvent event) {
    processEvent(new LineEditor(anchorPane), event);
  }

  @FXML
  private void brush(final ActionEvent event) {
    processEvent(new BrushEditor(anchorPane), event);
  }

  @FXML
  private void point(final ActionEvent event) {
    processEvent(new PointEditor(anchorPane), event);
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

  private void makeItemsUnique(final String text) {
    for (final var pair: menuItems) {
      final var items = pair.getValue();
      for (final var item: items) {
        final var selected = item.isSelected();
        final var equals = item.getText().equals(text);
        if (selected && !equals) item.setSelected(false);
      }
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

  private void buildMenuItems(final Menu root) {
    final List<RadioMenuItem> items = new ArrayList<>();
    for (final MenuItem item: root.getItems()) {
      if (item instanceof final Menu menu) {
        buildMenuItems(menu);
        continue;
      }
      items.addLast((RadioMenuItem)item);
    }
    menuItems.addLast(new Pair<Menu,List<RadioMenuItem>>(root, items));
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
    buildMenuItems(objectsMenu);
    addColors();
    for (final var pair: menuItems) {
      final var menu = pair.getKey();
      menu.setOnAction((action) -> {
        if (action.getTarget() instanceof Menu) return;
        final var selected = (RadioMenuItem)action.getTarget();
        final var text = selected.getText();
        makeItemsUnique(text);
        final var window = (Stage)borderPane.getScene().getWindow();
        final var fullPath = getFullName(selected, objectsMenu);
        window.setTitle(fullPath);
      });
    }
  }
}
