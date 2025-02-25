package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.services.ServiceAvis;
import tn.esprit.services.ServiceReponse;
import tn.esprit.models.Avis;
import tn.esprit.models.Reponse;
import java.util.Collections;
import java.util.Comparator;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReponseController {

    @FXML
    private VBox cardList;

    private ServiceAvis serviceAvis;

    public void initialize() {
        serviceAvis = new ServiceAvis();
        List<Avis> avisAcceptes = serviceAvis.getAvisAcceptes();
        for (Avis avis : avisAcceptes) {
            VBox card = createAvisCard(avis);
            cardList.getChildren().add(card);
        }
    }

    private VBox createAvisCard(Avis avis) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        Label noteLabel = new Label("Note: " + avis.getNote());
        noteLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label commentaireLabel = new Label("Commentaire: " + avis.getCommentaire());
        commentaireLabel.setStyle("-fx-font-size: 12px;");

        TextField responseField = new TextField();
        responseField.setPromptText("Écrire une réponse...");
        responseField.setStyle("-fx-font-size: 12px; -fx-padding: 5;");

        Button replyButton = new Button("Envoyer");
        replyButton.setStyle("-fx-background-color: #46D0EC; -fx-text-fill: white;");

        Button modifyButton = new Button("Modifier");
        modifyButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;");

        Button deleteButton = new Button("Supprimer");
        deleteButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");

        Button viewResponsesButton = new Button("Consulter Réponse");
        viewResponsesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        ServiceReponse serviceReponse = new ServiceReponse();
        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");

        replyButton.setOnAction(event -> {
            String responseText = responseField.getText();
            if (!responseText.isEmpty()) {
                try {
                    Reponse newReponse = new Reponse(avis.getId_avis(), responseText, new Date());
                    serviceReponse.add(newReponse);
                    feedbackLabel.setText("Réponse envoyée avec succès!");
                    responseField.clear();
                } catch (Exception e) {
                    feedbackLabel.setText("Erreur lors de l'envoi de la réponse.");
                    feedbackLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                feedbackLabel.setText("Veuillez écrire une réponse avant d'envoyer !");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        modifyButton.setOnAction(event -> {
            TextField modifyField = new TextField(avis.getCommentaire());
            Button saveButton = new Button("Enregistrer");
            saveButton.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white;");

            saveButton.setOnAction(saveEvent -> {
                avis.setCommentaire(modifyField.getText());
                serviceAvis.update(avis);
                commentaireLabel.setText("Commentaire: " + avis.getCommentaire());
                card.getChildren().removeAll(modifyField, saveButton);
            });

            card.getChildren().addAll(modifyField, saveButton);
        });

        deleteButton.setOnAction(event -> {
            serviceAvis.delete(avis);
            cardList.getChildren().remove(card);
        });

        viewResponsesButton.setOnAction(event -> {
            Listreponse l1= new Listreponse();
            l1.setIdavis(avis.getId_avis());
            changerScene(event, "/Listreponse.fxml");
        });

        HBox responseBox = new HBox(10);
        responseBox.getChildren().addAll(responseField, replyButton);

        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(modifyButton, deleteButton, viewResponsesButton);

        card.getChildren().addAll(noteLabel, commentaireLabel, responseBox, feedbackLabel, actionBox);
        return card;
    }

    private void changerScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode de filtrage des avis en fonction de la note
    @FXML
    private void filterByNote(ActionEvent event) {
        // Récupère la liste des avis acceptés
        List<Avis> avisAcceptes = serviceAvis.getAvisAcceptes();

        // Trie les avis par note (par exemple, de la plus haute à la plus basse)
        List<Avis> avisFiltres = avisAcceptes.stream()
                .sorted((a1, a2) -> Double.compare(a2.getNote(), a1.getNote())) // Tri décroissant par note
                .collect(Collectors.toList());

        // Vider la liste des cartes actuelles
        cardList.getChildren().clear();

        // Ajouter les cartes triées à la VBox
        for (Avis avis : avisFiltres) {
            VBox card = createAvisCard(avis);
            cardList.getChildren().add(card);
        }

        // Optionnel: Message pour indiquer que le filtrage est effectué
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Filtrage");
        alert.setHeaderText(null);
        alert.setContentText("Les avis ont été triés par note.");
        alert.showAndWait();
    }

    @FXML
    private void afficherAvisAcceptes(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reponse.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et changer la vue
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'interface reponse.fxml : " + e.getMessage());
        }
    }
}
