package tn.esprit.Controllers.controllers_reservation;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceVoiture;
import tn.esprit.services.ServiceHotel;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Voiture;
import tn.esprit.models.Hotel;

public class ReservationCell extends ListCell<Reservation> {

    private final Button cancelButton = new Button("Annuler");
    private final HBox hbox = new HBox();
    private final Text reservationText = new Text();
    private final ReservationListController controller;

    // Ajouter les services pour récupérer les détails
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();
    private final ServiceVoiture serviceVoiture = new ServiceVoiture();
    private final ServiceHotel serviceHotel = new ServiceHotel();

    public ReservationCell(ReservationListController controller) {
        super();
        this.controller = controller;

        // Configurer le bouton "Annuler"
        cancelButton.setOnAction(event -> {
            Reservation reservation = getItem();
            if (reservation != null) {
                controller.cancelReservation(reservation);
            }
        });

        // Ajouter les éléments à la HBox
        hbox.getChildren().addAll(reservationText, cancelButton);
        hbox.setSpacing(10);
    }

    @Override
    protected void updateItem(Reservation reservation, boolean empty) {
        super.updateItem(reservation, empty);

        if (empty || reservation == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Récupérer les détails du billet d'avion, de la voiture et de l'hôtel
            BilletAvion billet = serviceBilletAvion.getById(reservation.getId_billetAvion());
            Voiture voiture = serviceVoiture.getById(reservation.getId_voiture());
            Hotel hotel = serviceHotel.getById(reservation.getId_hotel());

            // Construire une chaîne de texte lisible
            StringBuilder details = new StringBuilder();

            if (billet != null) {
                details.append("Vol: ")
                        .append(billet.getCompagnie()).append(" - ")
                        .append(billet.getVilleDepart()).append(" vers ")
                        .append(billet.getVilleArrivee()).append(", ")
                        .append(billet.getClass_Billet()).append(", ")
                        .append(billet.getPrix()).append(" €\n");
            }

            if (voiture != null) {
                details.append("Voiture: ")
                        .append(voiture.getMarque()).append(" ")
                        .append(voiture.getModele()).append(", ")
                        .append(voiture.getPrixParJour()).append(" €/jour\n");
            }

            if (hotel != null) {
                details.append("Hôtel: ")
                        .append(hotel.getNom()).append(" à ")
                        .append(hotel.getVille()).append(", ")
                        .append(hotel.getTypeDeChambre()).append(", ")
                        .append(hotel.getPrixParNuit()).append(" €/nuit");
            }

            // Ajouter le statut de la réservation
            details.append("\nStatut: ").append(reservation.getStatut());

            // Définir le texte à afficher
            reservationText.setText(details.toString());
            setGraphic(hbox);
        }
    }
}