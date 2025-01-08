package org.example.ex02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class ModifierEmploye {
    private int employeIndex = -1;
    @FXML
    public Button boutton_modifierEPY;
    @FXML
    public Button boutton_fermer;
    @FXML
    private TextField ancienNom;
    @FXML
    private TextField ancienTelephone;
    @FXML
    private TextField ancienEmail;
    @FXML
    private TextField newNom;
    @FXML
    private TextField ancienProjet;
    @FXML
    private MenuButton newProjet;
    @FXML
    private TextField newTelephone;
    @FXML
    private TextField newEmail;
    @FXML
    private Label messageLabel;
    private Employes employesController;
    public void setEmployesController(Employes controller) {
        this.employesController = controller;
    }
    @FXML
    public void menuBoutton(ActionEvent actionEvent) {
        newProjet.getItems().clear();

        String path = "src/main/resources/data/projets.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (!ligne.trim().isEmpty()) {
                    String[] details = ligne.split(",");
                    String nomProjet = details[0];
                    MenuItem item = new MenuItem(nomProjet.trim());
                    String finalLigne = ligne;
                    item.setOnAction(event -> newProjet.setText(finalLigne.trim()));
                    newProjet.getItems().add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setEmployeDetails(String nom, String telephone, String email, String projet, int index) {
        ancienNom.setText(nom);
        ancienTelephone.setText(telephone);
        ancienEmail.setText(email);
        ancienProjet.setText(projet);
        employeIndex = index;
        menuBoutton(null);
    }
    @FXML
    public void modifierEmploye(ActionEvent actionEvent) {
        if (newNom.getText().isEmpty() || newTelephone.getText().isEmpty() || newEmail.getText().isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs");
            return;
        }
        try {
            employesController.employeNomList.set(employeIndex, newNom.getText());
            employesController.employeTelephoneList.set(employeIndex, newTelephone.getText());
            employesController.employeEmailList.set(employeIndex, newEmail.getText());
            employesController.employeProjetList.set(employeIndex, newProjet.getText());
            String filePath = "src/main/resources/data/employes.csv";

            List<String> lines = getStrings(filePath);

            try (FileWriter writer = new FileWriter(filePath)) {
                for (String line : lines) {
                    writer.write(line + "\n");
                }
            }
            fermer();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la modification de l'employé");
        }
    }


    private List<String> getStrings(String filePath) throws IOException {
        List<String> lines = new java.util.ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (currentIndex == employeIndex) {
                    lines.add(String.format("%s,%s,%s,%s",
                            newNom.getText(),
                            newTelephone.getText(),
                            newEmail.getText(),
                            newProjet.getText()
                    ));
                } else {
                    lines.add(line);
                }
                currentIndex++;
            }
        }
        return lines;
    }


    @FXML
    public void fermer() {
        Stage stage = (Stage) newEmail.getScene().getWindow();
        stage.close();
    }


}
