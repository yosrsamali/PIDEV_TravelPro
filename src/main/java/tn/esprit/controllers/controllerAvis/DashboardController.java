package tn.esprit.controllers.controllerAvis;

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
    private VBox avisContainer;

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {
        afficherAvisNonAcceptes();
    }

    private void afficherAvisNonAcceptes() {
        List<Avis> avisNonAcceptes = serviceAvis.getAvisNonAcceptes();

        for (Avis avis : avisNonAcceptes) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/views/avis_card.fxml"));
                AnchorPane card = loader.load();


                Label dateLabel = (Label) card.lookup("#dateLabel");
                Label noteLabel = (Label) card.lookup("#noteLabel");
                Label commentLabel = (Label) card.lookup("#commentLabel");
                Button acceptButton = (Button) card.lookup("#acceptButton");
                Button deleteButton = (Button) card.lookup("#deleteButton");


                dateLabel.setText("Date: " + avis.getDate_publication());
                noteLabel.setText("Note: " + avis.getNote());
                commentLabel.setText("Commentaire: " + avis.getCommentaire());


                acceptButton.setOnAction(event -> {
                    avis.setEstAccepte(true);
                    serviceAvis.update(avis);
                    avisContainer.getChildren().remove(card);
                });


                deleteButton.setOnAction(event -> {
                    serviceAvis.delete(avis);
                    avisContainer.getChildren().remove(card);
                });


                avisContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
