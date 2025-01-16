package org.example.ex02;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Accueil {
    @FXML public Button boutton_employes;
    @FXML public Button boutton_projets;
    @FXML public Button boutton_calendrier;

    @FXML
    private void openEmployes() {
        Open openemployes = new Open();
        openemployes.open("employes", "Liste des employes", false, Employes.class);
    }
    @FXML
    private void openProjets() {
        Open openprojet = new Open();
        openprojet.open("projets", "Liste des projets", false, Projets.class);
    }
    @FXML
    private void openCalendrier() {
        Open opencalendrier = new Open();
        opencalendrier.open("calendrier", "Calendrier", false, Calendrier.class);
    }
}


