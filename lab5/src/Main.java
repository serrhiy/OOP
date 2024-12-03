import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;

public class Main extends Application {
  private final String pathToView = "./resources/Main.fxml";
  private final String pathToViewTable = "./resources/Table.fxml";
  private final String titleMain = "Lab 5";
  private final String titleTable = "Table";
  private final double width = 900;
  private final double height = 900;


  static void main(String[] args) {
    launch(args);
  }

  public Stage startTable() throws Exception {
    final var stage = new Stage();
    final ScrollPane root = FXMLLoader.load(getClass().getResource(pathToViewTable));
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(titleTable);
    stage.setWidth(width);
    stage.setHeight(height);
    stage.show();
    return stage;
  }

  @Override
  public void start(Stage stage) throws Exception {
    final BorderPane root = FXMLLoader.load(getClass().getResource(pathToView));
    final Scene scene = new Scene(root);
    final var pane = (AnchorPane)((BorderPane)root.getCenter()).getCenter();
    stage.setScene(scene);
    pane.setPrefWidth(width);
    pane.setPrefHeight(height);
    stage.setTitle(titleMain);
    final var tableStage = startTable();
    tableStage.setOnCloseRequest((_) -> { stage.close(); });
    stage.setOnCloseRequest((_) -> { tableStage.close(); });
    stage.show();
  }
}