package tn.esprit.Controllers.controllers_reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceVoiture;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
    private DatePicker dpDateDeLocation;

    @FXML
    private DatePicker dpDateDeRemise;

    @FXML
    private FlowPane flowPaneVoitures;

    private ServiceVoiture serviceVoiture = new ServiceVoiture();

    // Validation des chaînes de caractères
    private boolean validerChaine(String chaine) {
        String regex = "^[a-zA-Z0-9\\s]+$";
        return chaine.matches(regex);
    }

    // Validation des dates
    private boolean validerDates(LocalDate dateLocation, LocalDate dateRemise) {
        LocalDate aujourdHui = LocalDate.now();

        if (dateLocation.isBefore(aujourdHui)) {
            showAlert("Erreur de saisie", "La date de location doit être supérieure à la date actuelle.");
            return false;
        }

        if (dateRemise.isBefore(dateLocation)) {
            showAlert("Erreur de saisie", "La date de remise doit être supérieure à la date de location.");
            return false;
        }

        return true;
    }

    @FXML
    public void ajouterVoiture() {
        if (tfMarque == null || tfModele == null || tfAnnee == null || tfPrixParJour == null || tfDisponible == null ||
                dpDateDeLocation == null || dpDateDeRemise == null) {
            System.out.println("Erreur : Un ou plusieurs champs ne sont pas initialisés.");
            showAlert("Erreur", "Un ou plusieurs champs ne sont pas initialisés.");
            return;
        }

        if (tfMarque.getText().isEmpty() || tfModele.getText().isEmpty() || tfAnnee.getText().isEmpty() ||
                tfPrixParJour.getText().isEmpty() || tfDisponible.getText().isEmpty() ||
                dpDateDeLocation.getValue() == null || dpDateDeRemise.getValue() == null) {
            showAlert("Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        if (!validerChaine(tfMarque.getText())) {
            showAlert("Erreur de saisie", "La marque ne doit pas contenir de caractères spéciaux.");
            return;
        }

        if (!validerChaine(tfModele.getText())) {
            showAlert("Erreur de saisie", "Le modèle ne doit pas contenir de caractères spéciaux.");
            return;
        }

        try {
            int annee = Integer.parseInt(tfAnnee.getText());
            double prixParJour = Double.parseDouble(tfPrixParJour.getText());

            if (annee <= 1900 || 2025 <= annee) {
                showAlert("Erreur de saisie", "L'année doit être entre 1900 et 2025.");
                return;
            }

            if (prixParJour <= 0) {
                showAlert("Erreur de saisie", "Le prix par jour doit être un nombre positif.");
                return;
            }

            LocalDate dateLocation = dpDateDeLocation.getValue();
            LocalDate dateRemise = dpDateDeRemise.getValue();

            if (!validerDates(dateLocation, dateRemise)) {
                return;
            }

            boolean disponible = tfDisponible.getText().equalsIgnoreCase("Oui");
            Date dateDeLocation = Date.valueOf(dateLocation);
            Date dateDeRemise = Date.valueOf(dateRemise);

            Voiture voiture = new Voiture(0, tfMarque.getText(), tfModele.getText(), annee, prixParJour, disponible, dateDeLocation, dateDeRemise);
            serviceVoiture.add(voiture);

            showAlert("Succès", "Voiture ajoutée avec succès.");

            // Réinitialiser les champs
            tfMarque.clear();
            tfModele.clear();
            tfAnnee.clear();
            tfPrixParJour.clear();
            tfDisponible.clear();
            dpDateDeLocation.setValue(null);
            dpDateDeRemise.setValue(null);

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
            VBox card = createVoitureCard(voiture);
            flowPaneVoitures.getChildren().add(card); // Ajouter la carte au FlowPane
        }
    }

    private VBox createVoitureCard(Voiture voiture) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #f4f4f4;");

        // Ajouter les informations de la voiture
        Label lblMarque = new Label("Marque: " + voiture.getMarque());
        lblMarque.setFont(new Font(14));

        Label lblModele = new Label("Modèle: " + voiture.getModele());
        lblModele.setFont(new Font(14));

        Label lbAnnee = new Label("Annee: " + voiture.getAnnee());
        lbAnnee.setFont(new Font(14));

        Label lblPrix = new Label("Prix/Jour: " + voiture.getPrixParJour() + " €");
        lblPrix.setFont(new Font(14));

        Label lblDisponible = new Label("Disponible: " + (voiture.isDisponible() ? "Oui" : "Non"));
        lblDisponible.setFont(new Font(14));

        Label lblDateLocation = new Label("Date de location: " + voiture.getDateDeLocation());
        lblDateLocation.setFont(new Font(14));

        Label lblDateRemise = new Label("Date de remise: " + voiture.getDateDeRemise());
        lblDateRemise.setFont(new Font(14));

        // Boutons Modifier et Supprimer
        Button btnModifier = new Button("Modifier");
        btnModifier.setOnAction(event -> modifierVoiture(voiture));

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> supprimerVoiture(voiture));

        // Ajouter les éléments à la carte
        card.getChildren().addAll(lblMarque, lblModele,lbAnnee, lblPrix, lblDisponible, lblDateLocation, lblDateRemise, btnModifier, btnSupprimer);

        return card;
    }

    private void modifierVoiture(Voiture voiture) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/ModifierVoiture.fxml"));
            Parent root = loader.load();

            ModifierVoitureController controller = loader.getController();
            controller.setVoiture(voiture);

            // Ajouter un écouteur pour actualiser la liste après la modification
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(event -> afficherVoitures()); // Actualisation automatique
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/GestionVoiture.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views_reservation/AffichageVoiture.fxml"));
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