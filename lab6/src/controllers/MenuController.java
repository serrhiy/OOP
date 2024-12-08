package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuController {

  @FXML
  private void close() {
    Platform.exit();
  }

  @FXML
  private void start() {
    
  }
}
