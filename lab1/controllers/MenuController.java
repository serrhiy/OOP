package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.layout.BorderPane;

public class MenuController {

  @FXML
  private BorderPane borderPane;

  private void makeModal(final String path, final String title) throws Exception {
    final var root = borderPane.getScene().getWindow();
    final Parent gui = FXMLLoader.load(getClass().getResource(path));
    final var scene = new Scene(gui);
    final var stage = new Stage();
    stage.setScene(scene);
    stage.initOwner(root);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setTitle(title);
    stage.show();
  }

  @FXML
  private void work1(final Event event) throws Exception {
    makeModal("../resources/Work1.fxml", "Робота 1");
  }

  @FXML
  private void work2(final Event event) throws Exception {
    makeModal("../resources/Work2.fxml", "Робота 2");
  }
}
