package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.models.Voiture;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceReservation;
import tn.esprit.services.ServiceVoiture;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceHotel;

import java.io.IOException;
import java.util.List;

public class GestionReservation {

    @FXML
    private FlowPane reservationContainer; // FlowPane pour afficher les cartes

    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ServiceVoiture serviceVoiture = new ServiceVoiture();
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();
    private final ServiceHotel serviceHotel = new ServiceHotel();

    @FXML
    public void initialize() {
        afficherReservations();
    }

    @FXML
    public void afficherReservations() {
        reservationContainer.getChildren().clear(); // Effacer les cartes précédentes
        List<Reservation> reservations = serviceReservation.getAll();

        for (Reservation reservation : reservations) {
            VBox card = creerCarteReservation(reservation);
            reservationContainer.getChildren().add(card);
        }
    }

    private VBox creerCarteReservation(Reservation reservation) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");

        // Détails de la voiture
        Label voitureLabel;
        if (reservation.getId_voiture() == 0) {
            voitureLabel = new Label("Voiture: Non spécifié");
        } else {
            Voiture voiture = serviceVoiture.getById(reservation.getId_voiture());
            voitureLabel = new Label("Voiture: " + (voiture != null ? voiture.getMarque() + " " + voiture.getModele() : "Inconnu"));
        }
        voitureLabel.setFont(new Font(14));

        // Détails du billet avion
        Label billetLabel;
        if (reservation.getId_billetAvion() == 0) {
            billetLabel = new Label("Billet Avion: Non spécifié");
        } else {
            BilletAvion billet = serviceBilletAvion.getById(reservation.getId_billetAvion());
            billetLabel = new Label("Billet Avion: " + (billet != null ? billet.getCompagnie() + " (" + billet.getVilleDepart() + " -> " + billet.getVilleArrivee() + ")" : "Inconnu"));
        }
        billetLabel.setFont(new Font(14));

        // Détails de l'hôtel
        Label hotelLabel;
        if (reservation.getId_hotel() == 0) {
            hotelLabel = new Label("Hôtel: Non spécifié");
        } else {
            Hotel hotel = serviceHotel.getById(reservation.getId_hotel());
            hotelLabel = new Label("Hôtel: " + (hotel != null ? hotel.toString() : "Inconnu"));
        }
        hotelLabel.setFont(new Font(14));

        // Détails du client (on garde l'ID pour l'instant)
        Label clientLabel = new Label("Client: ID " + reservation.getId_client());
        clientLabel.setFont(new Font(14));

        // Statut
        Label statutLabel = new Label("Statut: " + reservation.getStatut());
        statutLabel.setFont(new Font(14));

        // Boutons de modification et de suppression
        Button btnModifier = new Button("  Modifier ");
        btnModifier.setOnAction(event -> modifierReservation(reservation));

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> supprimerReservation(reservation));

        card.getChildren().addAll(voitureLabel, billetLabel, hotelLabel, clientLabel, statutLabel, btnModifier, btnSupprimer);
        return card;
    }

    public void afficherDetails(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsReservation.fxml"));
            Parent root = loader.load();

            DetailsReservationController controller = loader.getController();
            controller.setReservation(reservation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifierReservation(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReservation.fxml"));
            Parent root = loader.load();

            ModifierReservationController controller = loader.getController();
            controller.setReservation(reservation);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(event -> afficherReservations()); // Rafraîchir après fermeture
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void supprimerReservation(Reservation reservation) {
        serviceReservation.delete(reservation);
        afficherReservations(); // Rafraîchir la liste après suppression
    }
}