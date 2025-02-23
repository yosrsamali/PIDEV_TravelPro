package tn.esprit.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceProduit;

import java.io.File;
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
    private Button btnSelectImage;
    @FXML
    private Label lblImagePath;
    @FXML
    private FlowPane productCardContainer;

    private ServiceProduit serviceProduit = new ServiceProduit();
    private String selectedImagePath = null;

    @FXML
    public void initialize() {
        btnGoToAdminMainInterface.setOnAction(event -> switchScene("Admin_Main_Interface.fxml"));
        loadProductCards();

        // Ensure only numbers (with decimals) for Prix Achat
        tfPrixAchat.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                tfPrixAchat.setText(oldValue);
            }
        });

        // Ensure only whole numbers for Quantité Produit
        tfQuantiteProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfQuantiteProduit.setText(oldValue);
            }
        });
        // Ensure only letters, numbers, and spaces (max length: 50) for Nom Produit
        tfNomProduit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9\\s]*") || newValue.length() > 50) {
                tfNomProduit.setText(oldValue);
            }
        });
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
            produit.setImagePath(selectedImagePath);

            serviceProduit.add(produit);
            loadProductCards();
            showAlert("Success", "Product added successfully!");

            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid number format.");
        }
    }

    @FXML
    public void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(btnSelectImage.getScene().getWindow());

        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            lblImagePath.setText(selectedImagePath);
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

        // Image display
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150); // Set image width
        imageView.setFitHeight(150); // Set image height

        if (produit.getImagePath() != null && !produit.getImagePath().isEmpty()) {
            try {
                imageView.setImage(new Image(new File(produit.getImagePath()).toURI().toString()));
            } catch (Exception e) {
                imageView.setImage(null);
                System.err.println("Error loading image: " + e.getMessage());
            }
        } else {
            imageView.setImage(new Image(getClass().getResource("/images/placeholder.png").toString())); // Fallback placeholder
        }

        // Edit & Delete buttons
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> showEditDialog(produit));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> supprimerProduit(produit));

        HBox buttonBox = new HBox(10, editButton, deleteButton);

        // Card layout
        VBox card = new VBox(10, imageView, nameLabel, priceLabel, quantityLabel, buttonBox);
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-padding: 10; -fx-background-radius: 10; -fx-background-color: #fff;");

        return card;
    }


    private void clearFields() {
        tfNomProduit.clear();
        tfPrixAchat.clear();
        tfQuantiteProduit.clear();
        lblImagePath.setText("No image selected");
        selectedImagePath = null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showEditDialog(Produit produit) {
        // Create TextFields pre-filled with the product's details
        TextField tfNom = new TextField(produit.getNomProduit());
        TextField tfPrix = new TextField(String.valueOf(produit.getPrixAchat()));
        TextField tfQuantite = new TextField(String.valueOf(produit.getQuantiteProduit()));

        // Image selection button and label
        Label lblImagePath = new Label(produit.getImagePath() != null ? produit.getImagePath() : "No image selected");
        Button btnSelectImage = new Button("Select Image");

        btnSelectImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(btnSelectImage.getScene().getWindow());
            if (selectedFile != null) {
                lblImagePath.setText(selectedFile.getAbsolutePath());
            }
        });

        // Input Validation (for edit fields)
        tfPrix.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                tfPrix.setText(oldValue);
            }
        });
        tfQuantite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfQuantite.setText(oldValue);
            }
        });
        tfNom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*") || newValue.length() > 50) {
                tfNom.setText(oldValue);
            }
        });

        // Create the edit dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Product");
        dialog.getDialogPane().setContent(new VBox(10,
                new Label("Nom Produit:"), tfNom,
                new Label("Prix Achat:"), tfPrix,
                new Label("Quantite Produit:"), tfQuantite,
                new Label("Image Path:"), lblImagePath, btnSelectImage));

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        // Handle dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                try {
                    produit.setNomProduit(tfNom.getText());
                    produit.setPrixAchat(Double.parseDouble(tfPrix.getText()));
                    produit.setQuantiteProduit(Integer.parseInt(tfQuantite.getText()));
                    produit.setImagePath(lblImagePath.getText());

                    // Update the product in the database
                    serviceProduit.update(produit);
                    loadProductCards();
                    showAlert("Success", "Product updated successfully!");
                    return ButtonType.OK;
                } catch (NumberFormatException ex) {
                    showAlert("Input Error", "Invalid number format.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


}

