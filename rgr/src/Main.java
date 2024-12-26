import java.util.List;

import controllers.MainController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  private final int width = 800;
  private final int height = 600;
  private final String pathToView = "./resources/Main.fxml";
  private final String titleMain = "Raster graphic editor";

  public static void main(String[] args) {
    launch(args);
  }

  private Pair<Integer, Integer> parseCanvasSize(final List<String> args) {
    if (args.size() != 2) return null;
    final var width = Integer.parseInt(args.get(0));
    final var height = Integer.parseInt(args.get(1));
    return new Pair<>(width, height);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final var loader = new FXMLLoader(getClass().getResource(pathToView));
    final Parent root = loader.load();
    final MainController controller = loader.getController();
    final var size = parseCanvasSize(getParameters().getUnnamed());
    final var canvasWidth = size == null ? width : size.getKey();
    final var canvasHeight = size == null ? height : size.getValue();
    controller.setCanvasSize(canvasWidth, canvasHeight, size != null);
    if (canvasHeight > height || canvasWidth > width) {
      stage.setWidth(width);
      stage.setHeight(height);
    }
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(titleMain);
    scene.setFill(Color.WHITE);
    stage.show();
  }
}
