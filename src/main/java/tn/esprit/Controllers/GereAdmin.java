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
import javafx.scene.input.MouseEvent;
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

            lblNom.setText( admin.getNom().toUpperCase());
            lblPrenom.setText( admin.getPrenom());
        } else {
            lblNom.setText("Nom : Inconnu");
            lblPrenom.setText("Prénom : Inconnu");
        }
    }

    @FXML
    private void handleModifier(MouseEvent event) {
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
    private void handleSupprimer(MouseEvent event) {
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

    private void retourAccueil(MouseEvent event) {
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


    @FXML
    private void Afficherlistdededemende(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeDemande.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la nouvelle interface
            ListeDemandeController controller = loader.getController();
            controller.chargerListeDemandes(); // Charger les demandes

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des Demandes");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la liste des demandes.");
        }
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
