package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
    private ListView<HBox> productListView;

    private ServiceProduit serviceProduit = new ServiceProduit();

    @FXML
    public void initialize() {
        btnGoToAdminMainInterface.setOnAction(event -> switchScene("Admin_Main_Interface.fxml"));
        loadProductList();
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_Main_Interface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnGoToAdminMainInterface.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
            loadProductList();  // Refresh list
            showAlert("Success", "Product added successfully!");

            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid number format.");
        }
    }

    @FXML
    public void supprimerProduit(ActionEvent event) {
        if (productListView.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selection Error", "Please select a product to delete.");
            return;
        }

        HBox selectedBox = productListView.getSelectionModel().getSelectedItem();
        Label productLabel = (Label) selectedBox.getChildren().get(0); // First child should be the product label
        String[] productDetails = productLabel.getText().split(" - ");

        int productId = Integer.parseInt(productDetails[0].split(":")[1].trim());

        Produit produit = new Produit(productId);
        serviceProduit.delete(produit);
        loadProductList();  // Refresh list
        showAlert("Success", "Product deleted successfully!");
    }

    private void loadProductList() {
        ObservableList<HBox> productList = FXCollections.observableArrayList();
        serviceProduit.getAll().forEach(p -> {
            Label productLabel = new Label("ID: " + p.getIdProduit() + " - " + p.getNomProduit() + " - Prix: " + p.getPrixAchat() + " - Quantité: " + p.getQuantiteProduit());

            Button editButton = new Button("Edit");
            editButton.setOnAction(e -> showEditDialog(p)); // Show the edit dialog on button click

            HBox hbox = new HBox(10, productLabel, editButton);
            hbox.setPrefWidth(Region.USE_COMPUTED_SIZE);

            productList.add(hbox);
        });

        productListView.setItems(productList);
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
                    String updatedNom = tfNom.getText();
                    double updatedPrix = Double.parseDouble(tfPrix.getText());
                    int updatedQuantite = Integer.parseInt(tfQuantite.getText());

                    produit.setNomProduit(updatedNom);
                    produit.setPrixAchat(updatedPrix);
                    produit.setQuantiteProduit(updatedQuantite);

                    serviceProduit.update(produit);
                    loadProductList(); // Refresh the list view with updated product info
                    return ButtonType.OK;
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Invalid number format.");
                    return null;
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
