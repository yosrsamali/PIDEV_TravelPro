package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;
import java.util.Optional;

public class Gereclient {

    @FXML
    private Label lblNom;
    @FXML
    private Label lblPrenom;
    @FXML
    private Label lblMail;
    @FXML
    private Label lblNumTel;
    @FXML
    private Label lblAdresse;

    private Client client;
    private Utilisateur utilisateur;
    private final ServiceClient serviceClient = new ServiceClient();
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    /**
     * Méthode appelée après la connexion pour transmettre les informations du client.
     */
    public void ajouterdonner(Client client, Utilisateur utilisateur) {
        this.client = client;
        this.utilisateur = utilisateur;
        afficherInformationsClient();
    }


    private void afficherInformationsClient() {
        if (client != null && utilisateur != null) {
            lblNom.setText("Nom : " + utilisateur.getNom());
            lblPrenom.setText("Prénom : " + utilisateur.getPrenom());
            lblMail.setText("Email : " + utilisateur.getMail());
            lblNumTel.setText("Téléphone : " + client.getNumTel());
            lblAdresse.setText("Adresse : " + client.getAdresse());
        } else {
            lblNom.setText("Nom : Inconnu");
            lblPrenom.setText("Prénom : Inconnu");
            lblMail.setText("Email : Inconnu");
            lblNumTel.setText("Téléphone : Inconnu");
            lblAdresse.setText("Adresse : Inconnu");
        }
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modiferclient.fxml"));
            Parent root = loader.load();

            ModifierClientController modifierController = loader.getController();
            modifierController.setClientData(client, utilisateur);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Client");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger l'interface de modification.");
        }
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        if (client == null || utilisateur == null) {
            showAlert("Erreur", "Aucun client sélectionné pour la suppression.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression du client");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce client ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                serviceClient.delete(client);
                serviceUtilisateur.delete(utilisateur);

                showAlert("Succès", "Client supprimé avec succès.");

                retourAccueil(event);
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur est survenue lors de la suppression : " + e.getMessage());
            }
        }
    }

    private void retourAccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de retourner à l'accueil.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
