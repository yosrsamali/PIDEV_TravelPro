package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    @FXML
    private VBox avisContainer; // Conteneur des avis

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {
        afficherAvisNonAcceptes();
    }

    private void afficherAvisNonAcceptes() {
        List<Avis> avisNonAcceptes = serviceAvis.getAvisNonAcceptes();

        for (Avis avis : avisNonAcceptes) {
            try {
                // Charger le modèle de carte depuis avis_card.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/views/avis_card.fxml"));
                AnchorPane card = loader.load();

                // Récupérer les éléments FXML de la carte
                Label dateLabel = (Label) card.lookup("#dateLabel");
                Label noteLabel = (Label) card.lookup("#noteLabel");
                Label commentLabel = (Label) card.lookup("#commentLabel");
                Button acceptButton = (Button) card.lookup("#acceptButton");
                Button deleteButton = (Button) card.lookup("#deleteButton");

                // Mettre à jour les labels avec les données de l'avis
                dateLabel.setText("Date: " + avis.getDate_publication());
                noteLabel.setText("Note: " + avis.getNote());
                commentLabel.setText("Commentaire: " + avis.getCommentaire());

                // Action bouton Accepter
                acceptButton.setOnAction(event -> {
                    avis.setEstAccepte(true);
                    serviceAvis.update(avis);
                    avisContainer.getChildren().remove(card);
                });

                // Action bouton Supprimer
                deleteButton.setOnAction(event -> {
                    serviceAvis.delete(avis);
                    avisContainer.getChildren().remove(card);
                });

                // Ajouter la carte d'avis dans la VBox
                avisContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
