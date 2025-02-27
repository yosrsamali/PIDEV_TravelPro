package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.models.Voiture;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceReservation;
import tn.esprit.services.ServiceVoiture;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceHotel;

public class ModifierReservationController {

    @FXML
    private Label lblIdVoiture;
    @FXML
    private Label lblIdBilletAvion;
    @FXML
    private Label lblIdHotel;
    @FXML
    private Label lblIdClient;
    @FXML
    private TextField tfStatut;

    private Reservation reservation;
    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ServiceVoiture serviceVoiture = new ServiceVoiture();
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();
    private final ServiceHotel serviceHotel = new ServiceHotel();

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        displayReservationDetails();
    }

    private void displayReservationDetails() {
        // Détails de la voiture
        if (reservation.getId_voiture() == 0) {
            lblIdVoiture.setText("Non spécifié");
        } else {
            Voiture voiture = serviceVoiture.getById(reservation.getId_voiture());
            lblIdVoiture.setText(voiture != null ? voiture.getMarque() + " " + voiture.getModele() : "Inconnu");
        }

        // Détails du billet avion
        if (reservation.getId_billetAvion() == 0) {
            lblIdBilletAvion.setText("Non spécifié");
        } else {
            BilletAvion billet = serviceBilletAvion.getById(reservation.getId_billetAvion());
            lblIdBilletAvion.setText(billet != null ? billet.getCompagnie() + " (" + billet.getVilleDepart() + " -> " + billet.getVilleArrivee() + ")" : "Inconnu");
        }

        // Détails de l'hôtel
        if (reservation.getId_hotel() == 0) {
            lblIdHotel.setText("Non spécifié");
        } else {
            Hotel hotel = serviceHotel.getById(reservation.getId_hotel());
            lblIdHotel.setText(hotel != null ? hotel.toString() : "Inconnu");
        }

        // Détails du client
        lblIdClient.setText("ID " + reservation.getId_client());

        // Statut (modifiable)
        tfStatut.setText(reservation.getStatut());
    }

    @FXML
    private void modifierReservation() {
        String statut = tfStatut.getText();
        reservation.setStatut(statut);
        serviceReservation.update(reservation);

        Stage stage = (Stage) tfStatut.getScene().getWindow();
        stage.close();
    }
}