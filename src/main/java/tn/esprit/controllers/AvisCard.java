package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.io.IOException;
import java.util.List;

public class AvisCard {

    @FXML
    private VBox avisContainer;

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {
        loadAvisNonAcceptes();
    }

    private void loadAvisNonAcceptes() {
        avisContainer.getChildren().clear(); // Nettoyer avant d'ajouter
        List<Avis> avisList = serviceAvis.getAvisNonAcceptes();

        for (Avis avis : avisList) {
            HBox avisCard = createAvisCard(avis);
            avisContainer.getChildren().add(avisCard);
        }
    }

    private HBox createAvisCard(Avis avis) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

        Label lblCommentaire = new Label("📝 " + avis.getCommentaire());
        Label lblNote = new Label("⭐ Note: " + avis.getNote());

        Button btnAccepter = new Button("✅ Accepter");
        btnAccepter.setOnAction(e -> {
            avis.setEstAccepte(true);
            serviceAvis.update(avis);
            loadAvisNonAcceptes();
        });

        Button btnSupprimer = new Button("🗑️ Supprimer");
        btnSupprimer.setOnAction(e -> {
            serviceAvis.delete(avis);
            loadAvisNonAcceptes();
        });

        card.getChildren().addAll(lblCommentaire, lblNote, btnAccepter, btnSupprimer);
        return card;
    }
}
