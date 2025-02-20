package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceReservation;

public class ModifierReservationController {

    @FXML
    private TextField tfIdVoiture;
    @FXML
    private TextField tfIdBilletAvion;
    @FXML
    private TextField tfIdHotel;
    @FXML
    private TextField tfIdClient;
    @FXML
    private TextField tfStatut;

    private Reservation reservation;
    private final ServiceReservation serviceReservation = new ServiceReservation();

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        tfIdVoiture.setText(String.valueOf(reservation.getId_voiture()));
        tfIdBilletAvion.setText(String.valueOf(reservation.getId_billetAvion()));
        tfIdHotel.setText(String.valueOf(reservation.getId_hotel()));
        tfIdClient.setText(String.valueOf(reservation.getId_client()));
        tfStatut.setText(reservation.getStatut());
    }

    @FXML
    private void modifierReservation() {
        try {
            int idVoiture = tfIdVoiture.getText().isEmpty() ? 0 : Integer.parseInt(tfIdVoiture.getText());
            int idBilletAvion = tfIdBilletAvion.getText().isEmpty() ? 0 : Integer.parseInt(tfIdBilletAvion.getText());
            int idHotel = tfIdHotel.getText().isEmpty() ? 0 : Integer.parseInt(tfIdHotel.getText());
            int idClient = Integer.parseInt(tfIdClient.getText());
            String statut = tfStatut.getText();

            reservation.setId_voiture(idVoiture);
            reservation.setId_billetAvion(idBilletAvion);
            reservation.setId_hotel(idHotel);
            reservation.setId_client(idClient);
            reservation.setStatut(statut);

            serviceReservation.update(reservation);

            Stage stage = (Stage) tfIdVoiture.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            System.out.println("Erreur de saisie : Veuillez entrer des valeurs valides.");
        }
    }
}