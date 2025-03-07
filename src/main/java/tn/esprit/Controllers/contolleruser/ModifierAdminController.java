package tn.esprit.Controllers.contolleruser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Admin;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierAdminController {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfMail;


    @FXML
    private Label lblNomError;
    @FXML
    private Label lblPrenomError;
    @FXML
    private Label lblMailError;


    private Admin admin;
    private Utilisateur utilisateur;
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    private final ServiceAdmin serviceAdmin = new ServiceAdmin();

    public void setAdminData(Admin admin, Utilisateur utilisateur) {
        this.admin = admin;
        this.utilisateur = utilisateur;

        tfNom.setText(utilisateur.getNom());
        tfPrenom.setText(utilisateur.getPrenom());
        tfMail.setText(utilisateur.getMail());
    }

    // Action de modification
    @FXML
    private void handleModifier(ActionEvent event) {
        // Effacer les erreurs précédentes
        clearErrorMessages();

        // Validation des champs
        if (!validateFields()) {
            return;
        }

        // Mettre à jour les données utilisateur
        utilisateur.setNom(tfNom.getText());
        utilisateur.setPrenom(tfPrenom.getText());
        utilisateur.setMail(tfMail.getText());

        try {
            // Mise à jour dans la base de données
            serviceUtilisateur.update(utilisateur);
            serviceAdmin.update(admin);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GereAdmin.fxml"));
            Parent root = loader.load();
            GereAdmin adminController = loader.getController();
            showAlert("Succès", "Administrateur mis à jour avec succès !");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Profil Administrateur");
            stage.show();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour : " + e.getMessage());
        }
    }

    // Validation des champs
    private boolean validateFields() {
        boolean isValid = true;

        // Validation du nom
        if (tfNom.getText().isEmpty()) {
            lblNomError.setText("Nom est requis");
            isValid = false;
        }

        // Validation du prénom
        if (tfPrenom.getText().isEmpty()) {
            lblPrenomError.setText("Prénom est requis");
            isValid = false;
        }

        // Validation de l'email
        if (!isValidEmail(tfMail.getText())) {
            lblMailError.setText("Email invalide");
            isValid = false;
        }




        return isValid;
    }

    // Validation de l'email avec regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Vérification de la validité du mot de passe
    private boolean isValidPassword(String password) {
        return password.length() >= 6; // Mot de passe de base (min. 6 caractères)
    }

    // Vérification de la force du mot de passe
    private String getPasswordStrength(String password) {
        if (password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*")) {
            return "Forte";
        } else {
            return "Faible";
        }
    }

    // Effacer les messages d'erreur
    private void clearErrorMessages() {
        lblNomError.setText("");
        lblPrenomError.setText("");
        lblMailError.setText("");
    }

    // Affichage des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Retourner à la page précédente
    @FXML
    private void handleRetour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}