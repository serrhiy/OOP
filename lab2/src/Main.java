import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import canvas.Canvas;

public class Main extends Application {
  final private String pathToView = "./resources/Main.fxml";
  final private String title = "Lab 2";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    final var pane = (AnchorPane)root.getCenter();
    final var canvas = (javafx.scene.canvas.Canvas)pane.getChildren().getFirst();
    canvas.widthProperty().bind(pane.widthProperty());
    canvas.heightProperty().bind(pane.heightProperty());
    Canvas.setCanvas(canvas);
    scene.setOnKeyPressed((event) -> {
      if (event.isControlDown() && (event.getCode() == KeyCode.Z)) Canvas.pop();
    });
    stage.setWidth(960);
    stage.setHeight(720);
    stage.setScene(scene);
    stage.setTitle(title);

    stage.show();
  }
}
