package org.example.ex02;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;

public class AjouterEmploye{
    @FXML
    public Button boutton_ajouterEPY;
    @FXML
    public Button boutton_fermerajouterEPY;

    @FXML
    private TextField nomPrenomField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField emailField;
    @FXML
    private Label messageLabel;
    @FXML
    private Employes employesController;
    @FXML
    public void setEmployesController(Employes employesController){
        this.employesController = employesController;
    }
    @FXML
    private ObservableList<String> employeNomList;
    @FXML
    private ObservableList<String> employeTelephoneList;
    @FXML
    private ObservableList<String> employeEmailList;
    @FXML
    private ObservableList<String> employeProjetList;

    public void setObservableLists(ObservableList<String> employeNomList, ObservableList<String> employeTelephoneList, ObservableList<String> employeEmailList, ObservableList<String> employeProjetList){
        this.employeNomList = employeNomList;
        this.employeTelephoneList = employeTelephoneList;
        this.employeEmailList = employeEmailList;
        this.employeProjetList = employeProjetList;
    }

    @FXML
    private void ajouterEmploye(ActionEvent event){
        if (nomPrenomField.getText().isEmpty() || numeroField.getText().isEmpty() || emailField.getText().isEmpty()){
            messageLabel.setText("Tous les champs doivent être remplis.");
            return;
        }

        String filePath = "src/main/resources/data/employes.csv";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(String.format("%s,%s,%s,Pas de projet\n", nomPrenomField.getText(), numeroField.getText(), emailField.getText()));
            if (employeNomList != null && employeTelephoneList != null && employeEmailList != null) {
                employeNomList.add(nomPrenomField.getText());
                employeTelephoneList.add(numeroField.getText());
                employeEmailList.add(emailField.getText());
            }
            messageLabel.setText("Ajout réussie !");
            nomPrenomField.clear();
            numeroField.clear();
            emailField.clear();

        } catch (IOException e){
            messageLabel.setText("Erreur lors de l'écriture dans le fichier.");
        }
        finally {
            employesController.chargerDonneesDepuisCSV();
            }

    }


    @FXML
    private void fermer(ActionEvent event) {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}

