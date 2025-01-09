package org.example.ex02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ModifierProjet {
    private int projetIndex = -1;
    @FXML
    public Button boutton_modifierPRJ;
    @FXML
    public Button boutton_fermer;
    @FXML
    public TextField intituleField;
    @FXML
    public DatePicker dateDebut;
    @FXML
    public DatePicker dateFin;

    @FXML
    public TextField ancienIntituleField;
    @FXML
    public DatePicker ancienDateDebut;
    @FXML
    public DatePicker ancienDateFin;
    @FXML
    public Label messageLabel;

    private Projets projetsController;

    public void setProjetsController(Projets controller){
        this.projetsController = controller;
    }
    public void setProjetsDetails(String intitule, LocalDate dateDebut, LocalDate dateFin, int index) {
        ancienIntituleField.setText(intitule);
        ancienDateDebut.setValue(dateDebut);
        ancienDateFin.setValue(dateFin);
        projetIndex = index;
    }
    @FXML
    public void modifierProjet(ActionEvent event) {
        if (intituleField.getText().isEmpty() || dateDebut.getValue() == null|| dateFin.getValue() == null){
            messageLabel.setText("Veuillez remplir tous les champs");
            return;
        }
        if (dateFin.getValue().isBefore(dateDebut.getValue())) {
            messageLabel.setText("La date de fin doit être postérieure à la date de début.");
            return;
        }
        try {
            projetsController.projetIntituleList.set(projetIndex, intituleField.getText());
            projetsController.projetDateDebutList.set(projetIndex, dateDebut.getValue());
            projetsController.projetDateFinList.set(projetIndex, dateFin.getValue());

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
                if (currentIndex == projetIndex) {
                    lines.add(String.format("%s,%s,%s\n", intituleField.getText(), dateDebut.getValue(), dateFin.getValue()));
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
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }


}