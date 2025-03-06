package tn.esprit.Controllers.controllers_reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tn.esprit.models.BilletAvion;
import tn.esprit.services.ServiceBilletAvion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AffichageBilletController {

    @FXML
    private FlowPane billetContainer; // FlowPane pour afficher les cartes

    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();

    @FXML
    public void initialize() {
        afficherBillets();
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionBilletAvion.fxml"));
            Parent root = loader.load();
            billetContainer.getScene().setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la vue GestionBilletAvion.");
        }
    }

    private void afficherBillets() {
        billetContainer.getChildren().clear(); // Effacer les cartes précédentes
        List<BilletAvion> billets = serviceBilletAvion.getAll();

        for (BilletAvion billet : billets) {
            VBox card = creerCarteBillet(billet);
            billetContainer.getChildren().add(card);
        }
    }

    private VBox creerCarteBillet(BilletAvion billet) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 10; -fx-padding: 15; -fx-background-color: #ffffff; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 10, 0, 0);");

        // Affichage des informations du billet


        Label compagnieLabel = new Label("Compagnie: " + billet.getCompagnie());
        compagnieLabel.setFont(new Font(14));

        Label classeLabel = new Label("Classe: " + billet.getClass_Billet());
        classeLabel.setFont(new Font(14));

        Label trajetLabel = new Label("Trajet: " + billet.getVilleDepart() + " → " + billet.getVilleArrivee());
        trajetLabel.setFont(new Font(14));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Label dateDepartLabel = new Label("Date de départ: " + dateFormat.format(billet.getDateDepart()));
        dateDepartLabel.setFont(new Font(14));

        Label dateArriveeLabel = new Label("Date d'arrivée: " + dateFormat.format(billet.getDateArrivee()));
        dateArriveeLabel.setFont(new Font(14));

        Label prixLabel = new Label("Prix: " + billet.getPrix() + " €");
        prixLabel.setFont(new Font(14));

        // Boutons de modification et de suppression
        Button btnModifier = new Button("Modifier");
        btnModifier.setStyle(" -fx-text-fill: white;");
        btnModifier.setOnAction(event -> modifierBillet(billet));

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setStyle(" -fx-text-fill: white;");
        btnSupprimer.setOnAction(event -> supprimerBillet(billet));

        HBox buttonBox = new HBox(10, btnModifier, btnSupprimer);
        card.getChildren().addAll( compagnieLabel, classeLabel, trajetLabel, dateDepartLabel, dateArriveeLabel, prixLabel, buttonBox);
        return card;
    }

    private void modifierBillet(BilletAvion billet) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/ModifierBilletAvion.fxml"));
            Parent root = loader.load();

            ModifierBilletAvionController controller = loader.getController();
            controller.setBillet(billet);
            controller.setOnBilletUpdated(updatedBillet -> {
                afficherBillets(); // Rafraîchir la liste après modification
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la vue de modification.");
        }
    }

    private void supprimerBillet(BilletAvion billet) {
        try {
            serviceBilletAvion.delete(billet);
            afficherBillets(); // Rafraîchir la liste après suppression
            showAlert("Succès", "Billet supprimé avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la suppression du billet.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}