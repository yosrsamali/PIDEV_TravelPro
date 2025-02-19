package tn.esprit.controllers;

import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceReponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;

public class ReponseController {
    @FXML
    private VBox reponseContainer;  // Conteneur où afficher les réponses

    private final ServiceReponse reponseService = new ServiceReponse();

    public void setAvisId(int idAvis) {
        afficherReponses(idAvis);
    }

    private void afficherReponses(int idAvis) {
        List<Reponse> reponses = reponseService.getReponsesByAvis(idAvis);
        reponseContainer.getChildren().clear();

        for (Reponse reponse : reponses) {
            VBox card = new VBox();
            card.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-radius: 10; -fx-background-color: #e8e8e8; -fx-margin: 5px;");

            Label reponseLabel = new Label("Réponse: " + reponse.getReponse());

            card.getChildren().add(reponseLabel);
            reponseContainer.getChildren().add(card);
        }
    }
}
