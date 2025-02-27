package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Hotel;
import tn.esprit.models.Reservation;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceHotel;
import tn.esprit.services.ServiceReservation;
import tn.esprit.services.ServiceVoiture;

import java.sql.Date;
import java.util.List;

public class ReservationController {

    // Champs pour les billets d'avion
    @FXML private TextField departureField;
    @FXML private TextField arrivalField;
    @FXML private DatePicker travelDatePicker;
    @FXML private DatePicker returnDatePicker;
    @FXML private ComboBox<String> classComboBox;
    @FXML private ListView<BilletAvion> billetListView;  // Changé pour stocker des objets BilletAvion

    // Champs pour les voitures
    @FXML private ListView<Voiture> voitureListView;  // Changé pour stocker des objets Voiture

    // Champs pour les hôtels
    @FXML private ComboBox<String> typeChambreComboBox;
    @FXML private ListView<Hotel> hotelListView;  // Changé pour stocker des objets Hotel

    // Boutons
    @FXML private Button addHotelButton;
    @FXML private Button addCarButton;

    // Services
    private ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();
    private ServiceVoiture serviceVoiture = new ServiceVoiture();
    private ServiceHotel serviceHotel = new ServiceHotel();
    private ServiceReservation serviceReservation = new ServiceReservation();  // Ajout du service de réservation

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
            if (newSelection != null) {
                selectedBillet = newSelection;
            }
        });

        voitureListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedVoiture = newSelection;
            }
        });

        hotelListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedHotel = newSelection;
            }
        });
    }

    // Méthode pour rechercher les billets d'avion disponibles
    @FXML
    public void handleSearchBillets() {
        // Récupérer les valeurs des champs
        String villeDepart = departureField.getText();
        String villeArrivee = arrivalField.getText();
        Date dateDepart = Date.valueOf(travelDatePicker.getValue());
        Date dateRetour = Date.valueOf(returnDatePicker.getValue());
        String classBillet = classComboBox.getValue();

        // Rechercher les billets disponibles
        List<BilletAvion> availableBillets = serviceBilletAvion.getAvailableBillets(villeDepart, villeArrivee, dateDepart, dateRetour, classBillet);

        // Afficher les résultats dans la ListView
        billetListView.getItems().clear();
        billetListView.getItems().addAll(availableBillets);

        System.out.println("Nombre de billets trouvés : " + availableBillets.size());
    }

    // Méthode pour rechercher les voitures disponibles
    @FXML
    public void handleSearchVoiture() {
        // Récupérer les valeurs des champs
        Date dateDeLocation = Date.valueOf(travelDatePicker.getValue());
        Date dateDeRemise = Date.valueOf(returnDatePicker.getValue());

        // Rechercher les voitures disponibles
        List<Voiture> availableVoitures = serviceVoiture.getAvailableVoitures(dateDeLocation, dateDeRemise);

        // Afficher les résultats dans la ListView
        voitureListView.getItems().clear();
        voitureListView.getItems().addAll(availableVoitures);

        System.out.println("Nombre de voitures trouvées : " + availableVoitures.size());
    }

    // Méthode pour rechercher les hôtels disponibles
    @FXML
    public void handleSearchHotel() {
        // Récupérer les valeurs des champs
        String villeArrivee = arrivalField.getText();
        Date dateCheckIn = Date.valueOf(travelDatePicker.getValue());
        Date dateCheckOut = Date.valueOf(returnDatePicker.getValue());
        String typeDeChambre = typeChambreComboBox.getValue();

        // Rechercher les hôtels disponibles
        List<Hotel> availableHotels = serviceHotel.getAvailableHotels(villeArrivee, dateCheckIn, dateCheckOut, typeDeChambre);

        // Afficher les résultats dans la ListView
        hotelListView.getItems().clear();
        hotelListView.getItems().addAll(availableHotels);

        System.out.println("Nombre d'hôtels trouvés : " + availableHotels.size());
    }

    // Méthode pour valider la réservation
    @FXML
    public void handleSubmitReservation() {
        // Vérifier que tous les éléments sont sélectionnés
        if (selectedBillet == null || selectedVoiture == null || selectedHotel == null) {
            System.out.println("Veuillez sélectionner un billet, une voiture et un hôtel.");
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de réservation");
        confirmationAlert.setHeaderText("Confirmer la réservation");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir créer cette réservation ?");

        // Attendre la réponse de l'utilisateur
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // Si l'utilisateur confirme, créer la réservation
        if (result == ButtonType.OK) {
            // Créer une nouvelle réservation
            Reservation reservation = new Reservation(
                    selectedVoiture.getId(),
                    selectedBillet.getId(),
                    selectedHotel.getId(),
                    1,  // id_client (à remplacer par l'ID du client connecté)
                    "En attente"  // statut de la réservation
            );

            // Ajouter la réservation à la base de données
            serviceReservation.add(reservation);

            // Afficher un message de succès
            System.out.println("Réservation créée avec succès !");
            System.out.println("Billet : " + selectedBillet.getCompagnie() + " - " + selectedBillet.getPrix() + " €");
            System.out.println("Voiture : " + selectedVoiture.getMarque() + " " + selectedVoiture.getModele());
            System.out.println("Hôtel : " + selectedHotel.getNom() + " - " + selectedHotel.getVille());

            // Afficher une boîte de dialogue d'information pour confirmer l'ajout
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Réservation créée");
            successAlert.setHeaderText(null);
            successAlert.setContentText("La réservation a été créée avec succès !");
            successAlert.showAndWait();

            // Vider le formulaire après l'ajout
            clearForm();
        } else {
            // Afficher un message si l'utilisateur annule
            System.out.println("Création de la réservation annulée.");
        }
    }

    // Méthode pour vider le formulaire
    private void clearForm() {
        // Réinitialiser les champs de texte
        departureField.clear();
        arrivalField.clear();

        // Réinitialiser les DatePicker
        travelDatePicker.setValue(null);
        returnDatePicker.setValue(null);

        // Réinitialiser les ComboBox
        classComboBox.getSelectionModel().clearSelection();
        typeChambreComboBox.getSelectionModel().clearSelection();

        // Réinitialiser les ListView
        billetListView.getItems().clear();
        voitureListView.getItems().clear();
        hotelListView.getItems().clear();

        // Réinitialiser les sélections
        selectedBillet = null;
        selectedVoiture = null;
        selectedHotel = null;
    }
}