package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import java.io.IOException;
import javafx.scene.control.Alert;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;


public class ModifierClientController {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenome;
    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfPasswor;
    @FXML
    private TextField tfNum;
    @FXML
    private TextField tfLieux;

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
        tfPasswor.setText(utilisateur.getPassword());  // Assurez-vous que c'est sécurisé
        tfNum.setText(String.valueOf(client.getNumTel()));
        tfLieux.setText(client.getAdresse());
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        if (client == null || utilisateur == null) {
            showAlert("Erreur", "Aucun client sélectionné pour la modification.");
            return;
        }

        // Récupérer les nouvelles valeurs saisies
        utilisateur.setNom(tfNom.getText());
        utilisateur.setPrenom(tfPrenome.getText());
        utilisateur.setMail(tfMail.getText());
        utilisateur.setPassword(tfPasswor.getText());
        client.setNumTel(tfNum.getText());
        client.setAdresse(tfLieux.getText());

        // Mise à jour dans la base de données
        try {
            serviceUtilisateur.update(utilisateur);
            serviceClient.update(client);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gereclient.fxml"));
            Parent root = loader.load();
            Gereclient clientController = loader.getController();
           // clientController.ajouterdonner(client,utilisateur);
            showAlert("Succès", "Client mis à jour avec succès !");


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Profile");
            stage.show();

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour : " + e.getMessage());
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
    private void handleRetour(ActionEvent event) throws IOException {
        // Charger user.fxml (assurez-vous qu'il est dans resources/)
        Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));

        // Obtenir la scène actuelle et changer vers user.fxml
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
