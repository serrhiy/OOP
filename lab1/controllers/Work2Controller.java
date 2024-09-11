package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Work2Controller {
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private TextField textField;

  private final String titleText = "Ви ввели:";

  @FXML
  private void yesButton(final ActionEvent event) {
    final String text = textField.getText();
    if (text.isEmpty()) {
      cencelButton(event);
      return;
    }
    final var root = (Stage)anchorPane.getScene().getWindow();
    final var owner = (Stage)root.getOwner();
    final var anchorPane = (AnchorPane)(((BorderPane)owner.getScene().getRoot()).getCenter());
    final var title = (Label)anchorPane.getChildren().getFirst();
    final var textField = (Label)anchorPane.getChildren().getLast();
    anchorPane.setVisible(true);
    textField.setText(text);
    title.setText(titleText);
    root.close();
  }

  @FXML
  private void cencelButton(final ActionEvent event) {
    final var root = (Stage)anchorPane.getScene().getWindow();
    root.close();
  }
}
