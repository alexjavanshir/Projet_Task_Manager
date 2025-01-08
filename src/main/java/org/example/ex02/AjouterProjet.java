package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class AjouterProjet {
    @FXML public TextField intituleField;
    @FXML public MenuButton responsableField;
    @FXML public DatePicker dateDebut;
    @FXML public DatePicker dateFin;
    @FXML public Button boutonAjouterTache;
    @FXML public TextField ecrireAjouterTache;
    @FXML public ListView<Tache> listeTaches;
    @FXML public Button boutonAjouterMembre;
    @FXML public MenuButton menuChoisirMembre;
    @FXML public ListView<Membre> listeMembres;
    @FXML public Label messageLabel;
    @FXML public Button boutonAjouter;
    @FXML public Button boutonFermer;

    private ObservableList<String> projetsList;
    private Projets projetsController;

    @FXML
    public void initialize() {
        dateDebut.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now().plusMonths(1));
        listeTaches.setItems(FXCollections.observableArrayList());
        listeMembres.setItems(FXCollections.observableArrayList());
    }

    public void setProjetsController(Projets projets) {
        this.projetsController = projets;
    }

    @FXML
    public void ajouterProjet(ActionEvent event) {
        if (!validateProject()) {
            return;
        }

        try (FileWriter writer = new FileWriter("src/main/resources/data/projets.csv", true)) {
            writer.append(String.format("%s,%s,%s,%s,%s,%s\n",
                    intituleField.getText(),
                    responsableField.getText(),
                    dateDebut.getValue(),
                    dateFin.getValue(),
                    listeTaches.getItems().stream()
                            .map(Tache::getNom)
                            .collect(Collectors.joining(";")),
                    listeMembres.getItems().stream()
                            .map(Membre::getNom)
                            .collect(Collectors.joining(";"))
            ));

            if (projetsList != null) {
                projetsList.add(intituleField.getText());
            }

            messageLabel.setText("Projet ajouté avec succès");
            viderChamps();

            if (projetsController != null) {
                projetsController.initialize();
            }
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'enregistrement");
        }
    }

    private boolean validateProject() {
        if (intituleField.getText().isEmpty() || responsableField.getText().isEmpty()) {
            messageLabel.setText("Tous les champs doivent être remplis");
            return false;
        }

        if (dateDebut.getValue() == null || dateFin.getValue() == null) {
            messageLabel.setText("Les dates sont obligatoires");
            return false;
        }

        if (dateFin.getValue().isBefore(dateDebut.getValue())) {
            messageLabel.setText("La date de fin doit être après la date de début");
            return false;
        }

        return true;
    }

    @FXML
    public void ajouterTache() {
        String nomTache = ecrireAjouterTache.getText().trim();
        if (!nomTache.isEmpty()) {
            listeTaches.getItems().add(new Tache(nomTache));
            ecrireAjouterTache.clear();
        }
    }

    @FXML
    public void ajouterMembre() {
        String nomMembre = menuChoisirMembre.getText().trim();
        if (!nomMembre.isEmpty()) {
            listeMembres.getItems().add(new Membre(nomMembre));
            menuChoisirMembre.setText("");
        }
    }

    private void viderChamps() {
        intituleField.clear();
        responsableField.setText("");
        dateDebut.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now().plusMonths(1));
        listeTaches.getItems().clear();
        listeMembres.getItems().clear();
    }

    @FXML
    public void fermer(ActionEvent event) {
        ((Stage) messageLabel.getScene().getWindow()).close();
    }
}