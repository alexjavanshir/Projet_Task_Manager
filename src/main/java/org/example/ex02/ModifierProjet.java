package org.example.ex02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ModifierProjet {
    @FXML
    public Button boutton_modifierPRJ;
    @FXML
    public Button boutton_fermer;
    @FXML
    public TextField intituleField;
    @FXML
    public MenuButton responsableField;
    @FXML
    public DatePicker dateDebut;
    @FXML
    public DatePicker dateFin;
    @FXML
    public ListView<Tache> listeTaches;
    @FXML
    public ListView<Membre> listeMembres;
    @FXML
    public Label messageLabel;
/*
    private Project currentProject;
    private Projets projetsController;

    public void setProjetsController(Projets controller) {
        this.projetsController = controller;
    }

    public void setProject(Project project) {
        this.currentProject = project;

        // Fill the form with current project data
        intituleField.setText(project.getIntitule());
        responsableField.setText(project.getResponsable());
        dateDebut.setValue(project.getDateDebut());
        dateFin.setValue(project.getDateFin());

        // Update lists
        listeTaches.setItems(project.getTaches());
        listeMembres.setItems(project.getMembres());
    }

    @FXML
    public void modifierProjet(ActionEvent event) {
        if (intituleField.getText().isEmpty() || responsableField.getText().isEmpty()
                || dateDebut.getValue() == null || dateFin.getValue() == null) {
            messageLabel.setText("Veuillez remplir tous les champs");
            return;
        }

        if (dateFin.getValue().isBefore(dateDebut.getValue())) {
            messageLabel.setText("La date de fin doit être postérieure à la date de début.");
            return;
        }

        // Update project properties
        currentProject.setIntitule(intituleField.getText());
        currentProject.setResponsable(responsableField.getText());
        currentProject.setDateDebut(dateDebut.getValue());
        currentProject.setDateFin(dateFin.getValue());

        messageLabel.setText("Projet modifié avec succès");
        fermer();
    }

    @FXML
    public void fermer() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }*/
}