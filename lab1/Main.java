import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
  private final String title = "Lab1";
  private final String pathToView = "resources/Main.fxml";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start (final Stage stage) throws Exception {
    final Parent root = FXMLLoader.load(getClass().getResource(pathToView));
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}
