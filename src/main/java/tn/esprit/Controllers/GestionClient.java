package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.SessionManager;

import java.io.IOException;

public class GestionClient {

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfTel;

    @FXML
    private Label lblUserInfo;

    @FXML
    private Button btnAjouter;

    @FXML
    private ImageView imgRetour;

    private final IService<Utilisateur> su = new ServiceUtilisateur();
    private final ServiceClient serviceClient = new ServiceClient();

    private Utilisateur user;

    // Cette méthode permet d'ajouter un utilisateur à la gestion
    public void ajouterdonner(Utilisateur user) {
        this.user = user;
    }

    @FXML
    private void initialize() {
        // Ajout de l'événement pour imgRetour si ce n'est pas bien lié
        imgRetour.setOnMouseClicked(event -> {
            try {
                handleRetour(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
/*
    @FXML
    private void Add(ActionEvent event) {
        if (event.getSource() == btnAjouter) {
            System.out.println("✅ Bouton Ajouter cliqué !");

            // Vérification des champs
            String address = tfAddress.getText().trim();
            String tel = tfTel.getText().trim();

            if (address.isEmpty() || tel.isEmpty()) {
                afficherAlerte("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            // Vérifier si l'utilisateur existe
            if (user == null) {
                afficherAlerte("Erreur", "Aucun utilisateur sélectionné !");
                return;
            }

            // Ajouter l'utilisateur à la base de données
            su.add(user);
            System.out.println("✅ Utilisateur ajouté avec succès !");

            // Vérifier si l'utilisateur a bien été ajouté
            if (user.getId() == 0) {
                afficherAlerte("Erreur", "L'utilisateur n'a pas été ajouté !");
                return;
            }

            // Création et ajout du client associé
            Client newClient = new Client();
            newClient.setId(user.getId());
            newClient.setNumTel(tel);
            newClient.setAdresse(address);
            newClient.setMail(user.getMail());
            newClient.setNom(user.getNom());
            newClient.setPrenom(user.getPrenom());
            newClient.setRole(user.getRole());
            newClient.setPassword(user.getPassword());
            newClient.setCodeVerification(user.getCodeVerification());
            newClient.setImageUrl("https://static.thenounproject.com/png/1743561-200.png");
            serviceClient.add(newClient);
            System.out.println("✅ Client ajouté avec succès !");

            // **Ajout du client à la session**
            SessionManager.getInstance().setUtilisateurConnecte(newClient);
            System.out.println("✅ Client ajouté à la session !");

            // **Redirection vers `CodeVerifer.fxml` pour vérification**
            redirectToCodeVerifier(event);
        }
    }
*/

    @FXML
    private Label lblAddressError;
    @FXML
    private Label lblTelError;

    @FXML
    private void Add(ActionEvent event) {
        // Récupérer les valeurs des champs de saisie
        String address = tfAddress.getText().trim();
        String tel = tfTel.getText().trim();

        boolean validTel = tel.matches("\\d{8}");  // Vérifier si le numéro de téléphone contient exactement 8 chiffres
        boolean validAddress = address.matches(".*[a-zA-Z].*") || address.matches(".*\\d.*");  // Vérifier si l'adresse contient des lettres et des chiffres

        // Réinitialiser les messages d'erreur
        lblAddressError.setText("");
        lblTelError.setText("");

        // Validation du numéro de téléphone
        if (tel.isEmpty()) {
            tfTel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            lblTelError.setText("Veuillez entrer un numéro de téléphone.");
            return;
        } else if (!validTel) {
            tfTel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            lblTelError.setText("Le numéro de téléphone doit contenir exactement 8 chiffres.");
            return;
        } else {
            tfTel.setStyle("-fx-border-color: #37B7C3;");
        }

        // Validation de l'adresse
        if (address.isEmpty()) {
            tfAddress.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            lblAddressError.setText("Veuillez entrer une adresse.");
            return;
        } else if (!validAddress) {
            tfAddress.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            lblAddressError.setText("L'adresse doit contenir à la fois des lettres et des chiffres.");
            return;
        } else {
            tfAddress.setStyle("-fx-border-color: #37B7C3;");
        }

        // Vérifier si l'utilisateur existe (si nécessaire)
        if (user == null) {
            afficherAlerte("Erreur", "Aucun utilisateur sélectionné !");
            return;
        }

        // Ajouter l'utilisateur à la base de données
        su.add(user);
        System.out.println("✅ Utilisateur ajouté avec succès !");

        // Vérifier si l'utilisateur a bien été ajouté
        if (user.getId() == 0) {
            afficherAlerte("Erreur", "L'utilisateur n'a pas été ajouté !");
            return;
        }

        // Création et ajout du client associé
        Client newClient = new Client();
        newClient.setId(user.getId());
        newClient.setNumTel(tel);
        newClient.setAdresse(address);
        newClient.setMail(user.getMail());
        newClient.setNom(user.getNom());
        newClient.setPrenom(user.getPrenom());
        newClient.setRole(user.getRole());
        newClient.setPassword(user.getPassword());
        newClient.setCodeVerification(user.getCodeVerification());
        newClient.setImageUrl("https://static.thenounproject.com/png/1743561-200.png");
        serviceClient.add(newClient);
        System.out.println("✅ Client ajouté avec succès !");

        // **Ajout du client à la session**
        SessionManager.getInstance().setUtilisateurConnecte(newClient);
        System.out.println("✅ Client ajouté à la session !");

        // **Redirection vers `CodeVerifer.fxml` pour vérification**
        redirectToCodeVerifier(event);
    }
    @FXML
    private void handleRetour(MouseEvent event) throws IOException {
        System.out.println("🔙 Retour vers user.fxml");

        // Charger user.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));

        // Changer la scène vers user.fxml
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void redirectToCodeVerifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CodeVerifer.fxml"));
            Parent root = loader.load();

            // Changer la scène vers la vérification du code
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Vérification du Code");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur", "Impossible de charger l'interface de vérification.");
            e.printStackTrace();
        }
    }



    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
