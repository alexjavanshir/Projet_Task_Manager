package org.example.ex02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Accueil {
    @FXML
    public Button boutton_employes;
    @FXML
    public Button boutton_projets;


    @FXML
    private void openEmployes() throws IOException {
        Open openemployes = new Open();
        openemployes.open("employes", "Liste des employes", false, Employes.class);
    }
    @FXML
    private void openProjets() throws IOException {
        Open openprojet = new Open();
        openprojet.open("projets", "Liste des projets", false, Projets.class);
    }
}


