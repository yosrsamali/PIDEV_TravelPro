package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import tn.esprit.models.deponse;
import tn.esprit.services.Servicedeponse;

public class DeponseController {

    @FXML
    private TextField tfquantite_total;
    @FXML
    private TextField tfprix_achat;
    @FXML
    private TextField tftva;

    private Servicedeponse serviceDeponse = new Servicedeponse();

    // Méthode pour ajouter une dépense
    @FXML
    public void ajouterDeponse() {
        // Vérification des champs
        if (tfquantite_total.getText().isEmpty() || tfprix_achat.getText().isEmpty() || tftva.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        // Création de la dépense à partir des données saisies
        int quantiteTotal = Integer.parseInt(tfquantite_total.getText());
        double prixAchat = Double.parseDouble(tfprix_achat.getText());
        double tva = Double.parseDouble(tftva.getText());

        // Remarque : Vous ne passez plus l'ID, car il sera généré par la base de données
        deponse dep = new deponse(quantiteTotal, prixAchat, tva);

        // Ajout de la dépense à la base de données
        serviceDeponse.add(dep);

        // Affichage d'une alerte pour confirmer l'ajout
        showAlert(AlertType.INFORMATION, "Succès", "Dépense ajoutée avec succès.");

        // Redirection vers la scène liste_depense.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listedepense.fxml")); // Remplace par ton chemin
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) tfquantite_total.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erreur", "Impossible de charger la scène de liste des dépenses.");
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
