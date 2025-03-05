package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.CloudinaryUploader;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;

public class ModifierClientController {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenome;
    @FXML
    private TextField tfMail;
    @FXML
    private ImageView imgRetour;
    @FXML
    private TextField tfNum;
    @FXML
    private TextField tfLieux;
    @FXML
    private ImageView imageView;
    @FXML
    private Label lblNomError;

    @FXML
    private Label lblMailError;
    @FXML
    private Label lblLieuxError;
    @FXML
    private Label lblNumError;
    @FXML
    private Label lblPrenomError;

    private File selectedFile;
    private Client client;
    private Utilisateur utilisateur;
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    private final ServiceClient serviceClient = new ServiceClient();

    public void setClientData(Client client, Utilisateur utilisateur) {
        this.client = client;
        this.utilisateur = utilisateur;

        // Pré-remplir les champs avec les données actuelles du client
        tfNom.setText(utilisateur.getNom());
        tfPrenome.setText(utilisateur.getPrenom());
        tfMail.setText(utilisateur.getMail());
        tfNum.setText(client.getNumTel());
        tfLieux.setText(client.getAdresse());


        if (client.getImageUrl() == null && client.getImageUrl().equals("https://static.thenounproject.com/png/1743561-200.png")) {
            imageView.setImage(new Image(client.getImageUrl()));
        } else {
            imageView.setImage(new Image(client.getImageUrl()));
        }
    }

    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imageView.setImage(new Image(selectedFile.toURI().toString()));
            // client.setImageUrl(imageView.getImage().toString());
        }
    }


    {/*
    @FXML
    private void handleModifier(ActionEvent event) {
        if (client == null || utilisateur == null) {
            showAlert("Erreur", "Aucun client sélectionné pour la modification.");
            return;
        }
//Ayoub
        // Récupérer les nouvelles valeurs saisies
        utilisateur.setNom(tfNom.getText());
        utilisateur.setPrenom(tfPrenome.getText());
        utilisateur.setMail(tfMail.getText());
        utilisateur.setPassword(tfPasswor.getText());
        client.setNumTel(tfNum.getText());
        client.setAdresse(tfLieux.getText());

        // Vérifier si une nouvelle image est sélectionnée
        if (selectedFile != null) {
            try {
                String imageUrl = CloudinaryUploader.uploadFile(selectedFile);
                System.out.println("imageUrl==" + imageUrl);
                // Envoie l'image au cloud
                client.setImageUrl(imageUrl);
            } catch (Exception e) {
                showAlert("Erreur", "Échec de l'upload de l'image.");
                e.printStackTrace(); // Affiche l'erreur complète dans la console
                return;
            }

        }

        // Mise à jour dans la base de données
        try {
            serviceUtilisateur.update(utilisateur);
            serviceClient.update(client);

            showAlert("Succès", "Client mis à jour avec succès !");

            // Redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gereclient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Profile");
            stage.show();

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour : " + e.getMessage());
        }
    }
*/}

    @FXML
    private void handleModifier(ActionEvent event) {
        boolean isValid = true;

        // Réinitialiser les messages d'erreur
        lblNomError.setText("");
        lblPrenomError.setText("");
        lblNumError.setText("");
        lblLieuxError.setText("");
        lblMailError.setText("");

        // Récupérer les nouvelles valeurs saisies
        String nom = tfNom.getText();
        String prenom = tfPrenome.getText();
        String mail = tfMail.getText();
        String numTel = tfNum.getText();
        String adresse = tfLieux.getText();

        // Valider les champs
        if (nom.isEmpty()) {
            lblNomError.setText("Le nom est obligatoire.");
            isValid = false;
        }
        if (prenom.isEmpty()) {
            lblPrenomError.setText("Le prénom est obligatoire.");
            isValid = false;
        }
        if (mail.isEmpty() || !mail.contains("@")) {
            lblMailError.setText("Le mail est invalide.");
            isValid = false;
        }

        if (numTel.isEmpty()) {
            lblNumError.setText("Le numéro de téléphone est obligatoire.");
            isValid = false;
        }
        if (adresse.isEmpty()) {
            lblLieuxError.setText("L'adresse est obligatoire.");
            isValid = false;
        }

        // Si tout est valide, mettre à jour les informations
        if (isValid) {
            // Mettre à jour les données de l'utilisateur et du client
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setMail(mail);
            client.setNumTel(numTel);
            client.setAdresse(adresse);

            // Vérifier si une nouvelle image est sélectionnée
            if (selectedFile != null) {
                try {
                    String imageUrl = CloudinaryUploader.uploadFile(selectedFile);
                    System.out.println("imageUrl=="+imageUrl);
                    client.setImageUrl(imageUrl);
                } catch (Exception e) {
                    showAlert("Erreur", "Échec de l'upload de l'image.");
                    e.printStackTrace(); // Affiche l'erreur complète dans la console
                    return;
                }
            }

            // Mise à jour dans la base de données
            try {
                serviceUtilisateur.update(utilisateur);
                serviceClient.update(client);

                showAlert("Succès", "Client mis à jour avec succès !");

                // Redirection
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gereclient.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Profile");
                stage.show();

            } catch (Exception e) {
                showAlert("Erreur", "Une erreur est survenue lors de la mise à jour : " + e.getMessage());
            }
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
    @FXML
    private void handleRetour(MouseEvent event) throws IOException {
        System.out.println("🔙 Retour vers modiferclient.fxml");

        // Charger user.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/modiferclient.fxml"));

        // Changer la scène vers user.fxml
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
