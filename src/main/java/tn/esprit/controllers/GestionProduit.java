package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceProduit;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class GestionProduit {

    @FXML
    private TextField tfNomProduit;
    @FXML
    private TextField tfPrixProduit;
    @FXML
    private TextField tfQuantiteProduit;

    IService<Produit> sp = new ServiceProduit();
    @FXML
    private Label lbProduits;



    // Initialize method where we add listeners to the TextFields

    @FXML
    private VBox leftVBox;  // Add fx:id="leftVBox" to the VBox in FXML
    @FXML
    private VBox rightVBox; // Add fx:id="rightVBox" to the VBox in FXML




    @FXML
    public void initialize() {
        // Set HGrow for the right VBox to make it take the remaining space
        HBox.setHgrow(rightVBox, Priority.ALWAYS);
        // Listener for tfPrixProduit to allow only numbers and one decimal point
        tfPrixProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                tfPrixProduit.setText(oldValue); // Only allow numbers and a single decimal point
            }
        });

        // Listener for tfQuantiteProduit to allow only whole numbers
        tfQuantiteProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfQuantiteProduit.setText(oldValue); // Only allow whole numbers
            }
        });
    }

    @FXML
    public void ajouterProduit(ActionEvent actionEvent) {
        // Validate inputs before parsing
        String nom = tfNomProduit.getText();
        String prixText = tfPrixProduit.getText();
        String quantiteText = tfQuantiteProduit.getText();

        if (nom.isEmpty() || prixText.isEmpty() || quantiteText.isEmpty()) {
            lbProduits.setText("Please fill in all fields.");
            return;
        }

        // Validate that price and quantity are numeric
        double prix = 0;
        int quantite = 0;

        try {
            prix = Double.parseDouble(prixText);
            quantite = Integer.parseInt(quantiteText);
        } catch (NumberFormatException e) {
            lbProduits.setText("Invalid price or quantity. Please enter numbers.");
            return;
        }

        // Create the product and add to service
        Produit p = new Produit();
        p.setNomProduit(nom);
        p.setPrixProduit(prix);
        p.setQuantiteProduit(quantite);

        sp.add(p);
        lbProduits.setText("Product added successfully!");

        // Clear input fields after adding
        tfNomProduit.clear();
        tfPrixProduit.clear();
        tfQuantiteProduit.clear();
    }

    @FXML
    public void afficherProduits(ActionEvent actionEvent) {
        // Assuming sp.getAll() returns a list of products
        StringBuilder productsText = new StringBuilder();

        // Loop through all products and append each one with a newline
        for (Produit produit : sp.getAll()) {
            productsText.append(produit.toString()).append("\n");
        }

        // Set the label text with the formatted string
        if (productsText.length() == 0) {
            lbProduits.setText("No products available.");
        } else {
            lbProduits.setText(productsText.toString());
        }
    }
}
