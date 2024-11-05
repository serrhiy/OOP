import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  final private String pathToView = "./resources/Main.fxml";
  final private String title = "Lab 3";

  static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    final var pane = (AnchorPane)((BorderPane)root.getCenter()).getCenter();
    final var canvas = (javafx.scene.canvas.Canvas)pane.getChildren().getFirst();
    canvas.widthProperty().bind(pane.widthProperty());
    canvas.heightProperty().bind(pane.heightProperty());
    // scene.setOnKeyPressed((event) -> {
    //   if (event.isControlDown() && (event.getCode() == KeyCode.Z)) Canvas.pop();
    // });
    stage.setWidth(960);
    stage.setHeight(720);
    stage.setScene(scene);
    stage.setTitle(title);
    stage.show();
  }
}