package tn.esprit.controllers.controllerAvis;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AvisCard {

    @FXML
    private VBox avisContainer;

    private final ServiceAvis serviceAvis = new ServiceAvis();

    // Liste des mots inappropriés supplémentaires
    private final List<String> motsInappropriésSupplementaires = Arrays.asList("stupide", "idiot");

    @FXML
    public void initialize() {
        loadAvisNonAcceptes();
    }

    private void loadAvisNonAcceptes() {
        avisContainer.getChildren().clear(); // Nettoyer avant d'ajouter
        List<Avis> avisList = serviceAvis.getAvisNonAcceptes();

        for (Avis avis : avisList) {
            // Vérifier si le commentaire contient des mots inappropriés
            verifierMotsInappropriés(avis.getCommentaire()).thenAccept(contientMotsInappropriés -> {
                HBox avisCard = createAvisCard(avis, contientMotsInappropriés);
                // Utiliser Platform.runLater pour mettre à jour l'interface utilisateur
                Platform.runLater(() -> avisContainer.getChildren().add(avisCard));
            });
        }
    }

    private CompletableFuture<Boolean> verifierMotsInappropriés(String commentaire) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Encoder le commentaire pour l'URL
                String commentaireEncode = URLEncoder.encode(commentaire, StandardCharsets.UTF_8);

                // URL de l'API PurgoMalum
                String url = "http://www.purgomalum.com/service/json?text=" + commentaireEncode;

                // Envoyer la requête GET
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
                connection.setRequestMethod("GET");

                // Lire la réponse
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    try (java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        // Extraire le commentaire filtré de la réponse JSON
                        String jsonResponse = response.toString();
                        String commentaireFiltre = extraireCommentaireFiltre(jsonResponse);

                        // Vérifier si des mots inappropriés ont été détectés par PurgoMalum
                        boolean contientMotsInappropriés = commentaireFiltre.contains("***");

                        // Vérifier les mots inappropriés supplémentaires
                        if (!contientMotsInappropriés) {
                            contientMotsInappropriés = filtrerMotsSupplementaires(commentaireFiltre);
                        }

                        return contientMotsInappropriés;
                    }
                } else {
                    throw new RuntimeException("Erreur API : " + responseCode);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de l'appel à l'API PurgoMalum : " + e.getMessage(), e);
            }
        });
    }

    private String extraireCommentaireFiltre(String jsonResponse) {
        // Exemple de réponse JSON : {"result":"Ceci est un commentaire avec des *** !"}
        int startIndex = jsonResponse.indexOf("\"result\":\"") + 10;
        int endIndex = jsonResponse.lastIndexOf("\"");
        return jsonResponse.substring(startIndex, endIndex);
    }

    // Méthode pour filtrer les mots inappropriés supplémentaires
    private boolean filtrerMotsSupplementaires(String commentaire) {
        for (String mot : motsInappropriésSupplementaires) {
            if (commentaire.toLowerCase().contains(mot.toLowerCase())) {
                return true; // Le commentaire contient un mot inapproprié supplémentaire
            }
        }
        return false; // Aucun mot inapproprié supplémentaire trouvé
    }

    private HBox createAvisCard(Avis avis, boolean contientMotsInappropriés) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

        Label lblCommentaire = new Label("📝 " + avis.getCommentaire());
        Label lblNote = new Label("⭐ Note: " + avis.getNote());

        // Ajouter un message d'avertissement si des mots inappropriés sont détectés
        if (contientMotsInappropriés) {
            Label lblAvertissement = new Label("⚠️ Ce commentaire contient des mots inappropriés");
            lblAvertissement.setStyle("-fx-text-fill: red;");
            card.getChildren().add(lblAvertissement);
        }

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