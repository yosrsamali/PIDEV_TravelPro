package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Revenue;
import tn.esprit.services.RevenueService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class RevenueController {

    @FXML
    private ComboBox<String> cbTypeRevenue;  // ComboBox pour sélectionner le type de revenue

    @FXML
    private TextField tfReferenceId;         // TextField pour entrer l'ID de référence

    @FXML
    private TextField tfMontantTotal;        // TextField pour entrer le montant total

    @FXML
    private TextField tfCommission;          // TextField pour entrer la commission

    @FXML
    private DatePicker dpDateRevenue;        // DatePicker pour sélectionner la date de revenue

    private RevenueService revenueService;   // Service pour gérer les revenues

    public RevenueController() {
        revenueService = new RevenueService();
    }

    @FXML
    public void ajouterRevenue() {
        // Récupération des données saisies par l'utilisateur
        String typeRevenue = cbTypeRevenue.getValue();
        String referenceId = tfReferenceId.getText();
        double montantTotal = Double.parseDouble(tfMontantTotal.getText());
        double commission = Double.parseDouble(tfCommission.getText());
        LocalDate dateRevenue = dpDateRevenue.getValue();

        // Vérification si les champs sont valides
        if (typeRevenue == null || referenceId.isEmpty() || montantTotal <= 0 || commission < 0 || dateRevenue == null) {
            System.out.println("⚠️ Veuillez remplir tous les champs correctement !");
            return;
        }

        // Création d'une instance Revenue avec les données récupérées
        Revenue revenue = new Revenue(0, typeRevenue, referenceId, dateRevenue, montantTotal, commission);

        // Ajout de la revenue via le service
        revenueService.add(revenue);

        // Alerte pour confirmer l'ajout du revenue
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Revenue ajoutée avec succès!");

        // Lorsque l'utilisateur clique sur "OK"
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Rediriger vers la page ListRevenue
                redirigerVersListRevenue();
            }
        });

        // Réinitialisation des champs après l'ajout
        resetFields();
    }

    private void redirigerVersListRevenue() {
        try {
            // Charger le fichier FXML de ListRevenue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listRevenue.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène pour ListRevenue
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) cbTypeRevenue.getScene().getWindow(); // Utilise la fenêtre actuelle
            currentStage.setScene(scene);
            currentStage.setTitle("Liste des Revenus");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        cbTypeRevenue.getSelectionModel().clearSelection();
        tfReferenceId.clear();
        tfMontantTotal.clear();
        tfCommission.clear();
        dpDateRevenue.setValue(null);
    }
}
