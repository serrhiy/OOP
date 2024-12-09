
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import java.util.Map;
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
    final AnchorPane root = FXMLLoader.load(getClass().getResource(pathToView));
    stage.setScene(new Scene(root));
    stage.setTitle(title);
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(title);
    stage.show();
    final var listener = InputStreamListener.getInstance();
    listener.on("data", (json) -> {
      final var message = Map.of(
        "receiver", "manager",
        "service", "log",
        "data", json.toString()
      );
      System.out.print(new JSONObject(message).toString());
    });
  }
}
