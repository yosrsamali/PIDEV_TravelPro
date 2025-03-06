package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn.esprit.models.*;
import tn.esprit.services.*;
import tn.esprit.utils.SessionManager;

import java.io.IOException;
import java.util.Optional;

public class Gereclient {
    @FXML
    private Button btnAjouter2;
    @FXML
    private Label lblNom;
    @FXML
    private Label lblPrenom;
    @FXML
    private ImageView profileImageView; // Ajout de l'ImageView pour l'image du client

    private final ServiceClient serviceClient = new ServiceClient();
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    private final ServiceAdmin serviceAdmin = new ServiceAdmin();

    @FXML
    public void initialize() {
        afficherInformationsClient();
        afficherImageClient(); // Charger l'image du client
    }

    private void afficherInformationsClient() {
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (utilisateur instanceof Client) {
            Client client = (Client) utilisateur;
            lblNom.setText(client.getNom().toUpperCase());
            lblPrenom.setText(client.getPrenom());
        } else {
            lblNom.setText("Nom : Inconnu");
            lblPrenom.setText("Prénom : Inconnu");
        }
    }

    private void afficherImageClient() {
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (utilisateur instanceof Client) {
            Client client = (Client) utilisateur;
            String imageUrl = client.getImageUrl();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                profileImageView.setImage(new Image(imageUrl));
            } else {
                profileImageView.setImage(new Image(getClass().getResource("/images/default-avatar.png").toExternalForm()));
            }
        }
    }

    @FXML
    private void handleModifier(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modiferclient.fxml"));
            Parent root = loader.load();

            ModifierClientController modifierController = loader.getController();
            Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

            if (utilisateur instanceof Client) {
                modifierController.setClientData((Client) utilisateur, utilisateur);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Client");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger l'interface de modification.");
        }
    }

    @FXML
    private void handleSupprimer(MouseEvent event) {
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (!(utilisateur instanceof Client)) {
            showAlert("Erreur", "Aucun client connecté pour la suppression.");
            return;
        }

        Client client = (Client) utilisateur;

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
                SessionManager.getInstance().logout();
                retourAccueil(event);
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur est survenue lors de la suppression : " + e.getMessage());
            }
        }
    }

    private void retourAccueil(MouseEvent event) {
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

    @FXML
    private void Veruser(ActionEvent event) {
        loadCenterContent("/user.fxml", event);
    }

    @FXML
    private void Verclient(ActionEvent event) {
        loadCenterContent("/Client.fxml", event);
    }

    private void loadCenterContent(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            BorderPane borderPane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
            borderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void EnvoyerDemende(ActionEvent event) {
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (utilisateur == null) {
            afficherPopup("Erreur", "❌ Aucun utilisateur connecté !");
            return;
        }

        if (!(utilisateur instanceof Client)) {
            afficherPopup("Erreur", "❌ Seuls les clients peuvent envoyer une demande !");
            return;
        }

        Client client = (Client) utilisateur;
        ServiceDemandeValidation serviceDemande = new ServiceDemandeValidation();

        if (serviceDemande.demandeExiste(client.getId())) {
            afficherPopup("Erreur", "⚠ Une demande est déjà en attente pour ce client.");
            return;
        }

        DemandeValidation nouvelleDemande = new DemandeValidation(client.getIdClient());
        serviceDemande.add(nouvelleDemande);
        afficherPopup("Succès", "✅ Votre demande a été envoyée avec succès !");
    }

    private void afficherPopup(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void Deconnection(MouseEvent event) {
        SessionManager.getInstance().logout();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Page de connexion");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de retourner à l'écran de connexion.");
            e.printStackTrace();
        }
    }
}
