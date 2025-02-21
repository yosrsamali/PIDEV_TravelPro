package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Client;
import tn.esprit.models.PanierProduit;
import tn.esprit.services.ServicePanier;

public class AfficherPanier {
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

    private final ServicePanier servicePanier = new ServicePanier();
    private ObservableList<PanierProduit> cartList = FXCollections.observableArrayList();

    private int panierId; // Variable to store the id_panier

    // Initialize method - Load data for a specific panier
    @FXML
    public void initialize() {
        // Set up the table columns
        colNomProduit.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
        colPrixProduit.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colTotal.setCellValueFactory(cellData -> {
            PanierProduit item = cellData.getValue();
            return new javafx.beans.property.SimpleDoubleProperty(item.getQuantite() * item.getPrixVente()).asObject();
        });

        // Example: Set the panierId (this could be set dynamically depending on your logic)
        panierId = 1;  // For demonstration, replace with dynamic value

        // Load cart data based on specific panier ID
        loadCart(panierId);
    }

    // Load cart items for a specific panier
    private void loadCart(int panierId) {
        cartList.clear();
        cartList.addAll(servicePanier.getPanierProduitsById(panierId)); // Fetch only items for specific panier
        tablePanier.setItems(cartList);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = cartList.stream().mapToDouble(p -> p.getQuantite() * p.getPrixVente()).sum();
        labelTotalPrice.setText(total + " TND");
    }
}
