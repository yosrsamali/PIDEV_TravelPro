package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tn.esprit.models.BilletAvion;
import tn.esprit.services.ServiceBilletAvion;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestionBilletAvion {

    @FXML
    private TextField tfCompagnie, tfClassBillet, tfVilleDepart, tfVilleArrivee, tfDateDepart, tfDateArrivee, tfPrix;

    @FXML
    private VBox billetContainer;

    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();

    @FXML
    public void handleAjouterBillet() {
        if (tfCompagnie.getText().isEmpty() || tfClassBillet.getText().isEmpty() ||
                tfVilleDepart.getText().isEmpty() || tfVilleArrivee.getText().isEmpty() ||
                tfDateDepart.getText().isEmpty() || tfDateArrivee.getText().isEmpty() || tfPrix.getText().isEmpty()) {
            showAlert("Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        Date dateDepart, dateArrivee;
        try {
            dateDepart = dateFormat.parse(tfDateDepart.getText());
            dateArrivee = dateFormat.parse(tfDateArrivee.getText());
        } catch (ParseException e) {
            showAlert("Erreur de format de date", "Le format de la date doit être dd-MM-yyyy.");
            return;
        }

        double prix;
        try {
            prix = Double.parseDouble(tfPrix.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de format de prix", "Le prix doit être un nombre valide.");
            return;
        }

        BilletAvion billetAvion = new BilletAvion(
                0,tfCompagnie.getText(), tfClassBillet.getText(), tfVilleDepart.getText(),
                tfVilleArrivee.getText(), dateDepart, dateArrivee, prix
        );

        serviceBilletAvion.add(billetAvion);
        showAlert("Succès", "Le billet d'avion a été ajouté avec succès.");
        afficherBillets(); // Mise à jour après ajout
    }
    @FXML
    private void handleNaviguerAffichage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageBilletAvion.fxml"));
            Parent root = loader.load();
            tfCompagnie.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleAfficherBillets() {
        afficherBillets();
    }

    private void afficherBillets() {
        billetContainer.getChildren().clear(); // Efface l'affichage précédent
        List<BilletAvion> billets = serviceBilletAvion.getAll();

        for (BilletAvion billet : billets) {
            Pane card = creerCarteBillet(billet);
            billetContainer.getChildren().add(card);
        }
    }

    private Pane creerCarteBillet(BilletAvion billet) {
        HBox card = new HBox(10);
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");

        Label compagnieLabel = new Label("Compagnie: " + billet.getCompagnie());
        compagnieLabel.setFont(new Font(14));

        Label prixLabel = new Label("Prix: " + billet.getPrix() + " €");
        prixLabel.setFont(new Font(14));

        Label trajetLabel = new Label(billet.getVilleDepart() + " → " + billet.getVilleArrivee());
        trajetLabel.setFont(new Font(14));

        card.getChildren().addAll(compagnieLabel, trajetLabel, prixLabel);
        return card;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
