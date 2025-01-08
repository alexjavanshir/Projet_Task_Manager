package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Projets {
    @FXML public Button boutton_openajouterPRJ;
    @FXML public Button boutton_openmodifierPRJ;
    @FXML public Button boutton_supprimerPRJ;
    @FXML private ListView<String> listViewIntitule;
    @FXML private ListView<String> listViewResponsable;
    @FXML private ListView<LocalDate> listViewDateDebut;
    @FXML private ListView<LocalDate> listViewDateFin;
    @FXML private ListView<String> listViewMembres;
    @FXML private Label messageLabel;

    private final ObservableList<String> projetIntituleList = FXCollections.observableArrayList();
    private final ObservableList<String> projetResponsableList = FXCollections.observableArrayList();
    private final ObservableList<LocalDate> projetDateDebutList = FXCollections.observableArrayList();
    private final ObservableList<LocalDate> projetDateFinList = FXCollections.observableArrayList();
    private final ObservableList<List<Membre>> projetMembresList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listViewIntitule.setItems(projetIntituleList);
        listViewResponsable.setItems(projetResponsableList);
        listViewDateDebut.setItems(projetDateDebutList);
        listViewDateFin.setItems(projetDateFinList);
        chargerDonneesDepuisCSV();
    }

    private void chargerDonneesDepuisCSV() {
        projetIntituleList.clear();
        projetResponsableList.clear();
        projetDateDebutList.clear();
        projetDateFinList.clear();
        projetMembresList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/projets.csv"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length >= 6) {
                    projetIntituleList.add(details[0]);
                    projetResponsableList.add(details[1]);
                    projetDateDebutList.add(LocalDate.parse(details[2]));
                    projetDateFinList.add(LocalDate.parse(details[3]));

                    List<Membre> membres = Arrays.stream(details[5].split(";"))
                            .filter(s -> !s.isEmpty())
                            .map(Membre::new)
                            .collect(Collectors.toList());
                    projetMembresList.add(membres);
                }
            }
            updateMembresListView();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du chargement des projets");
        }
    }

    private void updateMembresListView() {
        listViewMembres.setItems(FXCollections.observableArrayList(
                projetMembresList.stream()
                        .map(list -> list.stream()
                                .map(Membre::getNom)
                                .collect(Collectors.joining(", ")))
                        .collect(Collectors.toList())
        ));
    }
    @FXML
    private void openAjouterProjet(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ajouterProjet.fxml"));
            Parent root = loader.load();

            AjouterProjet controller = loader.getController();
            controller.setProjetsController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un projet");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture de la fenêtre d'ajout");
        }
    }

    @FXML
    private void openModifierProjet(ActionEvent event) {
        int selectedIndex = listViewIntitule.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            messageLabel.setText("Veuillez sélectionner un projet à modifier");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifierProjet.fxml"));
            Parent root = loader.load();

            AjouterProjet controller = loader.getController();
            controller.setProjetsController(this);

            // Remplir les champs avec les données du projet sélectionné
            controller.intituleField.setText(projetIntituleList.get(selectedIndex));
            controller.responsableField.setText(projetResponsableList.get(selectedIndex));
            controller.dateDebut.setValue(projetDateDebutList.get(selectedIndex));
            controller.dateFin.setValue(projetDateFinList.get(selectedIndex));

            // Remplir les membres
            controller.listeMembres.setItems(FXCollections.observableArrayList(
                    projetMembresList.get(selectedIndex)
            ));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier le projet");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture de la fenêtre de modification");
        }
    }

    @FXML
    private void supprimerProjet() {
        int selectedIndex = listViewIntitule.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            projetIntituleList.remove(selectedIndex);
            projetResponsableList.remove(selectedIndex);
            projetDateDebutList.remove(selectedIndex);
            projetDateFinList.remove(selectedIndex);
            projetMembresList.remove(selectedIndex);

            try (FileWriter writer = new FileWriter("src/main/resources/data/projets.csv")) {
                for (int i = 0; i < projetIntituleList.size(); i++) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                            projetIntituleList.get(i),
                            projetResponsableList.get(i),
                            projetDateDebutList.get(i),
                            projetDateFinList.get(i),
                            "", // tâches
                            projetMembresList.get(i).stream()
                                    .map(Membre::getNom)
                                    .collect(Collectors.joining(";"))
                    ));
                }
                messageLabel.setText("Projet supprimé avec succès");
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de la suppression");
            }
        } else {
            messageLabel.setText("Veuillez sélectionner un projet");
        }
    }
}