package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Admin;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;

public class ModifierAdminController {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfRole;

    private Admin admin;
    private Utilisateur utilisateur;
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    private final ServiceAdmin serviceAdmin = new ServiceAdmin();

    /**
     * Pré-remplir les champs avec les données de l'admin.
     */
    public void setAdminData(Admin admin, Utilisateur utilisateur) {
        this.admin = admin;
        this.utilisateur = utilisateur;

        tfNom.setText(utilisateur.getNom());
        tfPrenom.setText(utilisateur.getPrenom());
        tfMail.setText(utilisateur.getMail());
        tfPassword.setText(utilisateur.getPassword()); // Assurez-vous de la gestion sécurisée des mots de passe
        tfRole.setText(utilisateur.getRole());
    }

    /**
     * Méthode pour modifier les informations de l'admin.
     */
    @FXML
    private void handleModifier(ActionEvent event) {
        if (admin == null || utilisateur == null) {
            showAlert("Erreur", "Aucun administrateur sélectionné pour la modification.");
            return;
        }

        // Mettre à jour les nouvelles valeurs
        utilisateur.setNom(tfNom.getText());
        utilisateur.setPrenom(tfPrenom.getText());
        utilisateur.setMail(tfMail.getText());
        utilisateur.setPassword(tfPassword.getText());
        utilisateur.setRole(tfRole.getText());

        // Mise à jour dans la base de données
        try {
            serviceUtilisateur.update(utilisateur);
            serviceAdmin.update(admin);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GereAdmin.fxml"));
            Parent root = loader.load();
            GereAdmin adminController = loader.getController();
            adminController.ajouterdonner(admin, utilisateur);
            showAlert("Succès", "Administrateur mis à jour avec succès !");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Profil Administrateur");
            stage.show();

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour : " + e.getMessage());
        }
    }

    /**
     * Méthode pour afficher des alertes.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Méthode pour retourner à l'interface utilisateur précédente.
     */
    @FXML
    private void handleRetour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
