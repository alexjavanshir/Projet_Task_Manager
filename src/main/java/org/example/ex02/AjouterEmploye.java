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
        String nomPrenom = nomPrenomField.getText();
        String numero = numeroField.getText();
        String email = emailField.getText();

        if (nomPrenom.isEmpty() || numero.isEmpty() || email.isEmpty()){
            messageLabel.setText("Tous les champs doivent être remplis.");
            return;
        }

        try{
            String filePath = "src/main/resources/data/employes.csv";
            try (FileWriter writer = new FileWriter(filePath, true)){
                writer.append(nomPrenom).append(",");
                writer.append(numero).append(",");
                writer.append(email).append(",");
                writer.append("Pas encore de projet").append("\n");
            }
            if (employeNomList != null && employeTelephoneList != null && employeEmailList != null && employeProjetList != null){
                employeNomList.add(nomPrenom);
                employeTelephoneList.add(numero);
                employeEmailList.add(email);
                employeProjetList.add("Pas encore de projet");
            }

            messageLabel.setText("Ajout réussie !");
            nomPrenomField.clear();
            numeroField.clear();
            emailField.clear();
        } catch (IOException e){
            messageLabel.setText("Erreur lors de l'écriture dans le fichier.");
            e.printStackTrace();
        }
    }


    @FXML
    private void fermer(ActionEvent event){
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}

