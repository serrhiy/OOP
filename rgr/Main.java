import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  private final String pathToView = "./resources/Main.fxml";
  private final String titleMain = "Raster graphic editor";
  private final double width = 900;
  private final double height = 900;


  static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    final var pane = (AnchorPane)((BorderPane)root.getCenter()).getCenter();
    stage.setScene(scene);
    pane.setPrefWidth(width);
    pane.setPrefHeight(height);
    stage.setTitle(titleMain);
    stage.show();
  }
}
