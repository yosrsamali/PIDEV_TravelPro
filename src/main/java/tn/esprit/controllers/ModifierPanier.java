package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.PanierProduit;
import tn.esprit.services.ServicePanier;

import java.io.IOException;
import java.util.Optional;

public class ModifierPanier {
    @FXML
    private TableColumn<PanierProduit, Button> colModify;
    @FXML
    private TableColumn<PanierProduit, Button> colDelete;
    @FXML
    private TableView<PanierProduit> tablePanier;
    @FXML
    private TableColumn<PanierProduit, Double> colPrixProduit;
    @FXML
    private TableColumn<PanierProduit, Integer> colQuantite;
    @FXML
    private TableColumn<PanierProduit, Double> colTotal;
    @FXML
    private TableColumn<PanierProduit, String> colNomProduit;
    @FXML
    private Label labelTotalPrice;
    @FXML
    private Button btnBackToMain; // Back button added
    @FXML
    private Button btnCheckout;


    private final ServicePanier servicePanier = new ServicePanier();
    private ObservableList<PanierProduit> cartList = FXCollections.observableArrayList();
    private int panierId; // Variable to store the id_panier

    // Initialize method - Load data for a specific panier
    @FXML
    public void initialize() {
        btnCheckout.setOnAction(event -> openPaymentInterface());
        colNomProduit.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
        colPrixProduit.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colTotal.setCellValueFactory(cellData -> {
            PanierProduit item = cellData.getValue();
            return new javafx.beans.property.SimpleDoubleProperty(item.getQuantite() * item.getPrixVente()).asObject();
        });

        // Add Modify button
        colModify.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");

            {
                modifyButton.setOnAction(event -> {
                    PanierProduit panierProduit = getTableView().getItems().get(getIndex());
                    showModifyDialog(panierProduit);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                }
            }
        });

        // Add Delete button
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    PanierProduit panierProduit = getTableView().getItems().get(getIndex());
                    deleteProductFromCart(panierProduit); // Call the delete function that refreshes the table
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Load cart items
        panierId = 1;  // Replace with dynamic value if needed
        loadCart(panierId);

        // Back button action
        btnBackToMain.setOnAction(event -> switchScene("Client_Main_Interface.fxml"));
    }

    // Load cart items for a specific panier
    private void loadCart(int panierId) {
        cartList.clear();
        cartList.addAll(servicePanier.getPanierProduitsById(panierId)); // Fetch only items for specific panier
        tablePanier.setItems(cartList);
        updateTotalPrice();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showModifyDialog(PanierProduit panierProduit) {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(panierProduit.getQuantite()));
        dialog.setTitle("Modify Quantity");
        dialog.setHeaderText("Modify quantity for " + panierProduit.getNomProduit());
        dialog.setContentText("Enter new quantity:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newQuantity -> {
            try {
                int newQty = Integer.parseInt(newQuantity);
                if (newQty > 0) {
                    servicePanier.updateProductQuantity(panierProduit.getIdPanier(), panierProduit.getIdProduit(), newQty);
                    loadCart(panierId); // Refresh table after modifying
                } else {
                    showAlert("Quantity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input! Please enter a valid number.");
            }
        });
    }

    private void deleteProductFromCart(PanierProduit panierProduit) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Suppression");
        confirmation.setHeaderText("Voulez-vous vraiment supprimer " + panierProduit.getNomProduit() + " du panier ?");
        confirmation.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            servicePanier.deleteProductFromCart(panierProduit.getIdPanier(), panierProduit.getIdProduit());

            // Show success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Suppression avec succès !");
            successAlert.showAndWait();

            // Refresh the cart
            loadCart(panierId);
        }
    }

    private void updateTotalPrice() {
        double total = cartList.stream().mapToDouble(p -> p.getQuantite() * p.getPrixVente()).sum();
        labelTotalPrice.setText(total + " TND");
    }
    private void openPaymentInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentInterface.fxml"));
            Parent root = loader.load();

            PaymentController controller = loader.getController();
            controller.setTotalAmount(cartList.stream().mapToDouble(p -> p.getQuantite() * p.getPrixVente()).sum());

            Stage stage = new Stage();
            stage.setTitle("Payment");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Scene switching method
    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) btnBackToMain.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}