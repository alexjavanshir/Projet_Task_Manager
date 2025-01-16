package org.example.ex02;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Open {
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
