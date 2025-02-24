package tn.esprit.controllers;

import tn.esprit.models.Avis;
import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceAvis;
import tn.esprit.services.ServiceReponse;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.util.List;

public class Listreponse {

    private static int idavis;

    @FXML
    private Label avisLabel;
    @FXML
    private VBox reponsesVBox;


    public void setIdavis(int idavis) {
        Listreponse.idavis = idavis;
    }


    @FXML
    private void initialize() {
        displayAvisAndReponses();
    }

    private void displayAvisAndReponses() {
        ServiceAvis serviceAvis = new ServiceAvis();
        ServiceReponse serviceReponse = new ServiceReponse();


        Avis avis = serviceAvis.getById(idavis).orElse(null);

        if (avis == null) {
            avisLabel.setText("Avis not found!");
            return;
        }


        avisLabel.setText("Avis ID: " + avis.getId_avis() + "\n"
                + "Note: " + avis.getNote() + "\n"
                + "Commentaire: " + avis.getCommentaire() + "\n"
                + "Date Publication: " + avis.getDate_publication());


        List<Reponse> reponses = serviceReponse.getReponsesByAvisId(idavis);


        reponsesVBox.getChildren().clear();


        for (Reponse reponse : reponses) {
            HBox reponseCard = new HBox();
            reponseCard.setSpacing(10);

            Label reponseLabel = new Label();
            reponseLabel.setText("Réponse ID: " + reponse.getId_reponse() + "\n"
                    + "Réponse: " + reponse.getReponse() + "\n"
                    + "Date Réponse: " + reponse.getDate_reponse());

            TextField modifyField = new TextField(reponse.getReponse());
            modifyField.setPromptText("Modifier la réponse...");

            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;");

            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");

            modifyButton.setOnAction(event -> {
                String newResponse = modifyField.getText();
                if (!newResponse.isEmpty()) {
                    reponse.setReponse(newResponse);
                    serviceReponse.update(reponse);
                    reponseLabel.setText("Réponse ID: " + reponse.getId_reponse() + "\n"
                            + "Réponse: " + reponse.getReponse() + "\n"
                            + "Date Réponse: " + reponse.getDate_reponse());
                }
            });

            deleteButton.setOnAction(event -> {
                serviceReponse.delete(reponse);
                reponsesVBox.getChildren().remove(reponseCard);
            });

            HBox actionBox = new HBox(10);
            actionBox.getChildren().addAll(modifyButton, deleteButton);

            reponseCard.getChildren().addAll(reponseLabel, modifyField, actionBox);
            reponsesVBox.getChildren().add(reponseCard);
        }
    }
}
