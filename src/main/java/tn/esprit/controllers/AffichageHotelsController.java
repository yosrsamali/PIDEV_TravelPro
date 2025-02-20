package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

import java.io.IOException;
import java.util.List;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionHotel.fxml"));
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
        VBox card = new VBox();
        card.setSpacing(10);
        card.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #f0f0f0;");
        card.setPadding(new javafx.geometry.Insets(10));

        // Ajouter les labels pour afficher les informations de l'hôtel
        Label lblNom = new Label("Nom: " + hotel.getNom());
        Label lblVille = new Label("Ville: " + hotel.getVille());
        Label lblPrixParNuit = new Label("Prix par nuit: " + hotel.getPrixParNuit() + " €");
        Label lblDisponible = new Label("Disponible: " + (hotel.isDisponible() ? "Oui" : "Non"));
        Label lblNombreEtoile = new Label("Nombre d'étoiles: " + hotel.getNombreEtoile());
        Label lblTypeDeChambre = new Label("Type de chambre: " + hotel.getTypeDeChambre());

        // Bouton "Modifier"
        Button btnModifier = new Button(" Modifier  ");
        btnModifier.setOnAction(event -> handleModifier(hotel)); // Passer l'hôtel

        // Bouton "Supprimer"
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> handleSupprimer(hotel)); // Passer l'hôtel

        // Bouton "Détails"


        // Ajouter les éléments à la carte
        card.getChildren().addAll(
                lblNom, lblVille, lblPrixParNuit, lblDisponible,
                lblNombreEtoile, lblTypeDeChambre, btnModifier, btnSupprimer
        );

        return card;
    }

    @FXML
    private void handleModifier(Hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierHotel.fxml"));
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
        // Supprimer l'hôtel
        serviceHotel.delete(hotel);
        loadHotels(); // Recharger la liste des hôtels après suppression
    }

    @FXML
    private void handleDetails(Hotel hotel) {
        // Afficher les détails de l'hôtel
        System.out.println("Détails de l'hôtel: " + hotel);
    }}