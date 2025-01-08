package org.example.ex02;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Open {

    /**
     * Ouvre une nouvelle fenêtre FXML
     *
     * @param nomFXML         Le nom du fichier FXML (sans l'extension .fxml)
     * @param titre           Le titre de la fenêtre
     * @param prendreControle Indique si vous souhaitez obtenir le contrôleur
     * @param classeControle  La classe du contrôleur (peut être null si prendreControle = false)
     * @param <T>             Le type générique pour le contrôleur
     * @return Le contrôleur de la fenêtre ouverte (si prendreControle est true), sinon null
     */
    public <T> T open(String nomFXML, String titre, boolean prendreControle, Class<T> classeControle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nomFXML + ".fxml"));
            Parent root = loader.load();

            T controller = null;
            if (prendreControle) {
                controller = loader.getController();
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titre);
            stage.setScene(scene);
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
