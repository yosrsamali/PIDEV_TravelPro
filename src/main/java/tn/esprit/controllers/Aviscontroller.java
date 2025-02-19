package tn.esprit.controllers;

import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;

public class AvisController {
    @FXML
    private VBox avisContainer;  // Conteneur où afficher les avis

    private final ServiceAvis avisService = new ServiceAvis();

    @FXML
    public void initialize() {
        afficherAvis();
    }

    private void afficherAvis() {
        List<Avis> avisList = avisService.getAvisAcceptes();
        avisContainer.getChildren().clear();

        for (Avis avis : avisList) {
            VBox card = new VBox();
            card.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-radius: 10; -fx-background-color: white; -fx-margin: 5px;");

            Label commentaireLabel = new Label("Commentaire: " + avis.getCommentaire());
            Label noteLabel = new Label("Note: " + avis.getNote());

            card.getChildren().addAll(commentaireLabel, noteLabel);
            avisContainer.getChildren().add(card);
        }
    }
}
