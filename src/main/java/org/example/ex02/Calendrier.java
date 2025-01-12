package org.example.ex02;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Calendrier {
    @FXML
    private Button boutton_fermer;
    @FXML
    private Label titreLabel;

    @FXML
    public void fermer(){
        Stage stage = (Stage) titreLabel.getScene().getWindow();
        stage.close();
    }
}
