package controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DialogController {
  private final List<String> defaultArgs = List.of(
    "java",
    "--module-path=/home/serhii/programming/code/java/libs/javafx/lib:/home/serhii/programming/code/java/libs/javax/lib/",
    "--add-modules=javafx.controls,javafx.fxml,javafx.swing,org.json",
    "Main"
  );
  @FXML private TextField widthField;
  @FXML private TextField heightField;
  @FXML private Pane pane;

  @FXML
  private void ok() {
    final var widthInput = widthField.getText();
    final var heightInput = heightField.getText();
    try {
      final var width = Integer.parseInt(widthInput);
      final var height = Integer.parseInt(heightInput);
      if (width <= 0 || height <= 0) return;
      final var args = new ArrayList<>(List.copyOf(defaultArgs));
      args.add(String.valueOf(width));
      args.add(String.valueOf(height));
      final var builder = new ProcessBuilder(args);
      builder.redirectErrorStream(true);
      builder.start();
      cancel();
    } catch (final Exception exception) {
      
    }

  }

  @FXML
  private void cancel() {
    final var root = (Stage)pane.getScene().getWindow();
    root.close();
  }
}
