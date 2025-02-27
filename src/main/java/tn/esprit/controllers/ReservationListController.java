package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceReservation;

import java.util.List;

public class ReservationListController {

    @FXML
    private ListView<Reservation> reservationListView;

    private ServiceReservation serviceReservation = new ServiceReservation();
    private int clientId = 0; // Remplacez par l'ID du client connecté

    @FXML
    public void initialize() {
        // Charger les réservations du client
        loadReservations();
    }

    private void loadReservations() {
        // Récupérer les réservations du client
        List<Reservation> reservations = serviceReservation.getReservationsByClientId(clientId);

        // Effacer les anciens éléments de la ListView
        reservationListView.getItems().clear();

        // Ajouter les nouvelles réservations à la ListView
        reservationListView.getItems().addAll(reservations);

        // Configurer un bouton "Annuler" pour chaque réservation
        reservationListView.setCellFactory(param -> new ReservationCell(this));
    }

    // Méthode pour annuler une réservation
    public void cancelReservation(Reservation reservation) {
        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation d'annulation");
        confirmationAlert.setHeaderText("Confirmer l'annulation");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir annuler cette réservation ?");

        // Attendre la réponse de l'utilisateur
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // Si l'utilisateur confirme, annuler la réservation
        if (result == ButtonType.OK) {
            serviceReservation.delete(reservation); // Supprimer la réservation
            loadReservations(); // Recharger les réservations après suppression
            System.out.println("Réservation annulée avec succès.");
        } else {
            System.out.println("Annulation de la réservation annulée.");
        }
    }
}