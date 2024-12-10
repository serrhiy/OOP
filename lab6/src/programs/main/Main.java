import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import listeners.InputStreamListener;

public class Main extends Application {

  private final double width = 900;
  private final double height = 900;
  private final String pathToView = "./resources/Main.fxml";
  private final String title = "Lab6";

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    stage.setScene(new Scene(root));
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(title);
    stage.show();
    final var listener = InputStreamListener.getInstance();
    listener.on("close", (_) -> Platform.runLater(stage::close));
  }
}
