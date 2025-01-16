package org.example.ex02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModifierTache {
    @FXML private TextField ancienNom;
    @FXML private TextField ancienDescription;
    @FXML private TextField ancienMembre;
    @FXML private TextField ancienStatut;

    @FXML private TextField newNom;
    @FXML private TextField newDescription;
    @FXML private ComboBox<String> newMembre;
    @FXML private ComboBox<String> newStatut;

    @FXML private Label messageLabel;
    @FXML private Button boutton_modifier;
    @FXML private Button boutton_fermer;

    private GestionnaireTache gestionnaireTacheController;
    private int tacheIndex;
    private int projetIndex;
    private String projetIntitule;

    @FXML
    public void initialize() {
        newStatut.getItems().addAll("À faire", "En cours", "Terminée");
    }

    public void setGestionnaireTacheController(GestionnaireTache controller) {
        this.gestionnaireTacheController = controller;
    }

    public void setTacheDetails(String nom, String description, String membre, String statut, int tacheIndex, int projetIndex) {
        this.tacheIndex = tacheIndex;
        this.projetIndex = projetIndex;
        this.projetIntitule = gestionnaireTacheController.projetLabel.getText();

        ancienNom.setText(nom);
        ancienDescription.setText(description);
        ancienMembre.setText(membre);
        ancienStatut.setText(statut);
    }

    private void chargerMembres(String selectedMembre) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/employes.csv"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length >= 4 && details[3].trim().equals(projetIntitule.trim())) {
                    newMembre.getItems().add(details[0]);
                }
            }
            newMembre.setValue(selectedMembre);
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du chargement des membres");
        }
    }

    @FXML
    private void modifierTache(ActionEvent event) {
        if (newNom.getText().isEmpty()||newDescription.getText().isEmpty()||newMembre.getValue() == null||newStatut.getValue() == null) {
            try {
                String fileName = projetIntitule.replaceAll("\\s", "")
                        .replaceAll("é", "e")
                        .replaceAll("è", "e");
                String filePath = "src/main/resources/data/" + fileName + ".csv";

                List<String> lines = getStrings(filePath);

                try (FileWriter writer = new FileWriter(filePath)) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }

                messageLabel.setText("Tâche modifiée avec succès");
                gestionnaireTacheController.setProjetsDetails(projetIntitule, projetIndex);
                fermer();
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de la modification de la tâche");
            }
        }
    }

    private List<String> getStrings(String filePath) throws IOException {
        List<String> lines = new java.util.ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (currentIndex == tacheIndex) {
                    lines.add(String.format("%s,%s,%s",
                            newDescription.getText(),
                            newMembre.getValue(),
                            newStatut.getValue()));
                } else {
                    lines.add(line);
                }
                currentIndex++;
            }
        }
        return lines;
    }

    @FXML
    private void fermer() {
        ((Stage) messageLabel.getScene().getWindow()).close();
    }
}