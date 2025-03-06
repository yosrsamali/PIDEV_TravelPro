package tn.esprit.controllers.controllerGestoinAchats;

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
import tn.esprit.models.Commande;
import tn.esprit.models.CommandeProduit;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.ServiceCommande;
import tn.esprit.services.StripePaymentService;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private Button btnBackToMain;
    @FXML
    private Button btnCheckout;

    private final ServicePanier servicePanier = new ServicePanier();
    private final StripePaymentService paymentService = new StripePaymentService();
    private ObservableList<PanierProduit> cartList = FXCollections.observableArrayList();
    private int panierId; // For example, 1; replace with your dynamic logic if needed

    // Exchange rate: 1 TND = 0.32 USD (adjust if needed)
    private static final double EXCHANGE_RATE_TND_TO_USD = 0.32;

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

        // Setup Modify button column
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
                setGraphic(empty ? null : modifyButton);
            }
        });

        // Setup Delete button column
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    PanierProduit panierProduit = getTableView().getItems().get(getIndex());
                    deleteProductFromCart(panierProduit);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        panierId = 1; // Replace with dynamic value if needed
        loadCart(panierId);

        btnBackToMain.setOnAction(event -> switchScene("Client_Main_Interface.fxml"));
    }

    // Load cart items for the given panier
    private void loadCart(int panierId) {
        cartList.clear();
        cartList.addAll(servicePanier.getPanierProduitsById(panierId));
        tablePanier.setItems(cartList);
        updateTotalPrice();
    }

    // Update total price label
    private void updateTotalPrice() {
        double total = cartList.stream().mapToDouble(p -> p.getQuantite() * p.getPrixVente()).sum();
        labelTotalPrice.setText(total + " TND");
    }

    // Show alert with title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Modify quantity dialog
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
                    loadCart(panierId);
                } else {
                    showAlert("Error", "Quantity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input! Please enter a valid number.");
            }
        });
    }

    // Delete product from cart
    private void deleteProductFromCart(PanierProduit panierProduit) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Suppression");
        confirmation.setHeaderText("Do you really want to remove " + panierProduit.getNomProduit() + " from the cart?");
        confirmation.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            servicePanier.deleteProductFromCart(panierProduit.getIdPanier(), panierProduit.getIdProduit());
            showAlert("Success", "Product removed successfully!");
            loadCart(panierId);
        }
    }

    // Convert TND to USD
    private double convertTndToUsd(double tndAmount) {
        return tndAmount * EXCHANGE_RATE_TND_TO_USD;
    }

    // This method handles the entire checkout process:
    // 1. It creates a Stripe checkout session and opens it.
    // 2. When the user confirms (clicks OK in the confirmation dialog),
    //    it creates a new order (Commande) and its order items (CommandeProduit),
    //    and then clears the cart.
    private void openPaymentInterface() {
        double totalAmountTND = cartList.stream().mapToDouble(p -> p.getQuantite() * p.getPrixVente()).sum();
        double amountInUSD = convertTndToUsd(totalAmountTND);
        String checkoutUrl = paymentService.createCheckoutSession(amountInUSD);
        if (checkoutUrl != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Processing Payment");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Click OK to proceed with payment and confirm your order.");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    Desktop.getDesktop().browse(new URI(checkoutUrl));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to open payment page.");
                    return;
                }
                // After initiating payment, create a new order and add order products.
                createOrder(totalAmountTND);
            }
        } else {
            showAlert("Error", "Payment initiation failed.");
        }
    }

    // Create a new order (Commande) and its associated order items (CommandeProduit),
    // then clear the cart.
    private void createOrder(double totalAmountTND) {
        // Create a new Commande. Replace the client ID with the actual logged-in client ID.
        ServiceCommande serviceCommande = new ServiceCommande();
        Commande commande = new Commande();
        commande.setIdClient(1); // Example client id; replace with actual client id
        commande.setMontantTotal(totalAmountTND);
        commande.setStatus("pending");
        commande.setDateCommande(LocalDateTime.now());

        // Convert each PanierProduit to a CommandeProduit
        List<CommandeProduit> commandeProduits = new ArrayList<>();
        for (PanierProduit pp : cartList) {
            CommandeProduit cp = new CommandeProduit();
            cp.setIdProduit(pp.getIdProduit());
            cp.setQuantite(pp.getQuantite());
            cp.setPrixVente(pp.getPrixVente());
            commandeProduits.add(cp);
        }
        commande.setProduits(commandeProduits);

        // Add the new order (this method also adds each order item to the commande_produit table)
        serviceCommande.add(commande);

        // Clear the cart
        servicePanier.clearCart(panierId);
        loadCart(panierId);
        showAlert("Success", "Your order has been placed successfully!");
    }

    // Switch to another scene
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
