package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tn.esprit.models.BilletAvion;
import tn.esprit.services.ServiceBilletAvion;

import java.io.IOException;
import java.util.List;

public class AffichageBilletController {

    @FXML
    private VBox billetContainer;

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
        billetContainer.getChildren().clear();
        List<BilletAvion> billets = serviceBilletAvion.getAll();

        for (BilletAvion billet : billets) {
            Pane card = creerCarteBillet(billet);
            billetContainer.getChildren().add(card);
        }
    }

    private Pane creerCarteBillet(BilletAvion billet) {
        HBox card = new HBox(10);
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");

        Label compagnieLabel = new Label("Compagnie: " + billet.getCompagnie());
        compagnieLabel.setFont(new Font(14));

        Label prixLabel = new Label("Prix: " + billet.getPrix() + " €");
        prixLabel.setFont(new Font(14));

        Label trajetLabel = new Label(billet.getVilleDepart() + " → " + billet.getVilleArrivee());
        trajetLabel.setFont(new Font(14));

        card.getChildren().addAll(compagnieLabel, trajetLabel, prixLabel);
        return card;
    }
}
