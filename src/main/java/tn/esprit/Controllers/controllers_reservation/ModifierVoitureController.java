package tn.esprit.Controllers.controllers_reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceVoiture;

import java.sql.Date;
import java.time.LocalDate;

public class ModifierVoitureController {

    @FXML
    private TextField tfMarque;

    @FXML
    private TextField tfModele;

    @FXML
    private TextField tfAnnee;

    @FXML
    private TextField tfPrixParJour;

    @FXML
    private TextField tfDisponible;

    @FXML
    private DatePicker dpDateDeLocation;

    @FXML
    private DatePicker dpDateDeRemise;

    private Voiture voiture;
    private ServiceVoiture serviceVoiture = new ServiceVoiture();

    // Validation des chaînes de caractères
    private boolean validerChaine(String chaine) {
        String regex = "^[a-zA-Z0-9\\s]+$";
        return chaine.matches(regex);
    }

    // Validation des dates
    private boolean validerDates(LocalDate dateLocation, LocalDate dateRemise) {
        LocalDate aujourdHui = LocalDate.now();

        if (dateLocation.isBefore(aujourdHui)) {
            showAlert("Erreur de saisie", "La date de location doit être supérieure à la date actuelle.");
            return false;
        }

        if (dateRemise.isBefore(dateLocation)) {
            showAlert("Erreur de saisie", "La date de remise doit être supérieure à la date de location.");
            return false;
        }

        return true;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
        tfMarque.setText(voiture.getMarque());
        tfModele.setText(voiture.getModele());
        tfAnnee.setText(String.valueOf(voiture.getAnnee()));
        tfPrixParJour.setText(String.valueOf(voiture.getPrixParJour()));
        tfDisponible.setText(voiture.isDisponible() ? "Oui" : "Non");


    }

    @FXML
    public void modifierVoiture() {
        try {
            // Validation des champs
            if (!validerChaine(tfMarque.getText())) {
                showAlert("Erreur de saisie", "La marque ne doit pas contenir de caractères spéciaux.");
                return;
            }

            if (!validerChaine(tfModele.getText())) {
                showAlert("Erreur de saisie", "Le modèle ne doit pas contenir de caractères spéciaux.");
                return;
            }

            int annee = Integer.parseInt(tfAnnee.getText());

            if (annee < 1900 || annee > 2025) {
                showAlert("Erreur de saisie", "L'année doit être comprise entre 1900 et 2025.");
                return;
            }

            double prixParJour = Double.parseDouble(tfPrixParJour.getText());

            if (prixParJour <= 0) {
                showAlert("Erreur de saisie", "Le prix par jour doit être un nombre positif.");
                return;
            }

            LocalDate dateLocation = dpDateDeLocation.getValue();
            LocalDate dateRemise = dpDateDeRemise.getValue();

            if (!validerDates(dateLocation, dateRemise)) {
                return;
            }

            // Mise à jour de la voiture
            voiture.setMarque(tfMarque.getText());
            voiture.setModele(tfModele.getText());
            voiture.setAnnee(annee);
            voiture.setPrixParJour(prixParJour);
            voiture.setDisponible(tfDisponible.getText().equalsIgnoreCase("Oui"));
            voiture.setDateDeLocation(Date.valueOf(dateLocation));
            voiture.setDateDeRemise(Date.valueOf(dateRemise));

            serviceVoiture.update(voiture);

            showAlert("Succès", "Voiture modifiée avec succès.");

            // Fermer la fenêtre après la modification
            Stage stage = (Stage) tfMarque.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "L'année et le prix par jour doivent être des nombres valides.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite : " + e.getMessage());
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