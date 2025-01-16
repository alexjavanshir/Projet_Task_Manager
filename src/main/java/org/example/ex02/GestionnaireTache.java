package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireTache {
    private int projetIndex = -1;
    @FXML public Button boutton_fermer;
    @FXML public Button boutton_ajoutertache;
    @FXML public Button boutton_modifiertache;
    @FXML public Button boutton_supprimertache;

    @FXML public Label messageLabel;
    @FXML public Label projetLabel;
    @FXML public ListView<String> nomTache;
    @FXML public ListView<String> descriptionTache;
    @FXML public ListView<String> membreTache;
    @FXML public ListView<String> statutTache;
    final ObservableList<String> nomsTache = FXCollections.observableArrayList();
    final ObservableList<String> descriptionsTache = FXCollections.observableArrayList();
    final ObservableList<String> membresTache = FXCollections.observableArrayList();
    final ObservableList<String> statutsTache = FXCollections.observableArrayList();
    private Projets projetsController;

    public void setProjetsController(Projets controller) {
        this.projetsController = controller;
    }

    public void setProjetsDetails(String intitule, int index) {
        projetLabel.setText(intitule);
        projetIndex = index;
        chargerTaches(projetIndex);
    }

    private String getTachesFilePath() {
        String projetIntitule = projetsController.projetIntituleList.get(projetIndex)
                .replaceAll("\\s", "")
                .replaceAll("é", "e")
                .replaceAll("è", "e");
        return "src/main/resources/data/" + projetIntitule + ".csv";
    }

    private void chargerTaches(int projetIndex) {
        if (projetIndex < 0) {
            messageLabel.setText("Aucun projet sélectionné");
            return;
        }

        String fichierTaches = getTachesFilePath();

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
        Open opener = new Open();
        AjouterTache ajouterTacheController = opener.open("ajouterTache", "Ajouter une tâche", true, AjouterTache.class);
        if (ajouterTacheController != null) {
            ajouterTacheController.setGestionnaireTacheController(this);
            ajouterTacheController.setProjetDetails(projetLabel.getText(), nomsTache, descriptionsTache, membresTache, statutsTache, projetIndex);
        }
    }
    @FXML
    public void modifierTache() {
        int selectedIndex = nomTache.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Open opener = new Open();
            ModifierTache modifierTacheController = opener.open("modifierTache", "Modifier une tâche", true, ModifierTache.class);
            if (modifierTacheController != null) {
                modifierTacheController.setGestionnaireTacheController(this);
                modifierTacheController.setTacheDetails(
                        projetLabel.getText(),
                        nomTache.getItems().get(selectedIndex),
                        descriptionTache.getItems().get(selectedIndex),
                        membreTache.getItems().get(selectedIndex),
                        statutTache.getItems().get(selectedIndex),
                        selectedIndex
                );
            }
        } else {
            messageLabel.setText("Veuillez sélectionner une tâche à modifier");
        }
    }

    @FXML
    public void supprimerTache() {
        int selectedIndex = nomTache.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            nomsTache.remove(selectedIndex);
            descriptionsTache.remove(selectedIndex);
            membresTache.remove(selectedIndex);
            statutsTache.remove(selectedIndex);

            List<String[]> taches = new ArrayList<>();
            String fichierTaches = getTachesFilePath();

            try (BufferedReader reader = new BufferedReader(new FileReader(fichierTaches))) {
                String ligne;
                int currentIndex = 0;
                while ((ligne = reader.readLine()) != null) {
                    if (currentIndex != selectedIndex) {
                        taches.add(ligne.split(",", -1));
                    }
                    currentIndex++;
                }
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de la lecture des tâches");
                return;
            }
            try (FileWriter writer = new FileWriter(fichierTaches)) {
                for (String[] tache : taches) {
                    writer.write(String.join(",", tache) + "\n");
                }
                messageLabel.setText("Tâche supprimée avec succès");
                chargerTaches(projetIndex);
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de la suppression de la tâche");
            }
        } else {
            messageLabel.setText("Veuillez sélectionner une tâche à supprimer");
        }
    }

    @FXML
    public void fermer(ActionEvent event) {
        ((Stage) messageLabel.getScene().getWindow()).close();
    }
}