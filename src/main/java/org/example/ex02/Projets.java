package org.example.ex02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Projets{
    @FXML public Button boutton_openajouterPRJ;
    @FXML public Button boutton_openmodifierPRJ;
    @FXML public Button boutton_openafficherPRJ;
    @FXML public Button boutton_supprimerPRJ;
    @FXML private ListView<String> listViewIntitule;
    @FXML private ListView<LocalDate> listViewDateDebut;
    @FXML private ListView<LocalDate> listViewDateFin;
    @FXML private ListView<String> listViewMembres;
    @FXML private Label messageLabel;

    final ObservableList<String> projetIntituleList = FXCollections.observableArrayList();
    final ObservableList<LocalDate> projetDateDebutList = FXCollections.observableArrayList();
    final ObservableList<LocalDate> projetDateFinList = FXCollections.observableArrayList();
    private final ObservableList<List<Membre>> projetMembresList = FXCollections.observableArrayList();
    private final ObservableList<List<Tache>> projetTacheList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        chargerDonneesCSV();
        listViewIntitule.setItems(projetIntituleList);
        listViewDateDebut.setItems(projetDateDebutList);
        listViewDateFin.setItems(projetDateFinList);
    }

    public void chargerDonneesCSV(){
        projetIntituleList.clear();
        projetDateDebutList.clear();
        projetDateFinList.clear();
        projetMembresList.clear();

        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/projets.csv"))){
            String ligne;
            while((ligne = reader.readLine()) != null){
                String[] details = ligne.split(",", -1);
                if(details.length >= 4){
                    projetIntituleList.add(details[0]);
                    projetDateDebutList.add(LocalDate.parse(details[1]));
                    projetDateFinList.add(LocalDate.parse(details[2]));

                    List<Membre> membres = Arrays.stream(details[3].split(";")).map(Membre::new).collect(Collectors.toList());
                    projetMembresList.add(membres);
                }
            }
            updateMembresListView();
        } catch(IOException e){
            messageLabel.setText("Erreur lors du chargement des projets");
        }
    }

    private void updateMembresListView(){
        listViewMembres.setItems(FXCollections.observableArrayList(
                projetMembresList.stream().map(list -> list.stream().map(Membre::getNom).collect(Collectors.joining(", "))).collect(Collectors.toList())
        ));
    }
    @FXML
    private void openAjouterProjet(ActionEvent event) throws IOException{
        Open opener = new Open();
        AjouterProjet ajouterProjetController = opener.open("ajouterProjet", "Ajouter un projet", true, AjouterProjet.class);
        if(ajouterProjetController != null){
            ajouterProjetController.setProjetsController(this);
            ajouterProjetController.setObservableLists(projetIntituleList, projetDateDebutList, projetDateFinList);
        }
    }

    @FXML
    private void openModifierProjet(ActionEvent event){
        int selectedIndex = listViewIntitule.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Open opener = new Open();
            ModifierProjet modifierProjetController = opener.open("modifierProjet", "Modifier un projet", true, ModifierProjet.class);
            if(modifierProjetController != null){
                modifierProjetController.setProjetsController(this);
                modifierProjetController.setProjetsDetails(projetIntituleList.get(selectedIndex), projetDateDebutList.get(selectedIndex), projetDateFinList.get(selectedIndex), selectedIndex);
            }
        }
        else{
            messageLabel.setText("Veuillez sélectionner un projet à modifier");
        }
    }
    @FXML
    private void openGererProjet(ActionEvent event){
        int selectedIndex = listViewIntitule.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Open opener = new Open();
            GestionnaireTache tacheController = opener.open("tache", "Gestionnaire de tâches", true, GestionnaireTache.class);
            if(tacheController != null){
                tacheController.setProjetsController(this);
                tacheController.setProjetsDetails(projetIntituleList.get(selectedIndex), selectedIndex);

            }
        }
        else{
            messageLabel.setText("Veuillez sélectionner un projet");
        }
    }

    @FXML
    private void supprimerProjet(){
        int selectedIndex = listViewIntitule.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            projetIntituleList.remove(selectedIndex);
            projetDateDebutList.remove(selectedIndex);
            projetDateFinList.remove(selectedIndex);
            if (selectedIndex < projetMembresList.size()) {
                projetMembresList.remove(selectedIndex);
            }

            try(FileWriter writer = new FileWriter("src/main/resources/data/projets.csv")){
                for(int i = 0; i < projetIntituleList.size(); i++){
                    writer.write(String.format("%s,%s,%s,", projetIntituleList.get(i), projetDateDebutList.get(i), projetDateFinList.get(i)));
                    if(!projetMembresList.get(i).isEmpty()) {
                        writer.write(String.format("%s\n", projetMembresList.get(i).stream().map(Membre::getNom).collect(Collectors.joining(";"))));
                    }
                }
                messageLabel.setText("Projet supprimé avec succès");
            } catch(IOException e){
                messageLabel.setText("Erreur lors de la suppression");
            }
        } else{
            messageLabel.setText("Veuillez sélectionner un projet");
        }
    }

}