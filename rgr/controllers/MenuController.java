package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.ToolBar;
import javafx.scene.control.MenuItem;
import events.EventEmitter;
import java.util.Map;
import javafx.application.Platform;
import settings.Color;
import settings.Fill;
import shapes.*;
import editors.Editor;

public class MenuController {

  @FXML private BorderPane borderPane;
  @FXML private AnchorPane anchorPane;
  @FXML private Menu colors;
  @FXML private Canvas canvas;
  @FXML private ToolBar toolBar;

  @FXML
  private void exit() {
    Platform.exit();
  }

  private final Map<String, Class<? extends Shape>> shapes = Map.of(
    "line", Line.class,
    "ellipse", Ellipse.class,
    "rectangle", Rectangle.class
  );

  @FXML
  private void initialize() {
    canvas.widthProperty().bind(anchorPane.widthProperty());
    canvas.heightProperty().bind(anchorPane.heightProperty());
    final var items = toolBar.getItems();
    final var emitter = new EventEmitter<Class<? extends Shape>>();
    Editor.getInstance().start(canvas, emitter);
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
        emitter.emit("shape", Constructor);
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
    final var fill =  Fill.getInstance().getFill();
    Fill.getInstance().setFill(!fill);
  }
}
