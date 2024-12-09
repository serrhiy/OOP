import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

  private final String pathToView = "./resources/Main.fxml";
  private final String title = "Generator";

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final Parent root = FXMLLoader.load(getClass().getResource(pathToView));
    stage.setScene(new Scene(root));
    stage.setTitle(title);
    stage.show();
  }
}
