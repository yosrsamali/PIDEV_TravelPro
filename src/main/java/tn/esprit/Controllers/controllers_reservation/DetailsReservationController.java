package tn.esprit.Controllers.controllers_reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Hotel;
import tn.esprit.models.Reservation;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceHotel;
import tn.esprit.services.ServiceVoiture;

public class DetailsReservationController {

    @FXML
    private Label lbClientDetails;
    @FXML
    private Label lbBilletDetails;
    @FXML
    private Label lbHotelDetails;
    @FXML
    private Label lbVoitureDetails;

    private Reservation reservation;

    private final ServiceHotel serviceHotel = new ServiceHotel();
    private final ServiceVoiture serviceVoiture = new ServiceVoiture();
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        afficherDetails();
    }

    private void afficherDetails() {
        // Afficher les détails du client
        lbClientDetails.setText("Détails du Client: ID " + reservation.getId_client());

        // Afficher les détails du billet d'avion (si disponible)
        if (reservation.getId_billetAvion() != 0) {
            BilletAvion billet = serviceBilletAvion.getById(reservation.getId_billetAvion());
            lbBilletDetails.setText("Détails du Billet: " + billet.toString());
        } else {
            lbBilletDetails.setText("Aucun billet d'avion associé.");
        }

        // Afficher les détails de l'hôtel (si disponible)
        if (reservation.getId_hotel() != 0) {
            Hotel hotel = serviceHotel.getById(reservation.getId_hotel());
            lbHotelDetails.setText("Détails de l'Hôtel: " + hotel.toString());
        } else {
            lbHotelDetails.setText("Aucun hôtel associé.");
        }

        // Afficher les détails de la voiture (si disponible)
        if (reservation.getId_voiture() != 0) {
            Voiture voiture = serviceVoiture.getById(reservation.getId_voiture());
            lbVoitureDetails.setText("Détails de la Voiture: " + voiture.toString());
        } else {
            lbVoitureDetails.setText("Aucune voiture associée.");
        }
    }
}