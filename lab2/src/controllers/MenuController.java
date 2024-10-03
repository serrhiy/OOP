package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import editors.*;

public class MenuController {

  @FXML
  private BorderPane borderPane;

  @FXML
  private Menu objectsMenu;

  @FXML
  private AnchorPane anchorPane;

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
  private void rectangle(final ActionEvent event) {
    processEvent(new RectangleEditor(anchorPane));
  }

  @FXML
  private void elipse(final ActionEvent event) {

  }

  @FXML
  private void line(final ActionEvent event) {
    processEvent(new LineEditor(anchorPane));
  }

  @FXML
  public void initialize() {
    objectsMenu.setOnAction((action) -> {
      final var selected = (MenuItem)action.getTarget();
      final var id = selected.getText();
      for (final MenuItem item: objectsMenu.getItems()) {
        final var radioItem = (RadioMenuItem)item;
        final var isSelected = radioItem.isSelected();
        final var equals = radioItem.getText().equals(id);
        if (isSelected && !equals) radioItem.setSelected(false);
      }
    });
  }
}