package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Hotel;
import tn.esprit.models.Reservation;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceHotel;
import tn.esprit.services.ServiceReservation;
import tn.esprit.services.ServiceVoiture;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ReservationController {

    // Champs pour les billets d'avion
    @FXML private TextField departureField;
    @FXML private TextField arrivalField;
    @FXML private DatePicker travelDatePicker;
    @FXML private DatePicker returnDatePicker;
    @FXML private ComboBox<String> classComboBox;
    @FXML private ListView<BilletAvion> billetListView;

    // Champs pour les voitures
    @FXML private ListView<Voiture> voitureListView;

    // Champs pour les hôtels
    @FXML private ComboBox<String> typeChambreComboBox;
    @FXML private ListView<Hotel> hotelListView;

    // Boutons
    @FXML private Button addHotelButton;
    @FXML private Button addCarButton;

    // Services
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();
    private final ServiceVoiture serviceVoiture = new ServiceVoiture();
    private final ServiceHotel serviceHotel = new ServiceHotel();
    private final ServiceReservation serviceReservation = new ServiceReservation();

    // Attributs pour stocker les sélections
    private BilletAvion selectedBillet;
    private Voiture selectedVoiture;
    private Hotel selectedHotel;

    @FXML
    public void initialize() {
        // Initialiser les options du ComboBox pour la classe du billet
        classComboBox.getItems().addAll("Économique", "Affaire", "Première");

        // Initialiser les options du ComboBox pour le type de chambre
        typeChambreComboBox.getItems().addAll("Single", "Double");

        // Ajouter des écouteurs pour capturer les sélections
        billetListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedBillet = newSelection;
        });

        voitureListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedVoiture = newSelection;
        });

        hotelListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedHotel = newSelection;
        });
    }

    // Méthode pour rechercher les billets d'avion disponibles
    @FXML
    public void handleSearchBillets() {
        try {
            String villeDepart = departureField.getText();
            String villeArrivee = arrivalField.getText();
            Date dateDepart = Date.valueOf(travelDatePicker.getValue());
            Date dateRetour = Date.valueOf(returnDatePicker.getValue());
            String classBillet = classComboBox.getValue();

            List<BilletAvion> availableBillets = serviceBilletAvion.getAvailableBillets(villeDepart, villeArrivee, dateDepart, dateRetour, classBillet);

            billetListView.getItems().clear();
            billetListView.getItems().addAll(availableBillets);

            System.out.println("Nombre de billets trouvés : " + availableBillets.size());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la recherche des billets.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour rechercher les voitures disponibles
    @FXML
    public void handleSearchVoiture() {
        try {
            Date dateDeLocation = Date.valueOf(travelDatePicker.getValue());
            Date dateDeRemise = Date.valueOf(returnDatePicker.getValue());

            List<Voiture> availableVoitures = serviceVoiture.getAvailableVoitures(dateDeLocation, dateDeRemise);

            voitureListView.getItems().clear();
            voitureListView.getItems().addAll(availableVoitures);

            System.out.println("Nombre de voitures trouvées : " + availableVoitures.size());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la recherche des voitures.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour rechercher les hôtels disponibles
    @FXML
    public void handleSearchHotel() {
        try {
            String villeArrivee = arrivalField.getText();
            Date dateCheckIn = Date.valueOf(travelDatePicker.getValue());
            Date dateCheckOut = Date.valueOf(returnDatePicker.getValue());
            String typeDeChambre = typeChambreComboBox.getValue();

            List<Hotel> availableHotels = serviceHotel.getAvailableHotels(villeArrivee, dateCheckIn, dateCheckOut, typeDeChambre);

            hotelListView.getItems().clear();
            hotelListView.getItems().addAll(availableHotels);

            System.out.println("Nombre d'hôtels trouvés : " + availableHotels.size());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la recherche des hôtels.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour valider la réservation
    @FXML
    public void handleSubmitReservation() {
        if (selectedBillet == null || selectedVoiture == null || selectedHotel == null) {
            showAlert("Erreur", "Veuillez sélectionner un billet, une voiture et un hôtel.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de réservation");
        confirmationAlert.setHeaderText("Confirmer la réservation");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir créer cette réservation ?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            Reservation reservation = new Reservation(
                    selectedVoiture.getId(),
                    selectedBillet.getId(),
                    selectedHotel.getId(),
                    0,  // id_client (à remplacer par l'ID du client connecté)
                    "En attente"
            );

            serviceReservation.add(reservation);

            // Envoyer un e-mail avec les informations de la réservation
            String clientEmail = "oussemaxxj@gmail.com"; // Remplacez par l'e-mail du client
            String subject = "Confirmation de votre réservation";
            String content = "Votre réservation a été créée avec succès.\n\n" +
                    "Détails de la réservation :\n" +
                    "Billet : " + selectedBillet.getCompagnie() + " - " + selectedBillet.getPrix() + " €\n" +
                    "Voiture : " + selectedVoiture.getMarque() + " " + selectedVoiture.getModele() + "\n" +
                    "Hôtel : " + selectedHotel.getNom() + " - " + selectedHotel.getVille() + "\n";

            EmailService emailService = new EmailService("yassin.abida00@gmail.com", "kbng hjxw ijwr zfhr");
            emailService.sendEmail(clientEmail, subject, content);

            System.out.println("Réservation créée avec succès !");
            System.out.println("Billet : " + selectedBillet.getCompagnie() + " - " + selectedBillet.getPrix() + " €");
            System.out.println("Voiture : " + selectedVoiture.getMarque() + " " + selectedVoiture.getModele());
            System.out.println("Hôtel : " + selectedHotel.getNom() + " - " + selectedHotel.getVille());

            showAlert("Succès", "La réservation a été créée avec succès !", Alert.AlertType.INFORMATION);
            clearForm();
        } else {
            System.out.println("Création de la réservation annulée.");
        }
    }
    // Méthode pour vider le formulaire
    private void clearForm() {
        departureField.clear();
        arrivalField.clear();
        travelDatePicker.setValue(null);
        returnDatePicker.setValue(null);
        classComboBox.getSelectionModel().clearSelection();
        typeChambreComboBox.getSelectionModel().clearSelection();
        billetListView.getItems().clear();
        voitureListView.getItems().clear();
        hotelListView.getItems().clear();
        selectedBillet = null;
        selectedVoiture = null;
        selectedHotel = null;
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleShowReservations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Mes réservations");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la vue des réservations.", Alert.AlertType.ERROR);
        }
    }
}