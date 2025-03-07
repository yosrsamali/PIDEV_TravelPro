package tn.esprit.Controllers.contolleruser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.Controllers.GestionClient;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class GestionUtulisateur {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenome;

    @FXML
    private TextField tfMail;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Label lblNomError;

    @FXML
    private Label lblPrenomError;

    @FXML
    private Label lblMailError;

    @FXML
    private Label lblPasswordError;

    @FXML
    private ImageView eyeIcon;

    private boolean isPasswordVisible = false;

    // Champ pour afficher le mot de passe en texte clair
    @FXML
    private TextField tfPasswordPlain;

    // Méthode pour basculer la visibilité du mot de passe
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

    IService<Utilisateur> su = new ServiceUtilisateur();

    /**
     * Génère un code de vérification à 6 chiffres
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(code);
    }

    @FXML
    private void goToNextScene(ActionEvent event) {
        // Valider les champs avant de continuer
        if (!validateFields()) {
            return; // Arrêter l'exécution si la validation échoue
        }

        try {
            // Récupérer les données saisies
            String nom = tfNom.getText();
            String prenom = tfPrenome.getText();
            String mail = tfMail.getText();
            String password = tfPassword.getText();
            String role = "Client";

            // Générer le code de vérification
            String codeVerification = generateVerificationCode();

            // Créer un objet Utilisateur avec le code de vérification
            Utilisateur user = new Utilisateur(nom, prenom, mail, password, role, codeVerification, false);
            System.out.println("Utilisateur créé : " + user);
            System.out.println("Code de vérification généré : " + codeVerification);

            // Déterminer quelle interface charger
            String fxmlFile = "/client.fxml";

            // Charger la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            GestionClient clientController = loader.getController();
            clientController.ajouterdonner(user);

            // Changer de scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Espace Client");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Verconnecret(ActionEvent event) throws IOException {
        // Charger le fichier FXML de connexion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/connecter.fxml"));
        Parent root = loader.load();

        // Obtenir la fenêtre actuelle et changer la scène
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Connexion"); // Titre de la nouvelle fenêtre
        stage.show();
    }

    /**
     * Valide les champs du formulaire
     */
    private boolean validateFields() {
        boolean isValid = true;

        // Validation du nom
        if (tfNom.getText().isEmpty()) {
            lblNomError.setText("Nom est requis");
            isValid = false;
        } else if (!tfNom.getText().matches("[a-zA-Z]+")) {
            lblNomError.setText("Le nom doit contenir uniquement des lettres");
            isValid = false;
        } else {
            lblNomError.setText("");
        }

        // Validation du prénom
        if (tfPrenome.getText().isEmpty()) {
            lblPrenomError.setText("Prénom est requis");
            isValid = false;
        } else if (!tfPrenome.getText().matches("[a-zA-Z]+")) {
            lblPrenomError.setText("Le prénom doit contenir uniquement des lettres");
            isValid = false;
        } else {
            lblPrenomError.setText("");
        }

        // Validation de l'email
        if (!isValidEmail(tfMail.getText())) {
            lblMailError.setText("Email invalide");
            isValid = false;
        } else {
            lblMailError.setText("");
        }

        // Validation du mot de passe
        if (tfPassword.getText().isEmpty()) {
            lblPasswordError.setText("Mot de passe est requis");
            isValid = false;
        } else if (tfPassword.getText().length() < 6) {
            lblPasswordError.setText("Mot de passe trop faible (min. 6 caractères)");
            isValid = false;
        } else {
            lblPasswordError.setText("");
        }

        return isValid;
    }

    /**
     * Vérifie si l'email est valide
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
