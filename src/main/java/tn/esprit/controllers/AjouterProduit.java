package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceProduit;

public class AjouterProduit {
    @FXML
    private ListView<String> productListView;
    @FXML
    private TextField tfPrixProduit; // Price input field
    @FXML
    private TextField tfQuantieProduit; // Quantity input field
    @FXML
    private TextField tfNomProduit; // Name input field
    @FXML
    private Label lbPersonnes; // Label to display products

    private IService<Produit> sp = new ServiceProduit();

    @FXML
    public void initialize() {
        // Validate price field (only numbers and up to 2 decimals)
        tfPrixProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) { // Accepts decimals with max 2 places
                tfPrixProduit.setText(oldValue);
            }
        });

        // Validate quantity field (only whole numbers)
        tfQuantieProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Accepts only digits
                tfQuantieProduit.setText(oldValue);
            }
        });

        // Validate name field (only letters and spaces)
        tfNomProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) { // Accepts only letters and spaces
                tfNomProduit.setText(oldValue);
            }
        });
    }

    @FXML
    public void AjouterProduit(ActionEvent actionEvent) {
        // Check if any field is empty
        if (tfNomProduit.getText().trim().isEmpty() ||
                tfQuantieProduit.getText().trim().isEmpty() ||
                tfPrixProduit.getText().trim().isEmpty()) {

            showAlert("Input Error", "All fields must be filled!");
            return;
        }

        try {
            // Create new product
            Produit p = new Produit();
            p.setNomProduit(tfNomProduit.getText());
            p.setQuantiteProduit(Integer.parseInt(tfQuantieProduit.getText()));
            p.setPrixAchat(Double.parseDouble(tfPrixProduit.getText()));

            // Add product to database
            sp.add(p);
            showAlert("Success", "Product added successfully!");

            // Clear input fields after adding
            tfNomProduit.clear();
            tfQuantieProduit.clear();
            tfPrixProduit.clear();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid number format!");
        }
    }

    @FXML
    public void AfficherProduit(ActionEvent actionEvent) {
        lbPersonnes.setText(sp.getAll().toString());
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
