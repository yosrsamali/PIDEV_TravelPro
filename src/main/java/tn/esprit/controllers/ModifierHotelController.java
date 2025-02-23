package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ModifierHotelController {

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

    private Hotel hotel;
    private ServiceHotel serviceHotel = new ServiceHotel();

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        tfNom.setText(hotel.getNom());
        tfVille.setText(hotel.getVille());
        tfPrixParNuit.setText(String.valueOf(hotel.getPrixParNuit()));
        tfDisponible.setText(hotel.isDisponible() ? "Oui" : "Non");
        tfNombreEtoile.setText(String.valueOf(hotel.getNombreEtoile()));
        tfTypeDeChambre.setText(hotel.getTypeDeChambre());
        dpDateCheckIn.setValue(hotel.getDateCheckIn().toLocalDate()); // Affichage des dates existantes
        dpDateCheckOut.setValue(hotel.getDateCheckOut().toLocalDate());
    }

    @FXML
    private void handleModifier() {
        try {
            // Vérification du nom (pas de caractères spéciaux)
            if (!Pattern.matches("[a-zA-Z0-9 ]+", tfNom.getText())) {
                showAlert("Erreur de saisie", "Le nom ne doit pas contenir de caractères spéciaux.");
                return;
            }

            // Vérification du prix
            double prixParNuit = Double.parseDouble(tfPrixParNuit.getText());
            if (prixParNuit <= 0) {
                showAlert("Erreur de format", "Le prix par nuit doit être un nombre positif.");
                return;
            }

            // Vérification du nombre d'étoiles
            int nombreEtoile = Integer.parseInt(tfNombreEtoile.getText());
            if (nombreEtoile < 1 || nombreEtoile > 7) {
                showAlert("Erreur de format", "Le nombre d'étoiles doit être compris entre 1 et 7.");
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

            if (checkIn.isBefore(today)) {
                showAlert("Erreur de date", "La date de check-in doit être supérieure ou égale à la date actuelle.");
                return;
            }

            if (checkOut.isBefore(checkIn)) {
                showAlert("Erreur de date", "La date de check-out doit être après la date de check-in.");
                return;
            }

            // Mettre à jour l'hôtel
            hotel.setNom(tfNom.getText());
            hotel.setVille(tfVille.getText());
            hotel.setPrixParNuit(prixParNuit);
            hotel.setDisponible(disponible);
            hotel.setNombreEtoile(nombreEtoile);
            hotel.setTypeDeChambre(tfTypeDeChambre.getText());
            hotel.setDateCheckIn(Date.valueOf(checkIn));
            hotel.setDateCheckOut(Date.valueOf(checkOut));

            // Mise à jour dans la base de données
            serviceHotel.update(hotel);
            ((Stage) tfNom.getScene().getWindow()).close(); // Fermer la fenêtre

        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Assurez-vous que le prix et le nombre d'étoiles sont valides.");
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}