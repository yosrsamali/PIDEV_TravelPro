package tn.esprit.Controllers.controllerAvis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Adminmanager {

    private static final Logger logger = Logger.getLogger(Adminmanager.class.getName());  // Logger instance

    @FXML
    private Button btnAfficherAvisNonAcceptes;  // Corresponds to the button in FXML
    @FXML
    private Button btnAjouterAvis;  // Corresponds to the button in FXML
    @FXML
    private Button btnAfficherAvisAcceptes;  // Corresponds to the button in FXML
    @FXML
    private VBox avisContainer;  // Corresponds to the VBox in FXML

    // Navigate to "Ajouter Avis" view
    @FXML
    private void goToAjouterAvis(ActionEvent event) {
        changerScene(event, "/viewAvis/Avis.fxml");
    }

    // Show non-accepted reviews
    @FXML
    private void afficherAvisNonAcceptes(ActionEvent event) {
        changerScene(event, "/viewAvis/avis_card.fxml");
    }

    // Show accepted reviews
    @FXML
    private void afficherAvisAcceptes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewAvis/Reponse.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading reponse.fxml interface", e);
        }
    }

    // Helper method to change scenes
    private void changerScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading FXML file: " + fxmlPath, e);
        }
    }
}
