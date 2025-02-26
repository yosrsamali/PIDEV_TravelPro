package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceReservation;

public class ReservationController {

    @FXML private TextField departureField;
    @FXML private TextField arrivalField;
    @FXML private DatePicker travelDatePicker;
    @FXML private DatePicker returnDatePicker;

    @FXML private Pane flightTicketBox;
    @FXML private ComboBox<String> classComboBox;
    @FXML private TextField airlineField;

    @FXML private Pane hotelBox;
    @FXML private TextField hotelField;

    @FXML private Pane carBox;
    @FXML private ComboBox<String> modeleComboBox;

    @FXML private Button addHotelButton;
    @FXML private Button addCarButton;

    private ServiceReservation serviceReservation = new ServiceReservation();

    @FXML
    public void handleAddFlightTicket() {
        flightTicketBox.setVisible(true);
        //moveElementsDown(flightTicketBox);
    }

    @FXML
    public void handleAddHotel() {
        hotelBox.setVisible(true);
        //moveElementsDown(hotelBox);
    }

    @FXML
    public void handleAddCar() {
        carBox.setVisible(true);
        //moveElementsDown(carBox);
    }

    @FXML
    public void handleSubmitReservation() {
        // Récupérer les valeurs des champs
        String departure = departureField.getText();
        String arrival = arrivalField.getText();
        String travelDate = travelDatePicker.getValue().toString();
        String returnDate = returnDatePicker.getValue().toString();

        // Récupérer les valeurs des champs supplémentaires
        String flightClass = classComboBox.getValue();
        String airline = airlineField.getText();
        String hotelName = hotelField.getText();
        String carModel = modeleComboBox.getValue();

        // Créer la réservation
        Reservation reservation = new Reservation(0, 0, 0, 0, "En attente");

        // Ajouter la réservation via le service
        serviceReservation.add(reservation);

        // Afficher un message de succès ou rediriger l'utilisateur
        System.out.println("Réservation créée avec succès !");
    }

    /*private void moveElementsDown(VBox vbox) {
        double offset = vbox.getHeight() + 20; // Décalage de la hauteur de la VBox + un espace supplémentaire
        addHotelButton.setLayoutY(addHotelButton.getLayoutY() + offset);
        hotelBox.setLayoutY(hotelBox.getLayoutY() + offset);
        addCarButton.setLayoutY(addCarButton.getLayoutY() + offset);
        carBox.setLayoutY(carBox.getLayoutY() + offset);
    }*/
}