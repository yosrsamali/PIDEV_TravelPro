package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private TextField tfPassword;
    @FXML
    private Label lbUsers; // Assuming you have a label to display users

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
}
