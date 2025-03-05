package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.models.Admin;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.SessionManager;

import java.io.IOException;

public class GestionConnecter {

    @FXML
    private TextField tfMail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfPasswordPlain;
    @FXML
    private ImageView eyeIcon;
    @FXML
    private Label lblPasswordError;

    private ServiceUtilisateur su = new ServiceUtilisateur();
    private ServiceAdmin sa = new ServiceAdmin();
    private ServiceClient sc = new ServiceClient();

    private boolean isPasswordVisible = false;

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Masquer le mot de passe et afficher le champ masqué
            tfPassword.setText(tfPasswordPlain.getText());
            tfPassword.setVisible(true);
            tfPasswordPlain.setVisible(false);
            eyeIcon.setImage(new Image("images/22249-200.png")); // Icône de l'œil fermé
        } else {
            // Afficher le mot de passe en texte clair
            tfPasswordPlain.setText(tfPassword.getText());
            tfPassword.setVisible(false);
            tfPasswordPlain.setVisible(true);
            eyeIcon.setImage(new Image("images/eye_icon.png")); // Icône de l'œil ouvert
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @FXML
    private void getuser(ActionEvent event) throws IOException {
        String mail = tfMail.getText().trim();
        String password = tfPassword.getText().trim();

        // Vérifier si les champs sont vides
        if (mail.isEmpty() || password.isEmpty()) {
            afficherAlerte("Échec de connexion", "Email ou mot de passe vide !");
            return;
        }

        // Récupérer l'utilisateur
        Utilisateur utilisateur = su.getByMailAndPassword(mail, password);

        if (utilisateur != null) {
            System.out.println("✅ Connexion réussie : " + utilisateur.getNom() + " " + utilisateur.getPrenom());

            FXMLLoader loader;
            Parent root;
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            if ("Admin".equals(utilisateur.getRole())) {
                // Récupérer les détails de l'Admin
                Admin adminDetails = sa.getAdminParIdUtilisateur(utilisateur.getId());
                adminDetails.setMail(utilisateur.getMail());
                adminDetails.setNom(utilisateur.getNom());
                adminDetails.setPrenom(utilisateur.getPrenom());
                adminDetails.setRole(utilisateur.getRole());
                adminDetails.setPassword(utilisateur.getPassword());
                // Ajouter à la session
                SessionManager.getInstance().setUtilisateurConnecte(adminDetails);

                loader = new FXMLLoader(getClass().getResource("/GereAdmin.fxml"));
                root = loader.load();

                // Passer les données au contrôleur
                GereAdmin adminController = loader.getController();
                // adminController.ajouterdonner(adminDetails, utilisateur);
            } else {
                // Récupérer les détails du Client
                Client clientDetails = sc.getClientParIdUtilisateur(utilisateur.getId());

                clientDetails.setMail(utilisateur.getMail());
                clientDetails.setNom(utilisateur.getNom());
                clientDetails.setPrenom(utilisateur.getPrenom());
                clientDetails.setRole(utilisateur.getRole());
                clientDetails.setPassword(utilisateur.getPassword());

                SessionManager.getInstance().setUtilisateurConnecte(clientDetails);

                loader = new FXMLLoader(getClass().getResource("/GereClient.fxml"));
                root = loader.load();

                // Passer les données au contrôleur
                Gereclient clientController = loader.getController();
                // clientController.ajouterdonner(clientDetails, utilisateur);
            }

            // Changer de scène
            stage.setScene(new Scene(root));
            stage.setTitle("Espace " + utilisateur.getRole());
            stage.show();
        } else {
            afficherAlerte("Échec de connexion", "Email ou mot de passe incorrect !");
            lblPasswordError.setText("Mot de passe incorrect");
        }
    }

    @FXML
    private void VerUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Utilisateur");
        stage.show();
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
