package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.SessionManager;

import java.io.IOException;

public class CodeVerifer {
    @FXML
    private TextField tfCodeVerification;
    @FXML
    private Button btnVerifier;

    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @FXML
    private void verifierCode(ActionEvent event) {
        // Récupérer l'utilisateur connecté
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (utilisateur == null) {
            afficherAlerte("Erreur", "Aucun utilisateur trouvé dans la session !");
            return;
        }

        // Récupérer le code saisi par l'utilisateur
        String codeSaisi = tfCodeVerification.getText();

        if (codeSaisi.isEmpty()) {
            afficherAlerte("Erreur", "Veuillez entrer un code de vérification.");
            return;
        }

        // Vérifier si le code saisi correspond à celui de l'utilisateur
        if (codeSaisi.equals(utilisateur.getCodeVerification())) {
            // Mettre à jour l'état de l'utilisateur en base de données
            serviceUtilisateur.updateEtatUtilisateur(utilisateur.getId(),true);

            // Afficher un message de succès
            afficherAlerte("Succès", "Votre compte a été vérifié avec succès !");

            // Rediriger vers l'interface Gestion Client
            changerScene(event, "/Gereclient.fxml");
        } else {
            afficherAlerte("Erreur", "Code incorrect ! Veuillez réessayer.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void changerScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion Client");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur", "Impossible de charger l'interface.");
            e.printStackTrace();
        }
    }
}
