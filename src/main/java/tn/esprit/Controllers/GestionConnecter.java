package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import tn.esprit.models.Admin;
import tn.esprit.models.Client;

import java.io.IOException;

import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;



public class GestionConnecter {

    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfPassword;

    private ServiceUtilisateur  su = new ServiceUtilisateur();
    private ServiceAdmin sa = new ServiceAdmin();
    private ServiceClient sc = new ServiceClient();

/*
    @FXML
    private void getuser(ActionEvent event) throws IOException{

        String mail = tfMail.getText().trim();

        String password = tfPassword.getText().trim();

        // Vérifier si les champs sont vides
        if (mail.isEmpty() || password.isEmpty()) {
            afficherAlerte("Échec de connexion", "Email ou le mot de passe vide !");

            return;
        }

        // Récupérer l'utilisateur avec le service
        Utilisateur utilisateur = su.getByMailAndPassword(mail, password);

        if (utilisateur != null) {
            System.out.println("Connexion réussie : " + utilisateur.getNom() + " " + utilisateur.getPrenom());











            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gereclient.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gereclient");
            stage.show();

        } else {

            afficherAlerte("Échec de connexion", "Email ou mot de passe incorrect !");
        }
    }
*/

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

  /*  @FXML
    private void getuser(ActionEvent event) throws IOException {
        String mail = tfMail.getText().trim();
        String password = tfPassword.getText().trim();

        // Vérifier si les champs sont vides
        if (mail.isEmpty() || password.isEmpty()) {
            afficherAlerte("Échec de connexion", "Email ou le mot de passe vide !");
            return;
        }

        // Récupérer l'utilisateur avec le service
        Utilisateur utilisateur = su.getByMailAndPassword(mail, password);

        if (utilisateur != null) {
            System.out.println("Connexion réussie : " + utilisateur.getNom() + " " + utilisateur.getPrenom());

            String fxmlFile;
            Admin userDetails; // Stockera l'objet Admin ou Client
            Client userDetails2=new Client();
            if ("Admin".equals(utilisateur.getRole())) {
                // Récupérer les détails de l'admin
                userDetails = sa.getAdminParIdUtilisateur(utilisateur.getId());
                fxmlFile = "/GereAdmin.fxml";
            } else {
                // Récupérer les détails du client
                userDetails2 = sc.getClientParIdUtilisateur(utilisateur.getId());
                fxmlFile = "/GereClient.fxml";
            }

            // Charger la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Récupérer le contrôleur de la nouvelle interface et lui envoyer les données
            if ("Admin".equals(utilisateur.getRole())) {
                System.out.println(utilisateur);
                System.out.println(userDetails);


            } else {
                System.out.println(utilisateur);
                System.out.println(userDetails2);
                Gereclient clientController = loader.getController();
                clientController.ajouterdonner(userDetails2,utilisateur);
            }

            // Changer de scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Espace " + utilisateur.getRole());
            stage.show();
        } else {
            afficherAlerte("Échec de connexion", "Email ou mot de passe incorrect !");
        }
    }

*/
  @FXML
  private void getuser(ActionEvent event) throws IOException {
      String mail = tfMail.getText().trim();
      String password = tfPassword.getText().trim();

      // Vérifier si les champs sont vides
      if (mail.isEmpty() || password.isEmpty()) {
          afficherAlerte("Échec de connexion", "Email ou le mot de passe vide !");
          return;
      }

      // Récupérer l'utilisateur avec le service
      Utilisateur utilisateur = su.getByMailAndPassword(mail, password);

      if (utilisateur != null) {
          System.out.println("Connexion réussie : " + utilisateur.getNom() + " " + utilisateur.getPrenom());

          FXMLLoader loader;
          Parent root;
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

          if ("Admin".equals(utilisateur.getRole())) {
              // Récupérer les détails de l'Admin
              Admin adminDetails = sa.getAdminParIdUtilisateur(utilisateur.getId());

              loader = new FXMLLoader(getClass().getResource("/GereAdmin.fxml"));
              root = loader.load();

              // Passer les données à GereadminController
              GereAdmin adminController = loader.getController();
              adminController.ajouterdonner(adminDetails, utilisateur);
          } else {
              // Récupérer les détails du Client
              Client clientDetails = sc.getClientParIdUtilisateur(utilisateur.getId());

              loader = new FXMLLoader(getClass().getResource("/GereClient.fxml"));
              root = loader.load();

              // Passer les données à GereclientController
              Gereclient clientController = loader.getController();
              clientController.ajouterdonner(clientDetails, utilisateur);
          }

          // Changer de scène
          stage.setScene(new Scene(root));
          stage.setTitle("Espace " + utilisateur.getRole());
          stage.show();
      } else {
          afficherAlerte("Échec de connexion", "Email ou mot de passe incorrect !");
      }
  }




    @FXML
    private void VerUser(ActionEvent event) throws IOException {
        // Charger le fichier FXML de connexion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user.fxml"));
        Parent root = loader.load();

        // Obtenir la fenêtre actuelle et changer la scène
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Utilisateur"); // Titre de la nouvelle fenêtre
        stage.show();
    }



}
