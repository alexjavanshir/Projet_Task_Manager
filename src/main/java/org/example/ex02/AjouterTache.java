package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class AjouterTache {
    @FXML private TextField nomField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> membreComboBox;
    @FXML private ComboBox<String> statutComboBox;
    @FXML private Label messageLabel;
    @FXML private Button boutton_ajouterTCH;
    @FXML private Button boutton_fermer;

    private GestionnaireTache gestionnaireTacheController;
    private String projetIntitule;
    private int projetIndex;

    private ObservableList<String> nomsTache = FXCollections.observableArrayList();
    private ObservableList<String> descriptionsTache = FXCollections.observableArrayList();
    private ObservableList<String> membresTache = FXCollections.observableArrayList();
    private ObservableList<String> statutsTache = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        statutComboBox.getItems().addAll("À faire", "En cours", "Terminée");
    }

    public void setGestionnaireTacheController(GestionnaireTache controller) {
        this.gestionnaireTacheController = controller;
    }

    public void setProjetDetails(String intitule, ObservableList<String> nomTache,ObservableList<String> descriptionsTache, ObservableList<String> membresTache, ObservableList<String> statutsTache, int index) {
        this.projetIntitule = intitule;
        this.nomsTache = nomTache;
        this.descriptionsTache = descriptionsTache;
        this.membresTache = membresTache;
        this.statutsTache = statutsTache;
        this.projetIndex = index;
        chargerMembres();
    }

    private void chargerMembres() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/employes.csv"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length >= 4 && details[3].trim().equals(projetIntitule.trim())) {
                    membreComboBox.getItems().add(details[0]);
                }
            }
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du chargement des membres");
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        if (nomField.getText().isEmpty() || descriptionField.getText().isEmpty() || membreComboBox.getValue() == null || statutComboBox.getValue() == null) {
            messageLabel.setText("Veuillez remplir tous les champs.");
        } else {
            String fileName = projetIntitule.replaceAll("\\s", "")
                    .replaceAll("é", "e")
                    .replaceAll("è", "e");
            String filePath = "src/main/resources/data/" + fileName + ".csv";

            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(String.format("%s,%s,%s,%s%n",
                        nomField.getText(),
                        descriptionField.getText(),
                        membreComboBox.getValue(),
                        statutComboBox.getValue()
                ));
                if (nomsTache != null && descriptionsTache != null && membresTache != null && statutsTache != null) {
                    nomsTache.add(nomField.getText());
                    descriptionsTache.add(descriptionField.getText());
                    membresTache.add(membreComboBox.getValue());
                    statutsTache.add(statutComboBox.getValue());
                }
                messageLabel.setText("Tâche ajoutée avec succès");
                viderChamps();
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'ajout de la tâche");
            }
        }
    }
        private void viderChamps(){
            nomField.clear();
            descriptionField.clear();
            membreComboBox.getItems().clear();
            statutComboBox.getItems().clear();
        }

    @FXML
    private void fermer(ActionEvent event) {
        ((Stage) messageLabel.getScene().getWindow()).close();
    }
}

