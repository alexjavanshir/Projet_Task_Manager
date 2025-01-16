package org.example.ex02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModifierTache {
    private int tacheIndex = -1;
    private String projetIntitule;
    @FXML private TextField newNom;
    @FXML private TextField newDescription;
    @FXML private ComboBox<String> newMembre;
    @FXML private ComboBox<String> newStatut;
    @FXML private TextField ancienNom;
    @FXML private TextField ancienDescription;
    @FXML private TextField ancienMembre;
    @FXML private TextField ancienStatut;
    @FXML private Label messageLabel;
    private GestionnaireTache gestionnaireTacheController;

    @FXML
    public void initialize() {
        newStatut.getItems().addAll("À faire", "En cours", "Terminée");
    }

    public void setGestionnaireTacheController(GestionnaireTache controller) {
        this.gestionnaireTacheController = controller;
    }

    public void setTacheDetails(String projetIntitule, String nom, String description, String membre, String statut, int tacheIndex) {
        this.projetIntitule = projetIntitule;
        ancienNom.setText(nom);
        ancienDescription.setText(description);
        ancienMembre.setText(membre);
        ancienStatut.setText(statut);
        this.tacheIndex = tacheIndex;

        chargerMembres(membre);
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
    public void modifierTache(ActionEvent event) {
        if (newNom.getText().isEmpty() || newDescription.getText().isEmpty() ||
                newMembre.getValue() == null || newStatut.getValue() == null) {
            messageLabel.setText("Veuillez remplir tous les champs");
            return;
        }
        try {
            String fileName = projetIntitule.replaceAll("\\s", "")
                    .replaceAll("é", "e")
                    .replaceAll("è", "e");
            String filePath = "src/main/resources/data/" + fileName + ".csv";

            gestionnaireTacheController.nomsTache.set(tacheIndex, newNom.getText());
            gestionnaireTacheController.descriptionsTache.set(tacheIndex, newDescription.getText());
            gestionnaireTacheController.membresTache.set(tacheIndex, newMembre.getValue());
            gestionnaireTacheController.statutsTache.set(tacheIndex, newStatut.getValue());

            List<String> lines = getStrings(filePath);

            try (FileWriter writer = new FileWriter(filePath)) {
                for (String line : lines) {
                    writer.write(line + "\n");
                }
            }
            fermer();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de la modification de la tâche");
        }
    }

    private List<String> getStrings(String filePath) throws IOException {
        List<String> lines = new java.util.ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (currentIndex == tacheIndex) {
                    lines.add(String.format("%s,%s,%s,%s\n", newNom.getText(), newDescription.getText(), newMembre.getValue(), newStatut.getValue()));
                } else {
                    lines.add(line);
                }
                currentIndex++;
            }
        } catch (IOException e){
            messageLabel.setText("Erreur lors de la réécriture du fichier");
        }
        return lines;
    }

    @FXML
    public void fermer() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}