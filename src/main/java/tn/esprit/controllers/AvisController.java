package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.sql.Timestamp;
import java.util.Date;

public class AvisController {

    @FXML
    private TextField noteField;

    @FXML
    private TextField commentaireField;

    @FXML
    private Button submitButton;

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> ajouterAvis());
    }

    private void ajouterAvis() {
        try {
            String noteText = noteField.getText().trim();
            String commentaire = commentaireField.getText().trim();

            // Vérifier si la note est un entier
            if (!noteText.matches("\\d+")) {
                showAlert("Erreur", "❌ La note doit être un nombre entier.");
                return;
            }

            int note = Integer.parseInt(noteText);

            // Vérifier si la note est entre 0 et 10
            if (note < 0 || note > 10) {
                showAlert("Erreur", "❌ La note doit être comprise entre 0 et 10.");
                return;
            }

            // Vérifier si le commentaire n'est pas vide
            if (commentaire.isEmpty()) {
                showAlert("Erreur", "❌ Le commentaire ne peut pas être vide.");
                return;
            }

            // Créer un avis et l'ajouter à la base de données
            Avis avis = new Avis(0, note, commentaire, new Timestamp(new Date().getTime()), false);
            serviceAvis.add(avis);

            showAlert("Succès", "✅ Avis ajouté avec succès !");

            // Réinitialiser les champs après ajout
            noteField.clear();
            commentaireField.clear();

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