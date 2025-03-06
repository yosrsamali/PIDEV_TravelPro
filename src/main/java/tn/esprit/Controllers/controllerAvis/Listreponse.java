package tn.esprit.Controllers.controllerAvis;

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

        // Mise à jour pour ne pas afficher l'ID de l'avis
        avisLabel.setText("Note: " + avis.getNote() + "\n"
                + "Commentaire: " + avis.getCommentaire() + "\n"
                + "Date Publication: " + avis.getDate_publication());

        List<Reponse> reponses = serviceReponse.getReponsesByAvisId(idavis);

        reponsesVBox.getChildren().clear();

        for (Reponse reponse : reponses) {
            HBox reponseCard = new HBox();
            reponseCard.setSpacing(10);

            Label reponseLabel = new Label();
            reponseLabel.setText("Réponse: " + reponse.getReponse() + "\n"
                    + "Date Réponse: " + reponse.getDate_reponse());

            TextField modifyField = new TextField(reponse.getReponse());
            modifyField.setPromptText("Modifier la réponse...");

            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: #088395; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 5px;");

            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: #088395; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 5px;");

            modifyButton.setOnAction(event -> {
                String newResponse = modifyField.getText().trim(); // Enlève les espaces avant et après le texte
                if (newResponse.isEmpty()) {
                    // Afficher un message d'erreur si le champ est vide ou contient uniquement des espaces
                    modifyField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    modifyField.setPromptText("La réponse ne peut pas être vide !");
                } else {
                    // Réinitialisation du style d'erreur et mise à jour de la réponse si la saisie est valide
                    modifyField.setStyle(""); // Retirer la bordure rouge
                    modifyField.setPromptText("Modifier la réponse...");

                    reponse.setReponse(newResponse); // Mise à jour de la réponse
                    serviceReponse.update(reponse); // Enregistrement de la nouvelle réponse

                    // Mise à jour de l'affichage de la réponse sans l'ID
                    reponseLabel.setText("Réponse: " + reponse.getReponse() + "\n"
                            + "Date Réponse: " + reponse.getDate_reponse()); // Affichage sans l'ID
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
