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
import tn.esprit.models.BilletAvion;
import tn.esprit.services.ServiceBilletAvion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AffichageBilletController {

    @FXML
    private FlowPane billetContainer; // FlowPane pour afficher les cartes

    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();

    @FXML
    public void initialize() {
        afficherBillets();
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionBilletAvion.fxml"));
            Parent root = loader.load();
            billetContainer.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherBillets() {
        billetContainer.getChildren().clear(); // Effacer les cartes précédentes
        List<BilletAvion> billets = serviceBilletAvion.getAll();

        for (BilletAvion billet : billets) {
            VBox card = creerCarteBillet(billet);
            billetContainer.getChildren().add(card);
        }
    }

    private VBox creerCarteBillet(BilletAvion billet) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");

        // Affichage des informations du billet
        Label idLabel = new Label("ID: " + billet.getId());
        idLabel.setFont(new Font(14));

        Label compagnieLabel = new Label("Compagnie: " + billet.getCompagnie());
        compagnieLabel.setFont(new Font(14));

        Label classeLabel = new Label("Classe: " + billet.getClass_Billet());
        classeLabel.setFont(new Font(14));

        Label trajetLabel = new Label("Trajet: " + billet.getVilleDepart() + " → " + billet.getVilleArrivee());
        trajetLabel.setFont(new Font(14));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Label dateDepartLabel = new Label("Date de départ: " + dateFormat.format(billet.getDateDepart()));
        dateDepartLabel.setFont(new Font(14));

        Label dateArriveeLabel = new Label("Date d'arrivée: " + dateFormat.format(billet.getDateArrivee()));
        dateArriveeLabel.setFont(new Font(14));

        Label prixLabel = new Label("Prix: " + billet.getPrix() + " €");
        prixLabel.setFont(new Font(14));

        // Boutons de modification et de suppression
        Button btnModifier = new Button("Modifier");
        btnModifier.setOnAction(event -> modifierBillet(billet));

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> supprimerBillet(billet));

        card.getChildren().addAll(idLabel, compagnieLabel, classeLabel, trajetLabel, dateDepartLabel, dateArriveeLabel, prixLabel, btnModifier, btnSupprimer);
        return card;
    }

    private void modifierBillet(BilletAvion billet) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierBilletAvion.fxml"));
            Parent root = loader.load();

            ModifierBilletAvionController controller = loader.getController();
            controller.setBillet(billet);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerBillet(BilletAvion billet) {
        serviceBilletAvion.delete(billet);
        afficherBillets(); // Rafraîchir la liste après suppression
    }
}