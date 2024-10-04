import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import settings.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Main extends Application {
  final private String pathToView = "./resources/Main.fxml";
  final private String title = "Lab 2";

  static void main(String[] args) {
    launch(args);
  }

  private void addColors(final BorderPane root) {
    final var menuBar = (MenuBar)root.getTop();
    final var items = new ArrayList<MenuItem>();
    for (final var color: Color.getStringColors()) {
      items.addLast(new MenuItem(color));
    }
    final var colorsMenu = menuBar.getMenus().filtered((menu) -> {
      return menu.getText().toLowerCase().equals("colors");
    }).getFirst();
    colorsMenu.getItems().addAll(items);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    final var pane = (AnchorPane)root.getCenter();
    scene.setOnKeyPressed((event) -> {
      if (event.isControlDown() && (event.getCode() == KeyCode.Z)) {
        final var childers = pane.getChildren();
        if (childers.size() > 0) pane.getChildren().removeLast();
      }
    });
    addColors(root);  
    stage.setWidth(960);
    stage.setHeight(720);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}
