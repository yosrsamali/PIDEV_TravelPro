package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceProduit;
import tn.esprit.interfaces.IService;

public class DeleteProduit {

    @FXML
    private ListView<String> productListView; // List of products
    @FXML
    private Button btnDelete; // Delete button

    private IService<Produit> sp = new ServiceProduit();

    @FXML
    public void initialize() {
        // Load product names into ListView
        loadProducts();

        // Handle delete button click
        btnDelete.setOnAction(event -> deleteSelectedProduct());
    }

    private void loadProducts() {
        productListView.getItems().clear();
        for (Produit produit : sp.getAll()) {
            productListView.getItems().add(produit.getIdProduit() + " - " + produit.getNomProduit()); // Show ID + Name
        }
    }

    private void deleteSelectedProduct() {
        String selectedItem = productListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Selection Error", "Please select a product to delete.");
            return;
        }

        // Extract product ID from selected item
        int productId = Integer.parseInt(selectedItem.split(" - ")[0]);

        // Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this product?");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                // Delete the product
                sp.delete(new Produit(productId));
                showAlert("Success", "Product deleted successfully!");

                // Refresh product list
                loadProducts();
            }
        });
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
