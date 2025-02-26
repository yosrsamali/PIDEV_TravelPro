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
import javafx.stage.Stage;
import tn.esprit.models.*;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.*;
import tn.esprit.utils.SessionManager;
import java.time.LocalDateTime;

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
    private Label lblMail;
    @FXML
    private Label lblNumTel;
    @FXML
    private Label lblAdresse;

    private final ServiceClient serviceClient = new ServiceClient();
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    private final ServiceAdmin serviceAdmin = new ServiceAdmin();

    @FXML
    public void initialize() {
        afficherInformationsClient();
    }

    private void afficherInformationsClient() {
        // Récupérer le client connecté depuis la session
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (utilisateur instanceof Client) {
            Client client = (Client) utilisateur;

            lblNom.setText("Nom : " + client.getNom());
            lblPrenom.setText("Prénom : " + client.getPrenom());
            lblMail.setText("Email : " + client.getMail());
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

            // Récupérer le client depuis la session et l'envoyer au contrôleur de modification
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
    private void handleSupprimer(ActionEvent event) {
        // Récupérer le client depuis la session
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
    private void Add(ActionEvent event) {
        if (event.getSource() == btnAjouter2) {
            System.out.println(" Bouton Ajouter cliqué");
            Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();


            Utilisateur user= new Utilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(), utilisateur.getPassword(), utilisateur.getRole());
            // Vérifier si l'utilisateur existe
            if (user == null) {
                System.out.println(" Aucun utilisateur sélectionné !");
                return;
            }
             user.setRole("Admin");
            // Ajouter l'utilisateur à la base de données
            serviceUtilisateur.update(user);
            System.out.println("✅ Utilisateur ajouté avec succès !");
            user.setId(utilisateur.getId());
            // Vérifier si l'utilisateur a bien été ajouté
            if (user.getId() == 0) {
                System.out.println("❌ Erreur : l'utilisateur n'a pas été ajouté.");
                return;
            }

            // Création et ajout de l'administrateur associé
            Admin newAdmin = new Admin();
            newAdmin.setId(user.getId());
            newAdmin.setMail(user.getMail());
            newAdmin.setNom(user.getNom());
            newAdmin.setPrenom(user.getPrenom());
            newAdmin.setRole(user.getRole());
            newAdmin.setPassword(user.getPassword());

            serviceAdmin.add(newAdmin);
            System.out.println("✅ Administrateur ajouté avec succès !");

            // **Ajout de l'admin à la session**
            SessionManager.getInstance().setUtilisateurConnecte(newAdmin);
            System.out.println("✅ Admin ajouté à la session !");

            // **Redirection vers `GereAdmin.fxml`**
            redirectToGereAdmin(event);
        }
    }

    private void redirectToGereAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GereAdmin.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et transmettre les données de l'admin
            GereAdmin gereAdminController = loader.getController();

            // Changer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion de l'Administrateur");
            stage.show();
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du chargement de GereAdmin.fxml : " + e.getMessage());
        }
    }


    @FXML
    private void EnvoyerDemende(ActionEvent event) {
        // Récupérer le client connecté depuis la session
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();

        if (utilisateur == null) {
            afficherPopup("Erreur", "❌ Aucun utilisateur connecté !");
            return;
        }

        // Vérifier si c'est bien un client
        if (!(utilisateur instanceof Client)) {
            afficherPopup("Erreur", "❌ Seuls les clients peuvent envoyer une demande !");
            return;
        }

        Client client = (Client) utilisateur;

        // Vérifier si une demande existe déjà pour ce client
        ServiceDemandeValidation serviceDemande = new ServiceDemandeValidation();
        if (serviceDemande.demandeExiste(client.getId())) {
            afficherPopup("Erreur", "⚠ Une demande est déjà en attente pour ce client.");
            return;
        }

        // Ajouter une nouvelle demande
        DemandeValidation nouvelleDemande = new DemandeValidation(client.getIdClient());
        serviceDemande.add(nouvelleDemande);

        // Afficher une popup de succès
        afficherPopup("Succès", "✅ Votre demande a été envoyée avec succès !");
    }

    private void afficherPopup(String titre, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





}
