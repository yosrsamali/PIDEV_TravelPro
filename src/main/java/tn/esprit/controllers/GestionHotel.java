package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

import java.io.IOException;

public class GestionHotel {
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfVille;
    @FXML
    private TextField tfPrixParNuit;
    @FXML
    private TextField tfDisponible;
    @FXML
    private TextField tfNombreEtoile;
    @FXML
    private TextField tfTypeDeChambre;
    @FXML
    private Label lbHotels;

    IService<Hotel> sh = new ServiceHotel();

    @FXML
    public void ajouterHotel(ActionEvent actionEvent) {
        // Contrôle de saisie
        if (tfNom.getText().isEmpty() || tfVille.getText().isEmpty() || tfPrixParNuit.getText().isEmpty() ||
                tfDisponible.getText().isEmpty() || tfNombreEtoile.getText().isEmpty() || tfTypeDeChambre.getText().isEmpty()) {
            showAlert("Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        // Vérification du prix
        double prixParNuit;
        try {
            prixParNuit = Double.parseDouble(tfPrixParNuit.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Le prix par nuit doit être un nombre valide.");
            return;
        }

        // Vérification du nombre d'étoiles
        int nombreEtoile;
        try {
            nombreEtoile = Integer.parseInt(tfNombreEtoile.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Le nombre d'étoiles doit être un entier valide.");
            return;
        }

        // Vérification de la disponibilité
        String disponibleText = tfDisponible.getText().toLowerCase();
        if (!disponibleText.equals("oui") && !disponibleText.equals("non")) {
            showAlert("Erreur de saisie", "La disponibilité doit être 'Oui' ou 'Non'.");
            return;
        }
        boolean disponible = disponibleText.equals("oui");

        // Création de l'hôtel
        Hotel h = new Hotel(
                tfNom.getText(),
                tfVille.getText(),
                prixParNuit,
                disponible,
                nombreEtoile,
                tfTypeDeChambre.getText()
        );

        // Ajout de l'hôtel
        sh.add(h);
        showAlert("Succès", "L'hôtel a été ajouté avec succès.");
    }
    @FXML
    private void handleNaviguerAffichage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageHotels.fxml"));
            Parent root = loader.load();
            tfNom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void afficherHotels(ActionEvent actionEvent) {
        lbHotels.setText(sh.getAll().toString());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}