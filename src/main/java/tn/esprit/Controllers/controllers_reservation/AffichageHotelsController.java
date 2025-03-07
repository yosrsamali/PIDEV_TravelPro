package tn.esprit.Controllers.controllers_reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AffichageHotelsController {

    @FXML
    private FlowPane flowPane; // Conteneur pour les cartes

    private ServiceHotel serviceHotel = new ServiceHotel();

    @FXML
    public void initialize() {
        loadHotels(); // Charger les hôtels au démarrage
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionHotel.fxml"));
            Parent root = loader.load();
            flowPane.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHotels() {
        flowPane.getChildren().clear(); // Effacer les cartes précédentes
        List<Hotel> hotels = serviceHotel.getAll(); // Récupérer tous les hôtels
        for (Hotel hotel : hotels) {
            VBox card = createHotelCard(hotel); // Créer une carte pour chaque hôtel
            flowPane.getChildren().add(card); // Ajouter la carte au FlowPane
        }
    }

    private VBox createHotelCard(Hotel hotel) {
        // Créer une carte (VBox) pour afficher les informations de l'hôtel

        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");
        card.setPadding(new javafx.geometry.Insets(10));

        // Ajouter les labels pour afficher les informations de l'hôtel
        Label lblNom = new Label("Nom: " + hotel.getNom());
        lblNom.setFont(new Font(14));
        Label lblVille = new Label("Ville: " + hotel.getVille());
        lblVille.setFont(new Font(14));
        Label lblPrixParNuit = new Label("Prix par nuit: " + hotel.getPrixParNuit() + " €");
        lblPrixParNuit.setFont(new Font(14));
        Label lblDisponible = new Label("Disponible: " + (hotel.isDisponible() ? "Oui" : "Non"));
        lblDisponible.setFont(new Font(14));
        Label lblNombreEtoile = new Label("Nombre d'étoiles: " + hotel.getNombreEtoile());
        lblNombreEtoile.setFont(new Font(14));
        Label lblTypeDeChambre = new Label("Type de chambre: " + hotel.getTypeDeChambre());
        lblTypeDeChambre.setFont(new Font(14));

        // Nouveaux labels pour dateCheckIn et dateCheckOut
        Label lblDateCheckIn = new Label("Date Check-In: " + hotel.getDateCheckIn());
        lblDateCheckIn.setFont(new Font(14));
        Label lblDateCheckOut = new Label("Date Check-Out: " + hotel.getDateCheckOut());
        lblDateCheckOut.setFont(new Font(14));

        // Bouton "Modifier"
        Button btnModifier = new Button(" Modifier  ");
        btnModifier.setOnAction(event -> handleModifier(hotel)); // Passer l'hôtel

        // Bouton "Supprimer"
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> handleSupprimer(hotel)); // Passer l'hôtel

        // Ajouter les éléments à la carte
        card.getChildren().addAll(
                lblNom, lblVille, lblPrixParNuit, lblDisponible,
                lblNombreEtoile, lblTypeDeChambre, lblDateCheckIn, lblDateCheckOut,
                btnModifier, btnSupprimer
        );

        return card;
    }

    @FXML
    private void handleModifier(Hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/ModifierHotel.fxml"));
            Parent root = loader.load();

            ModifierHotelController controller = loader.getController();
            controller.setHotel(hotel); // Passer l'hôtel à modifier

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recharger les hôtels après modification
            loadHotels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML

    private void handleSupprimer(Hotel hotel) {
        try {
            // Vérifier si l'hôtel est en cours d'utilisation (par exemple, s'il y a des réservations)
            boolean isHotelInUse = serviceHotel.isHotelInUse(hotel.getId());

            if (isHotelInUse) {
                // Afficher une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Cet hôtel est en cours d'utilisation.");
                alert.setContentText("Voulez-vous vraiment supprimer cet hôtel et toutes les réservations associées ?");

                // Ajouter les boutons "Oui" et "Non"
                ButtonType buttonTypeOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeNon = new ButtonType("Non", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                // Attendre la réponse de l'utilisateur
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == buttonTypeOui) {
                    // L'utilisateur a choisi "Oui", forcer la suppression
                    serviceHotel.deleteByHotelId(hotel.getId()); // Supprimer les réservations associées
                    serviceHotel.delete(hotel); // Supprimer l'hôtel
                    loadHotels(); // Recharger la liste des hôtels
                    serviceHotel.showAlert("Succès", "L'hôtel et les réservations associées ont été supprimés avec succès.");
                } else {
                    // L'utilisateur a choisi "Non", annuler la suppression
                    serviceHotel.showAlert("Annulé", "La suppression de l'hôtel a été annulée.");
                }
            } else {
                // L'hôtel n'est pas en cours d'utilisation, le supprimer directement
                serviceHotel.delete(hotel);
                loadHotels(); // Recharger la liste des hôtels
                serviceHotel.showAlert("Succès", "L'hôtel a été supprimé avec succès.");
            }
        } catch (Exception e) {
            serviceHotel.showAlert("Erreur", "Une erreur s'est produite lors de la suppression de l'hôtel : " + e.getMessage());
        }
    }
}
