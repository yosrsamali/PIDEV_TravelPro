package tn.esprit.Controllers.controllers_reservation;

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
import tn.esprit.utils.SessionManager;
import tn.esprit.models.Utilisateur;
import tn.esprit.models.Client;

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
    @FXML private ComboBox<BilletAvion> billetComboBox;

    // Champs pour les voitures
    @FXML private ComboBox<Voiture> voitureComboBox;

    // Champs pour les hôtels
    @FXML private ComboBox<String> typeChambreComboBox;
    @FXML private ComboBox<Hotel> hotelComboBox;

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

        // Ajouter des écouteurs pour détecter les changements dans les champs
        departureField.textProperty().addListener((obs, oldValue, newValue) -> updateBillets());
        arrivalField.textProperty().addListener((obs, oldValue, newValue) -> updateBillets());
        travelDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> updateBillets());
        returnDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> updateBillets());
        classComboBox.valueProperty().addListener((obs, oldValue, newValue) -> updateBillets());

        travelDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> updateVoitures());
        returnDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> updateVoitures());

        arrivalField.textProperty().addListener((obs, oldValue, newValue) -> updateHotels());
        travelDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> updateHotels());
        returnDatePicker.valueProperty().addListener((obs, oldValue, newValue) -> updateHotels());
        typeChambreComboBox.valueProperty().addListener((obs, oldValue, newValue) -> updateHotels());

        // Ajouter des écouteurs pour capturer les sélections
        billetComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedBillet = newSelection;
        });

        voitureComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedVoiture = newSelection;
        });

        hotelComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedHotel = newSelection;
        });
    }

    // Méthode pour mettre à jour les billets disponibles
    private void updateBillets() {
        if (departureField.getText().isEmpty() || arrivalField.getText().isEmpty() || travelDatePicker.getValue() == null || returnDatePicker.getValue() == null || classComboBox.getValue() == null) {
            return;
        }

        try {
            String villeDepart = departureField.getText();
            String villeArrivee = arrivalField.getText();
            Date dateDepart = Date.valueOf(travelDatePicker.getValue());
            Date dateRetour = Date.valueOf(returnDatePicker.getValue());
            String classBillet = classComboBox.getValue();

            List<BilletAvion> availableBillets = serviceBilletAvion.getAvailableBillets(villeDepart, villeArrivee, dateDepart, dateRetour, classBillet);

            billetComboBox.getItems().clear();
            billetComboBox.getItems().addAll(availableBillets);

            System.out.println("Nombre de billets trouvés : " + availableBillets.size());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la recherche des billets.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour mettre à jour les voitures disponibles
    private void updateVoitures() {
        if (travelDatePicker.getValue() == null || returnDatePicker.getValue() == null) {
            return;
        }

        try {
            Date dateDeLocation = Date.valueOf(travelDatePicker.getValue());
            Date dateDeRemise = Date.valueOf(returnDatePicker.getValue());

            List<Voiture> availableVoitures = serviceVoiture.getAvailableVoitures(dateDeLocation, dateDeRemise);

            voitureComboBox.getItems().clear();
            voitureComboBox.getItems().addAll(availableVoitures);

            System.out.println("Nombre de voitures trouvées : " + availableVoitures.size());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la recherche des voitures.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour mettre à jour les hôtels disponibles
    private void updateHotels() {
        if (arrivalField.getText().isEmpty() || travelDatePicker.getValue() == null || returnDatePicker.getValue() == null || typeChambreComboBox.getValue() == null) {
            return;
        }

        try {
            String villeArrivee = arrivalField.getText();
            Date dateCheckIn = Date.valueOf(travelDatePicker.getValue());
            Date dateCheckOut = Date.valueOf(returnDatePicker.getValue());
            String typeDeChambre = typeChambreComboBox.getValue();

            List<Hotel> availableHotels = serviceHotel.getAvailableHotels(villeArrivee, dateCheckIn, dateCheckOut, typeDeChambre);

            hotelComboBox.getItems().clear();
            hotelComboBox.getItems().addAll(availableHotels);

            System.out.println("Nombre d'hôtels trouvés : " + availableHotels.size());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la recherche des hôtels.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour valider la réservation
    @FXML
    public void handleSubmitReservation() {
        // Contrôle de saisie pour les dates
        if (travelDatePicker.getValue() == null || returnDatePicker.getValue() == null) {
            showAlert("Erreur", "Veuillez sélectionner une date de départ et une date de retour.", Alert.AlertType.WARNING);
            return;
        }

        if (travelDatePicker.getValue().isAfter(returnDatePicker.getValue())) {
            showAlert("Erreur", "La date de départ doit être antérieure à la date de retour.", Alert.AlertType.WARNING);
            return;
        }

        // Contrôle de saisie pour les lieux de départ et d'arrivée
        if (!isValidLocation(departureField.getText()) || !isValidLocation(arrivalField.getText())) {
            showAlert("Erreur", "Le lieu de départ et d'arrivée ne doivent pas contenir de chiffres ou de caractères spéciaux.", Alert.AlertType.WARNING);
            return;
        }

        // Vérification des sélections
        if (selectedBillet == null || selectedVoiture == null || selectedHotel == null) {
            showAlert("Erreur", "Veuillez sélectionner un billet, une voiture et un hôtel.", Alert.AlertType.WARNING);
            return;
        }

        // Récupérer l'ID du client connecté
        int clientId = getClientId();
        if (clientId == -1) {
            showAlert("Erreur", "Aucun client connecté.", Alert.AlertType.WARNING);
            return;
        }
        System.out.println("id clientId" );
        System.out.println( clientId );



        // Confirmation de la réservation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de réservation");
        confirmationAlert.setHeaderText("Confirmer la réservation");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir créer cette réservation ?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // Créer la réservation avec l'ID du client connecté
            Reservation reservation = new Reservation(
                    selectedVoiture.getId(),
                    selectedBillet.getId(),
                    selectedHotel.getId(),
                    clientId,  // Utilisation de l'ID du client connecté
                    "En attente"
            );

            serviceReservation.add(reservation);

            // Envoyer l'e-mail de confirmation
            String clientEmail = "yassin.abida@esprit.tn"; // E-mail du client
            String subject = "Creation de réservation";

            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"fr\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Creation de réservation</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 0; }\n" +
                    "        .container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }\n" +
                    "        h1 { color: #007BFF; text-align: center; }\n" +
                    "        .welcome-message { font-size: 18px; margin-bottom: 20px; }\n" +
                    "        .details { margin-bottom: 20px; }\n" +
                    "        .details h2 { color: #007BFF; margin-bottom: 10px; }\n" +
                    "        .details p { margin: 5px 0; }\n" +
                    "        .footer { text-align: center; margin-top: 20px; font-size: 14px; color: #777; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h1>Creation de reservation</h1>\n" +
                    "        <div class=\"welcome-message\">\n" +
                    "            Bonjour <strong>" + clientEmail + "</strong>,<br>\n" +
                    "            Merci d'avoir choisi nos services. Voici les details de votre reservation :\n" +
                    "        </div>\n" +
                    "        <div class=\"details\">\n" +
                    "            <h2>Details du billet d'avion</h2>\n" +
                    "            <p><strong>Compagnie :</strong> " + selectedBillet.getCompagnie() + "</p>\n" +
                    "            <p><strong>Prix :</strong> " + selectedBillet.getPrix() + " euro</p>\n" +
                    "            <p><strong>Date de depart :</strong> " + travelDatePicker.getValue() + "</p>\n" +
                    "            <p><strong>Date de retour :</strong> " + returnDatePicker.getValue() + "</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"details\">\n" +
                    "            <h2>Details de la voiture</h2>\n" +
                    "            <p><strong>Marque :</strong> " + selectedVoiture.getMarque() + "</p>\n" +
                    "            <p><strong>Modele :</strong> " + selectedVoiture.getModele() + "</p>\n" +
                    "            <p><strong>Annee :</strong> " + selectedVoiture.getAnnee() + "</p>\n" +
                    "            <p><strong>Prix :</strong> " + selectedVoiture.getPrixParJour() + " euro</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"details\">\n" +
                    "            <h2>Details de l'hotel</h2>\n" +
                    "            <p><strong>Nom :</strong> " + selectedHotel.getNom() + "</p>\n" +
                    "            <p><strong>Ville :</strong> " + selectedHotel.getVille() + "</p>\n" +
                    "            <p><strong>Type de chambre :</strong> " + typeChambreComboBox.getValue() + "</p>\n" +
                    "            <p><strong>Prix :</strong> " + selectedHotel.getPrixParNuit() + " euro</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>Merci de nous faire confiance. Pour toute question, contactez-nous à <a href=\"mailto:support@travelpro.com\">support@travelpro.com</a>.</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

            EmailService emailService = new EmailService("yassin.abida00@gmail.com", "kbng hjxw ijwr zfhr");
            emailService.sendEmail(clientEmail, subject, htmlContent);

            // Afficher un message de confirmation
            showAlert("Succès", "Réservation validée et e-mail envoyé avec succès !", Alert.AlertType.INFORMATION);

            // Actualiser le formulaire
            clearForm();
        } else {
            System.out.println("Création de la réservation annulée.");
        }
    }

    // Méthode pour récupérer l'ID du client connecté
    private int getClientId() {
        Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();
        System.out.println("utilisateur====" );
        System.out.println( utilisateur );
        if (utilisateur instanceof Client) {
            return ((Client) utilisateur).getIdClient();
        }
        return -1; // Retourne -1 si l'utilisateur n'est pas un client
    }

    // Méthode pour vérifier si un lieu est valide
    private boolean isValidLocation(String location) {
        // Expression régulière pour vérifier que le lieu ne contient pas de chiffres ou de caractères spéciaux
        String regex = "^[a-zA-Z\\s]+$";
        return location.matches(regex);
    }

    // Méthode pour vider le formulaire
    private void clearForm() {
        departureField.clear();
        arrivalField.clear();
        travelDatePicker.setValue(null);
        returnDatePicker.setValue(null);
        classComboBox.getSelectionModel().clearSelection();
        typeChambreComboBox.getSelectionModel().clearSelection();
        billetComboBox.getItems().clear();
        voitureComboBox.getItems().clear();
        hotelComboBox.getItems().clear();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/ReservationList.fxml"));
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