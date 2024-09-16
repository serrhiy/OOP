package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Work1Controller implements Initializable {
  
  @FXML
  private ListView<String> listView;
  @FXML
  private AnchorPane anchorPane;

  private final String[] groups = { "IM-31", "IM-32", "IM-33", "IM-34" };
  private final String titleText = "Обрана група:";
  private String selectedGroup;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listView.getItems().addAll(groups);
    listView.getSelectionModel().selectedItemProperty().addListener((event) -> {
      selectedGroup = listView.getSelectionModel().getSelectedItem();
    });
  }

  @FXML
  private void cencelButton(final ActionEvent event) {
    final var root = (Stage)anchorPane.getScene().getWindow();
    root.close();
  }

  @FXML
  private void yesButton(final ActionEvent event) {
    if (selectedGroup == null) {
      cencelButton(event);
      return;
    }
    final var root = (Stage)anchorPane.getScene().getWindow();
    final var owner = (Stage)root.getOwner();
    final var anchorPane = (AnchorPane)(((BorderPane)owner.getScene().getRoot()).getCenter());
    final var title = (Label)anchorPane.getChildren().getFirst();
    final var text = (Label)anchorPane.getChildren().getLast();
    anchorPane.setVisible(true);
    text.setText(selectedGroup);
    title.setText(titleText);
    root.close();
  }
}
