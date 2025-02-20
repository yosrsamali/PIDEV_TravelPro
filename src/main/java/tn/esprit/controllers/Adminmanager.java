package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.io.IOException;
import java.util.List;

public class Adminmanager {
  @FXML
    private void goToAjouterAvis(ActionEvent event) {
        changerScene(event, "/Avis.fxml");
    }

    @FXML
    private void afficherAvisNonAcceptes(ActionEvent event) {
        changerScene(event, "/avis_card.fxml");

    }


    private void changerScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void afficherAvisAcceptes(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reponse.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et changer la vue
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface reponse.fxml : " + e.getMessage());
        }
    }


}
