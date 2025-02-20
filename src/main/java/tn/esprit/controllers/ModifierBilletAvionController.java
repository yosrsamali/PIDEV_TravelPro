package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.BilletAvion;
import tn.esprit.services.ServiceBilletAvion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifierBilletAvionController {

    @FXML
    private TextField tfCompagnie, tfClassBillet, tfVilleDepart, tfVilleArrivee, tfDateDepart, tfDateArrivee, tfPrix;

    private BilletAvion billet;
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();

    public void setBillet(BilletAvion billet) {
        this.billet = billet;
        tfCompagnie.setText(billet.getCompagnie());
        tfClassBillet.setText(billet.getClass_Billet());
        tfVilleDepart.setText(billet.getVilleDepart());
        tfVilleArrivee.setText(billet.getVilleArrivee());
        tfDateDepart.setText(new SimpleDateFormat("dd-MM-yyyy").format(billet.getDateDepart()));
        tfDateArrivee.setText(new SimpleDateFormat("dd-MM-yyyy").format(billet.getDateArrivee()));
        tfPrix.setText(String.valueOf(billet.getPrix()));
    }

    @FXML
    private void modifierBillet() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date dateDepart = dateFormat.parse(tfDateDepart.getText());
            Date dateArrivee = dateFormat.parse(tfDateArrivee.getText());

            billet.setCompagnie(tfCompagnie.getText());
            billet.setClass_Billet(tfClassBillet.getText());
            billet.setVilleDepart(tfVilleDepart.getText());
            billet.setVilleArrivee(tfVilleArrivee.getText());
            billet.setDateDepart(dateDepart);
            billet.setDateArrivee(dateArrivee);
            billet.setPrix(Double.parseDouble(tfPrix.getText()));

            serviceBilletAvion.update(billet);

            showAlert("Succès", "Billet modifié avec succès.");

            Stage stage = (Stage) tfCompagnie.getScene().getWindow();
            stage.close();
        } catch (ParseException e) {
            showAlert("Erreur", "Format de date invalide. Utilisez le format dd-MM-yyyy.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le prix doit être un nombre valide.");
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