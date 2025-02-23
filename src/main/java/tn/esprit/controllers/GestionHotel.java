package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

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
    private DatePicker dpDateCheckIn;  // Remplacement de TextField par DatePicker
    @FXML
    private DatePicker dpDateCheckOut; // Remplacement de TextField par DatePicker
    @FXML
    private Label lbHotels;

    IService<Hotel> sh = new ServiceHotel();

    @FXML
    public void ajouterHotel(ActionEvent actionEvent) {
        // Contrôle de saisie
        if (tfNom.getText().isEmpty() || tfVille.getText().isEmpty() || tfPrixParNuit.getText().isEmpty() ||
                tfDisponible.getText().isEmpty() || tfNombreEtoile.getText().isEmpty() || tfTypeDeChambre.getText().isEmpty() ||
                dpDateCheckIn.getValue() == null || dpDateCheckOut.getValue() == null) {
            showAlert("Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        // Vérification du nom (pas de caractères spéciaux)
        if (!Pattern.matches("[a-zA-Z0-9 ]+", tfNom.getText())) {
            showAlert("Erreur de saisie", "Le nom ne doit pas contenir de caractères spéciaux.");
            return;
        }

        // Vérification du prix
        double prixParNuit;
        try {
            prixParNuit = Double.parseDouble(tfPrixParNuit.getText());
            if (prixParNuit <= 0) {
                showAlert("Erreur de format", "Le prix par nuit doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Le prix par nuit doit être un nombre valide.");
            return;
        }

        // Vérification du nombre d'étoiles
        int nombreEtoile;
        try {
            nombreEtoile = Integer.parseInt(tfNombreEtoile.getText());
            if (nombreEtoile < 1 || nombreEtoile > 7) {
                showAlert("Erreur de format", "Le nombre d'étoiles doit être compris entre 1 et 7.");
                return;
            }
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

        // Vérification des dates
        LocalDate checkIn = dpDateCheckIn.getValue();
        LocalDate checkOut = dpDateCheckOut.getValue();
        LocalDate today = LocalDate.now();

        if (checkIn.isBefore(today)){
            showAlert("Erreur de date", "La date de check-in doit être supérieure ou égale à la date actuelle.");
            return;
        }

        if (checkOut.isBefore(checkIn)) {
            showAlert("Erreur de date", "La date de check-out doit être après la date de check-in.");
            return;
        }

        Date dateCheckIn = Date.valueOf(checkIn);
        Date dateCheckOut = Date.valueOf(checkOut);

        // Création de l'hôtel avec les nouvelles dates
        Hotel h = new Hotel(
                tfNom.getText(),
                tfVille.getText(),
                prixParNuit,
                disponible,
                nombreEtoile,
                tfTypeDeChambre.getText(),
                dateCheckIn,
                dateCheckOut
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}