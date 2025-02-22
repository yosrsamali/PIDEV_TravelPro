package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceProduit;

import java.io.IOException;

public class AdminManagesProduit {

    public Button btnAjouter;
    public Button btnSupprimer;
    @FXML
    private Button btnGoToAdminMainInterface;
    @FXML
    private TextField tfNomProduit;
    @FXML
    private TextField tfPrixAchat;
    @FXML
    private TextField tfQuantiteProduit;
    @FXML
    private FlowPane productCardContainer;

    private ServiceProduit serviceProduit = new ServiceProduit();

    @FXML
    public void initialize() {
        btnGoToAdminMainInterface.setOnAction(event -> switchScene("Admin_Main_Interface.fxml"));
        loadProductCards();
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) btnGoToAdminMainInterface.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        switchScene("Admin_Main_Interface.fxml");
    }
    @FXML
    private void validatePrixAchat() {
        tfPrixAchat.setText(tfPrixAchat.getText().replaceAll("[^\\d.]", ""));
        if (!tfPrixAchat.getText().matches("\\d*\\.?\\d*")) {
            tfPrixAchat.setText("");
        }
    }

    @FXML
    private void validateQuantiteProduit() {
        tfQuantiteProduit.setText(tfQuantiteProduit.getText().replaceAll("[^\\d]", ""));
        if (!tfQuantiteProduit.getText().matches("\\d*")) {
            tfQuantiteProduit.setText("");
        }
    }


    @FXML
    public void ajouterProduit(ActionEvent event) {
        if (tfNomProduit.getText().isEmpty() || tfPrixAchat.getText().isEmpty() || tfQuantiteProduit.getText().isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }

        try {
            String nomProduit = tfNomProduit.getText();
            double prixAchat = Double.parseDouble(tfPrixAchat.getText());
            int quantiteProduit = Integer.parseInt(tfQuantiteProduit.getText());

            Produit produit = new Produit();
            produit.setNomProduit(nomProduit);
            produit.setPrixAchat(prixAchat);
            produit.setQuantiteProduit(quantiteProduit);

            serviceProduit.add(produit);
            loadProductCards();
            showAlert("Success", "Product added successfully!");

            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid number format.");
        }
    }

    @FXML
    public void supprimerProduit(Produit produit) {
        serviceProduit.delete(produit);
        loadProductCards();
        showAlert("Success", "Product deleted successfully!");
    }

    private void loadProductCards() {
        productCardContainer.getChildren().clear();

        serviceProduit.getAll().forEach(p -> {
            VBox card = createProductCard(p);
            productCardContainer.getChildren().add(card);
        });
    }

    private VBox createProductCard(Produit produit) {
        Label nameLabel = new Label(produit.getNomProduit());
        Label priceLabel = new Label("Prix: " + produit.getPrixAchat());
        Label quantityLabel = new Label("Quantité: " + produit.getQuantiteProduit());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> showEditDialog(produit));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> supprimerProduit(produit));

        HBox buttonBox = new HBox(10, editButton, deleteButton);

        VBox card = new VBox(10, nameLabel, priceLabel, quantityLabel, buttonBox);
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-padding: 10; -fx-background-radius: 10; -fx-background-color: #fff;");

        return card;
    }

    private void showEditDialog(Produit produit) {
        TextField tfNom = new TextField(produit.getNomProduit());
        TextField tfPrix = new TextField(String.valueOf(produit.getPrixAchat()));
        TextField tfQuantite = new TextField(String.valueOf(produit.getQuantiteProduit()));

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Product");

        dialog.getDialogPane().setContent(new VBox(10, new Label("Nom Produit:"), tfNom, new Label("Prix Achat:"), tfPrix, new Label("Quantite Produit:"), tfQuantite));

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                try {
                    produit.setNomProduit(tfNom.getText());
                    produit.setPrixAchat(Double.parseDouble(tfPrix.getText()));
                    produit.setQuantiteProduit(Integer.parseInt(tfQuantite.getText()));

                    serviceProduit.update(produit);
                    loadProductCards();
                    return ButtonType.OK;
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Invalid number format.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void clearFields() {
        tfNomProduit.clear();
        tfPrixAchat.clear();
        tfQuantiteProduit.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}