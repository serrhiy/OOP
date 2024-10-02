import javafx.application.Application;
// import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {
  final private String pathToView = "./resources/Main.fxml";
  final private String title = "Lab 2";

  static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final Parent parent = FXMLLoader.load(getClass().getResource(pathToView));
    final var scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}