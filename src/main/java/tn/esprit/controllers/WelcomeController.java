package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private void naviguerVersGestionHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionHotel.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionBilletAvion.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionVoiture.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionReservation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}