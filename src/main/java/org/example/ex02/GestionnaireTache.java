package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GestionnaireTache {

    private int projetIndex = -1;
    @FXML
    public Button boutton_fermer;
    @FXML
    public Button boutton_ajoutertache;
    @FXML
    public Button boutton_modifiertache;
    @FXML
    public Button boutton_supprimertache;

    @FXML
    public Label messageLabel;
    @FXML
    public Label projetLabel;
    @FXML
    public ListView<String> nomTache;
    @FXML
    public ListView<String> descriptionTache;
    @FXML
    public ListView<String> membreTache;
    @FXML
    public ListView<String> statutTache;

    private Projets projetsController;

    public void setProjetsController(Projets controller){
        this.projetsController = controller;
    }

    public void setProjetsDetails(String intitule, int index) {
        projetLabel.setText(intitule);
        projetIndex = index;
        chargerTaches(projetIndex);
    }

    private void chargerTaches(int projetIndex) {
        if (projetIndex < 0) {
            messageLabel.setText("Aucun projet sélectionné");
            return;
        }
        String projetIntitule = projetsController.projetIntituleList.get(projetIndex).replaceAll("\\s", "").replaceAll("é","e").replaceAll("è","e");
        String fichierTaches = "src/main/resources/data/"+projetIntitule + ".csv";

        ObservableList<String> nomsTache = FXCollections.observableArrayList();
        ObservableList<String> descriptionsTache = FXCollections.observableArrayList();
        ObservableList<String> membresTache = FXCollections.observableArrayList();
        ObservableList<String> statutsTache = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(fichierTaches))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",", -1);
                if (details.length >= 4) {
                    nomsTache.add(details[0]);
                    descriptionsTache.add(details[1]);
                    membresTache.add(details[2]);
                    statutsTache.add(details[3]);
                }
            }
            nomTache.setItems(nomsTache);
            descriptionTache.setItems(descriptionsTache);
            membreTache.setItems(membresTache);
            statutTache.setItems(statutsTache);

        } catch (IOException e) {
            messageLabel.setText("Erreur lors du chargement des tâches pour ce projet");
        }
    }

    @FXML
    public void ajouterTache() {
    }
    @FXML
    public void modifierTache() {
    }
    @FXML
    public void supprimerTache() {
    }

    @FXML
    public void fermer(ActionEvent event){
        ((Stage) messageLabel.getScene().getWindow()).close();
    }
}
