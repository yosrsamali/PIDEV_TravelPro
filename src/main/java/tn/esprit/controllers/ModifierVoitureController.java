package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceVoiture;

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

    private Voiture voiture;
    private ServiceVoiture serviceVoiture = new ServiceVoiture();

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
            voiture.setMarque(tfMarque.getText());
            voiture.setModele(tfModele.getText());
            voiture.setAnnee(Integer.parseInt(tfAnnee.getText()));
            voiture.setPrixParJour(Double.parseDouble(tfPrixParJour.getText()));
            voiture.setDisponible(tfDisponible.getText().equalsIgnoreCase("Oui"));

            serviceVoiture.update(voiture);

            showAlert("Succès", "Voiture modifiée avec succès.");

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