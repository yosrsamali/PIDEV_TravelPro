package tn.esprit.Controllers.controllers_reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private void naviguerVersGestionHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionHotel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void naviguerVersGestionBilletAvion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionBilletAvion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void naviguerVersGestionVoiture() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionVoiture.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void naviguerVersGestionReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionReservation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void naviguerVersCreerReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/ReservationForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Créer une Réservation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}