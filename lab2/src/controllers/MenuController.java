package controllers;

import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import editors.*;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class MenuController {

  @FXML
  private BorderPane borderPane;

  @FXML
  private Menu objectsMenu;

  @FXML
  private AnchorPane anchorPane;
  
  private final List<Pair<Menu, List<RadioMenuItem>>> menuItems = new ArrayList<>();

  private boolean isPrimary(final MouseEvent event) {
    return event.getButton().equals(MouseButton.PRIMARY);
  }

  private void processEvent(Editor editor) {
    anchorPane.setOnMousePressed((event) -> {
      if (isPrimary(event)) editor.onLeftButtonDown(event.getX(), event.getY());
    });
    anchorPane.setOnMouseDragged((event) -> {
      if (isPrimary(event)) editor.onMouseMove(event.getX(), event.getY());
    });
    anchorPane.setOnMouseReleased((event) -> {
      if (isPrimary(event)) editor.onLeftButtonUp(event.getX(), event.getY());
    });
  }

  @FXML
  private void rectangleCenter(final ActionEvent event) {
    processEvent(new RectangleCenterEditor(anchorPane));
  }

  @FXML
  private void rectangleAngle(final ActionEvent event) {
    processEvent(new RectangleAngleEditor(anchorPane));
  }

  @FXML
  private void elipse(final ActionEvent event) {

  }

  @FXML
  private void line(final ActionEvent event) {
    processEvent(new LineEditor(anchorPane));
  }

  @FXML
  private void exit() {
    Platform.exit();
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

  @FXML
  private void initialize() {
    buildMenuItems(objectsMenu);
    for (final var pair: menuItems) {
      final var menu = pair.getKey();
      menu.setOnAction((action) -> {
        if (action.getTarget() instanceof Menu) return;
        final var selected = (RadioMenuItem)action.getTarget();
        final var text = selected.getText();
        makeItemsUnique(text);
      });
    }
  }
}
