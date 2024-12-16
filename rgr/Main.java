import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  private final String pathToView = "./resources/Main.fxml";
  private final String titleMain = "Raster graphic editor";
  private final double width = 800;
  private final double height = 650;

  static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setScene(scene);
    stage.setTitle(titleMain);
    scene.setFill(Color.WHITE);
    stage.show();
  }
}
