package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {
  final private String pathToDialg = "../resources/Dialog.fxml";
  final private String title = "Enter data";

  @FXML
  private BorderPane borderPane;

  @FXML
  private void close() {
    Platform.exit();
  }

  @FXML
  private void start() throws Exception {
    final var root = borderPane.getScene().getWindow();
    final var gui = (Parent)FXMLLoader.load(getClass().getResource(pathToDialg));
    final var scene = new Scene(gui);
    final var stage = new Stage();
    stage.setScene(scene);
    stage.initOwner(root);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setTitle(title);
    stage.show();
  }
}
