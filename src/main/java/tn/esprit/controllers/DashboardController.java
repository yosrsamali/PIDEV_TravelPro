package tn.esprit.controllers;

import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;

public class DashboardController {
    @FXML
    private VBox avisContainer;  // Conteneur où afficher les avis

    private final ServiceAvis avisService = new ServiceAvis();

    @FXML
    public void initialize() {
        afficherAvisEnAttente();
    }

    private void afficherAvisEnAttente() {
        List<Avis> avisEnAttente = avisService.getAvisEnAttente();
        avisContainer.getChildren().clear();

        for (Avis avis : avisEnAttente) {
            VBox card = new VBox();
            card.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #f4f4f4;");

            Label commentaireLabel = new Label("Commentaire: " + avis.getCommentaire());
            Label noteLabel = new Label("Note: " + avis.getNote());
            Button accepterButton = new Button("Accepter");

            accepterButton.setOnAction(event -> {
                avisService.accepterAvis(avis.getId_avis());
                afficherAvisEnAttente(); // Refresh
            });

            card.getChildren().addAll(commentaireLabel, noteLabel, accepterButton);
            avisContainer.getChildren().add(card);
        }
    }
}
