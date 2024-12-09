package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

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
    try {
      final var json = new JSONObject();
      json.put("service", "create");
      json.put("receiver", "generator");
      final var data = new JSONObject();
      data.put("n", n);
      data.put("min", min);
      data.put("max", max);
      json.put("data", data);
      System.out.print(json.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
