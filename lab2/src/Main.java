import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  final private String pathToView = "./resources/Main.fxml";
  final private String title = "Lab 2";

  static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}