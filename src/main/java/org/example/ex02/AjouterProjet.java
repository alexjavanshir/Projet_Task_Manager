package org.example.ex02;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class AjouterProjet{
    @FXML public TextField intituleField;
    @FXML public DatePicker dateDebut;
    @FXML public DatePicker dateFin;

    @FXML public Label messageLabel;
    @FXML public Button boutonAjouter;
    @FXML public Button boutonFermer;
    @FXML
    private Projets projetsController;

    private ObservableList<String> projetIntituleList;
    private ObservableList<LocalDate> projetDateDebutList;
    private ObservableList<LocalDate> projetDateFinList;

    public void setObservableLists(ObservableList<String> projetIntituleList, ObservableList<LocalDate> projetDateDebutList, ObservableList<LocalDate> projetDateFinList){
        this.projetIntituleList = projetIntituleList;
        this.projetDateDebutList = projetDateDebutList;
        this.projetDateFinList = projetDateFinList;
    }

    public void setProjetsController(Projets projets){
        this.projetsController = projets;
    }

    @FXML
    public void ajouterProjet(ActionEvent event){
        if(!validateProject()){
            return;
        }
        try(FileWriter writer = new FileWriter("src/main/resources/data/projets.csv", true)){
            writer.append(String.format("%s,%s,%s,,\n",
                    intituleField.getText(),
                    dateDebut.getValue(),
                    dateFin.getValue()
            ));
            if(projetIntituleList != null && dateDebut != null && dateFin != null){
                projetIntituleList.add(intituleField.getText());
                projetDateDebutList.add(dateDebut.getValue());
                projetDateFinList.add(dateFin.getValue());
            }
            messageLabel.setText("Projet ajouté avec succès");
            viderChamps();
        } catch(IOException e){
            messageLabel.setText("Erreur lors de l'enregistrement");
        }
    }

    private boolean validateProject(){
        if(intituleField.getText().isEmpty() || dateDebut.getValue() == null || dateFin.getValue() == null){
            messageLabel.setText("Tous les champs doivent être remplis");
            return false;
        }

        if(dateFin.getValue().isBefore(dateDebut.getValue())){
            messageLabel.setText("La date de fin doit être après la date de début");
            return false;
        }

        return true;
    }

    private void viderChamps(){
        intituleField.clear();
        dateDebut.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now().plusMonths(1));
    }

    @FXML
    public void fermer(ActionEvent event){
        ((Stage) messageLabel.getScene().getWindow()).close();
    }
}