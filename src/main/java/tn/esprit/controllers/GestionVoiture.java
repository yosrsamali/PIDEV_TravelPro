package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceVoiture;

import java.io.IOException;
import java.util.List;

public class GestionVoiture {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnVoirVoitures;

    @FXML
    private TextField tfMarque;

    @FXML
    private TextField tfModele;

    @FXML
    private TextField tfAnnee;

    @FXML
    private TextField tfPrixParJour;

    @FXML
    private TextField tfDisponible;

    @FXML
    private FlowPane flowPaneVoitures;

    private ServiceVoiture serviceVoiture = new ServiceVoiture();

    @FXML
    public void ajouterVoiture() {
        if (tfMarque == null || tfModele == null || tfAnnee == null || tfPrixParJour == null || tfDisponible == null) {
            System.out.println("Erreur : Un ou plusieurs champs ne sont pas initialisés.");
            showAlert("Erreur", "Un ou plusieurs champs ne sont pas initialisés.");
            return;
        }

        if (tfMarque.getText().isEmpty() || tfModele.getText().isEmpty() || tfAnnee.getText().isEmpty() ||
                tfPrixParJour.getText().isEmpty() || tfDisponible.getText().isEmpty()) {
            showAlert("Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        try {
            String marque = tfMarque.getText();
            String modele = tfModele.getText();
            int annee = Integer.parseInt(tfAnnee.getText());
            double prixParJour = Double.parseDouble(tfPrixParJour.getText());
            boolean disponible = tfDisponible.getText().equalsIgnoreCase("Oui");

            Voiture voiture = new Voiture(0, marque, modele, annee, prixParJour, disponible);
            serviceVoiture.add(voiture);

            showAlert("Succès", "Voiture ajoutée avec succès.");

            tfMarque.clear();
            tfModele.clear();
            tfAnnee.clear();
            tfPrixParJour.clear();
            tfDisponible.clear();

        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "L'année et le prix par jour doivent être des nombres valides.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }

    @FXML
    public void afficherVoitures() {
        List<Voiture> voitures = serviceVoiture.getAll();
        flowPaneVoitures.getChildren().clear(); // Effacer les cartes précédentes

        for (Voiture voiture : voitures) {
            HBox card = createVoitureCard(voiture); // Utiliser HBox au lieu de VBox
            flowPaneVoitures.getChildren().add(card); // Ajouter la carte au FlowPane
        }
    }

    private HBox createVoitureCard(Voiture voiture) {
        HBox card = new HBox(10); // Utiliser HBox au lieu de VBox
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");

        // Ajouter les informations de la voiture
        Label lblMarque = new Label("Marque: " + voiture.getMarque());
        lblMarque.setFont(new Font(14));

        Label lblModele = new Label("Modèle: " + voiture.getModele());
        lblModele.setFont(new Font(14));

        Label lblPrix = new Label("Prix/Jour: " + voiture.getPrixParJour() + " €");
        lblPrix.setFont(new Font(14));

        Label lblDisponible = new Label("Disponible: " + (voiture.isDisponible() ? "Oui" : "Non"));
        lblDisponible.setFont(new Font(14));

        // Boutons Modifier et Supprimer
        Button btnModifier = new Button("Modifier");
        btnModifier.setOnAction(event -> modifierVoiture(voiture));

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> supprimerVoiture(voiture));

        // Ajouter les éléments à la carte
        card.getChildren().addAll(lblMarque, lblModele, lblPrix, lblDisponible, btnModifier, btnSupprimer);

        return card;
    }

    private void modifierVoiture(Voiture voiture) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVoiture.fxml"));
            Parent root = loader.load();

            ModifierVoitureController controller = loader.getController();
            controller.setVoiture(voiture);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerVoiture(Voiture voiture) {
        serviceVoiture.delete(voiture);
        afficherVoitures();
        showAlert("Succès", "Voiture supprimée avec succès.");
    }

    @FXML
    public void naviguerVersGestionVoiture() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionVoiture.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnAjouter.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void naviguerVersAffichageVoiture() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageVoiture.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnVoirVoitures.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            GestionVoiture gestionVoitureController = loader.getController();
            gestionVoitureController.afficherVoitures();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}