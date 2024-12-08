package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogController {
  @FXML
  private AnchorPane anchorPane;

  @FXML
  private TextField nField;

  @FXML
  private TextField minField;

  @FXML
  private TextField maxField;

  @FXML
  private void cancel() {
    final var window = (Stage)anchorPane.getScene().getWindow();
    window.close();
  }

  @FXML
  private void ok() {
    final var n = nField.getText();
    final var min = minField.getText();
    final var max = maxField.getText();
    
  }
}
