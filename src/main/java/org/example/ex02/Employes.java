package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Employes{
    @FXML
    public Button boutton_openajouterEPY;
    @FXML
    public Button boutton_openmodifierEPY;
    @FXML
    public Button boutton_supprimerEPY;
    @FXML
    private ListView<String> listViewNom;
    @FXML
    private ListView<String> listViewTelephone;
    @FXML
    private ListView<String> listViewEmail;
    @FXML
    private ListView<String> listViewProjet;
    @FXML
    private Label messageLabel;
    final ObservableList<String> employeNomList = FXCollections.observableArrayList();
    final ObservableList<String> employeTelephoneList = FXCollections.observableArrayList();
    final ObservableList<String> employeEmailList = FXCollections.observableArrayList();
    final ObservableList<String> employeProjetList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        chargerDonneesDepuisCSV();
        listViewNom.setItems(employeNomList);
        listViewTelephone.setItems(employeTelephoneList);
        listViewEmail.setItems(employeEmailList);
        listViewProjet.setItems(employeProjetList);
    }

    void chargerDonneesDepuisCSV(){
        employeNomList.clear();
        employeTelephoneList.clear();
        employeEmailList.clear();
        employeProjetList.clear();

        String cheminFichier = "src/main/resources/data/employes.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))){
            String ligne;
            while ((ligne = reader.readLine()) != null){
                String[] details = ligne.split(",", -1);
                if (details.length == 4){
                    employeNomList.add(details[0]);
                    employeTelephoneList.add(details[1]);
                    employeEmailList.add(details[2]);
                    employeProjetList.add(details[3]);

                }
            }
        } catch (IOException e){
            messageLabel.setText("Erreur lors du chargement du fichier CSV");
        }
    }

    @FXML
    private void openAjouterEmploye() throws IOException{
        Open opener = new Open();
        AjouterEmploye ajouterEmployeController = opener.open("ajoutEmploye", "Ajouter un employé", true, AjouterEmploye.class);

        if (ajouterEmployeController != null){
            ajouterEmployeController.setEmployesController(this);
            ajouterEmployeController.setObservableLists(employeNomList, employeTelephoneList, employeEmailList, employeProjetList);
        }
        chargerDonneesDepuisCSV();
    }

    @FXML
    private void openModifierEmploye() throws IOException{
        int selectedIndex = listViewNom.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Open open = new Open();
            ModifierEmploye modifierEmployeController = open.open("modifierEmploye", "Modifier un employé", true, ModifierEmploye.class);
            if (modifierEmployeController != null) {
                modifierEmployeController.setEmployesController(this);
                modifierEmployeController.setEmployeDetails(employeNomList.get(selectedIndex), employeTelephoneList.get(selectedIndex), employeEmailList.get(selectedIndex), employeProjetList.get(selectedIndex), selectedIndex);
            }
        } else{
                messageLabel.setText("Veuillez sélectionner un employé à modifier");
            }
    }

    @FXML
    private void supprimerEmploye() throws IOException{
        int selectedIndex = listViewNom.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0){
            employeNomList.remove(selectedIndex);
            employeTelephoneList.remove(selectedIndex);
            employeEmailList.remove(selectedIndex);
            employeProjetList.remove(selectedIndex);

            String filePath = "src/main/resources/data/employes.csv";

            try (FileWriter writer = new FileWriter(filePath)){
                for (int i = 0; i < employeNomList.size(); i++){
                    writer.write(String.format("%s,%s,%s,%s\n", employeNomList.get(i), employeTelephoneList.get(i), employeEmailList.get(i), employeProjetList.get(i)));
                }
            } catch (IOException e){
                messageLabel.setText("Erreur lors de la suppression de l'employé.");
                return;
            }
            messageLabel.setText("Employé supprimé avec succès.");
        } else{
            messageLabel.setText("Veuillez sélectionner un employé à supprimer.");
        }
    }

    @FXML
    private void fermerE(ActionEvent event){
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}

