package tn.esprit.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class AvisController {

    @FXML
    private TextField noteField;

    @FXML
    private TextField commentaireField;

    @FXML
    private Button submitButton;

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> ajouterAvis());
    }

    private void ajouterAvis() {
        try {
            String noteText = noteField.getText().trim();
            String commentaire = commentaireField.getText().trim();

            // Vérifier si la note est un entier
            if (!noteText.matches("\\d+")) {
                showAlert("Erreur", "❌ La note doit être un nombre entier.");
                return;
            }

            int note = Integer.parseInt(noteText);

            // Vérifier si la note est entre 0 et 10
            if (note < 0 || note > 10) {
                showAlert("Erreur", "❌ La note doit être comprise entre 0 et 10.");
                return;
            }

            // Vérifier si le commentaire n'est pas vide
            if (commentaire.isEmpty()) {
                showAlert("Erreur", "❌ Le commentaire ne peut pas être vide.");
                return;
            }

            // Filtrer le commentaire avec PurgoMalum
            filtrerCommentaire(commentaire).thenAccept(commentaireFiltre -> {
                // Vérifier si des mots inappropriés ont été détectés
                if (commentaireFiltre.contains("***")) {
                    // Compter le nombre de mots inappropriés
                    long nombreMotsInappropriés = commentaireFiltre.chars().filter(ch -> ch == '*').count() / 3;
                    Platform.runLater(() -> showAlert("Avertissement", "⚠️ Ce commentaire contient " + nombreMotsInappropriés + " mot(s) inapproprié(s)."));
                }

                // Créer un avis avec le commentaire filtré
                Avis avis = new Avis(0, note, commentaireFiltre, new Timestamp(new Date().getTime()), false);
                serviceAvis.add(avis);

                // Afficher un message de succès
                Platform.runLater(() -> {
                    showAlert("Succès", "✅ Avis ajouté avec succès !");
                    noteField.clear();
                    commentaireField.clear();
                });

                // Envoyer une notification système
                notifierAdmin();
            }).exceptionally(ex -> {
                // Gérer les erreurs (comme IOException)
                Platform.runLater(() -> showAlert("Erreur", "❌ Erreur lors du filtrage du commentaire : " + ex.getCause().getMessage()));
                return null;
            });

        } catch (Exception e) {
            showAlert("Erreur", "❌ Une erreur s'est produite : " + e.getMessage());
        }
    }

    private CompletableFuture<String> filtrerCommentaire(String commentaire) {
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
                        return extraireCommentaireFiltre(jsonResponse);
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (title.equals("Succès")) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void notifierAdmin() {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().getImage("icon.png"); // Assurez-vous d'avoir une icône
                TrayIcon trayIcon = new TrayIcon(image, "Notification");
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);

                trayIcon.displayMessage("Nouvel Avis", "📢 Un nouvel avis a été ajouté !", TrayIcon.MessageType.INFO);
            } catch (Exception e) {
                System.out.println("Erreur lors de l'envoi de la notification : " + e.getMessage());
            }
        } else {
            System.out.println("Le système ne supporte pas les notifications.");
        }
    }
}