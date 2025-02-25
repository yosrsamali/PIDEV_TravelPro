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
import tn.esprit.models.Admin;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.SessionManager;

import java.io.IOException;
import java.util.Optional;

public class GereAdmin {

    @FXML
    private Label lblNom;
    @FXML
    private Label lblPrenom;
    @FXML
    private Label lblMail;
    @FXML
    private Label lblRole;

    private final ServiceAdmin serviceAdmin = new ServiceAdmin();
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @FXML
    public void initialize() {
        afficherInformationsAdmin();
    }

    private void afficherInformationsAdmin() {
        // Récupérer l'utilisateur connecté depuis la session
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();
        System.out.println("utilisateur");
        System.out.println(utilisateur);

        if (utilisateur instanceof Admin) {
            Admin admin = (Admin) utilisateur;

            lblNom.setText("Nom : " + admin.getNom());
            lblPrenom.setText("Prénom : " + admin.getPrenom());
            lblMail.setText("Email : " + admin.getMail());
            lblRole.setText("Rôle : " + admin.getRole());
        } else {
            lblNom.setText("Nom : Inconnu");
            lblPrenom.setText("Prénom : Inconnu");
            lblMail.setText("Email : Inconnu");
            lblRole.setText("Rôle : Inconnu");
        }
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifieradmin.fxml"));
            Parent root = loader.load();

            ModifierAdminController modifierController = loader.getController();

            // Récupérer l'utilisateur depuis la session et l'envoyer au contrôleur de modification
            Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();
            if (utilisateur instanceof Admin) {
                modifierController.setAdminData((Admin) utilisateur, utilisateur);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Admin");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger l'interface de modification.");
        }
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        // Récupérer l'utilisateur depuis la session
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (!(utilisateur instanceof Admin)) {
            showAlert("Erreur", "Aucun administrateur connecté pour la suppression.");
            return;
        }

        Admin admin = (Admin) utilisateur;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression de l'administrateur");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet administrateur ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                serviceAdmin.delete(admin);
                serviceUtilisateur.delete(utilisateur);

                showAlert("Succès", "Administrateur supprimé avec succès.");

                // Nettoyer la session et retourner à l'accueil
                SessionManager.getInstance().logout();
                retourAccueil(event);
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur est survenue lors de la suppression : " + e.getMessage());
            }
        }
    }

    private void retourAccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
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
