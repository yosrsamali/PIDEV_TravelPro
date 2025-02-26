package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.sql.Timestamp;
import java.util.Date;

public class AvisController {

    @FXML
    private HBox ratingBox; // Conteneur des étoiles

    @FXML
    private TextField commentaireField;

    @FXML
    private Button submitButton;

    private int note = 0; // Note sélectionnée

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {
        // Initialiser les étoiles à gris par défaut
        updateStars();
    }

    // Méthode pour définir la note
    @FXML
    private void setRating(javafx.event.ActionEvent event) {
        Button star = (Button) event.getSource();
        note = Integer.parseInt(star.getUserData().toString());
        updateStars(); // Mettre à jour l'apparence des étoiles
    }

    // Mettre à jour l'apparence des étoiles
    private void updateStars() {
        for (int i = 0; i < ratingBox.getChildren().size(); i++) {
            Button star = (Button) ratingBox.getChildren().get(i);
            if (i < note) {
                star.setStyle("-fx-text-fill: gold;"); // Étoile sélectionnée
            } else {
                star.setStyle("-fx-text-fill: gray;"); // Étoile non sélectionnée
            }
        }
    }

    @FXML
    private void ajouterAvis() {
        try {
            String commentaire = commentaireField.getText().trim();

            // Validation de la note
            if (note < 1 || note > 5) {
                showAlert("Erreur", "❌ Veuillez sélectionner une note entre 1 et 5.");
                return;
            }

            // Validation du commentaire
            if (commentaire.isEmpty()) {
                showAlert("Erreur", "❌ Le commentaire ne peut pas être vide.");
                return;
            }

            // Créer un avis avec la note et le commentaire
            Avis avis = new Avis(0, note, commentaire, new Timestamp(new Date().getTime()), false);
            serviceAvis.add(avis);

            showAlert("Succès", "✅ Avis ajouté avec succès !");

            // Réinitialiser les champs après ajout
            commentaireField.clear();
            note = 0;
            updateStars(); // Réinitialiser les étoiles

        } catch (Exception e) {
            showAlert("Erreur", "❌ Une erreur s'est produite : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (title.equals("Succès")) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}