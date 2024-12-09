import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import org.json.JSONObject;

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
    stage.setTitle(title);
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(title);
    stage.show();
    CompletableFuture.runAsync(() -> {
      final var isReader = new InputStreamReader(System.in);
      try (final var reader = new BufferedReader(isReader)) {
        while (true) {
          final var line = reader.readLine();
          if (line == null) continue;
          final var json = new JSONObject(line);
          if (json.getString("service").equals("close")) {
            Platform.runLater(stage::close);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
