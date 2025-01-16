package org.example.ex02;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Calendrier {

    @FXML private Label titreLabel;

    @FXML
    public void fermer(){
        Stage stage = (Stage) titreLabel.getScene().getWindow();
        stage.close();
    }
}
