import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  private final String pathToView = "./resources/Main.fxml";
  private final String titleMain = "Raster graphic editor";

  static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(titleMain);
    scene.setFill(Color.WHITE);
    stage.show();
  }
}
