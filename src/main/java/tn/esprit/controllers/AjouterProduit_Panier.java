package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Produit;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.ServiceProduit;
import tn.esprit.interfaces.IService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AjouterProduit_Panier {

    @FXML
    private TableView<Produit> productTableView;
    @FXML
    private TableColumn<Produit, String> colNomProduit;
    @FXML
    private TableColumn<Produit, Double> colPrixAchat;
    @FXML
    private TableColumn<Produit, Integer> colQuantite;
    @FXML
    private TableColumn<Produit, Void> colAction;
    @FXML
    private Button btnBack; // Back button

    private IService<Produit> sp = new ServiceProduit();
    private ServicePanier servicePanier = new ServicePanier();

    private final Map<Integer, Spinner<Integer>> spinnerMap = new HashMap<>();

    @FXML
    public void initialize() {
        colNomProduit.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
        colPrixAchat.setCellValueFactory(new PropertyValueFactory<>("prixVente"));

        loadProducts();

        colQuantite.setCellFactory(param -> new TableCell<>() {
            private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Produit produit = getTableView().getItems().get(getIndex());
                    spinnerMap.put(produit.getIdProduit(), spinner);
                    setGraphic(spinner);
                }
            }
        });

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnAdd = new Button("Add");

            {
                btnAdd.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    Spinner<Integer> spinner = spinnerMap.get(produit.getIdProduit());

                    if (spinner != null) {
                        int quantity = spinner.getValue();
                        addToCart(produit, quantity);
                    } else {
                        showAlert("Error", "Failed to retrieve quantity.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(btnAdd));
                }
            }
        });

        btnBack.setOnAction(event -> goBackToMain()); // Set action for Back button
    }

    private void loadProducts() {
        ObservableList<Produit> produits = FXCollections.observableArrayList(sp.getAll());

        colPrixAchat.setText("Price");
        colPrixAchat.setCellFactory(tc -> new TableCell<Produit, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f TND", price));
                }
            }
        });

        productTableView.setItems(produits);
    }

    private void addToCart(Produit produit, int quantity) {
        servicePanier.ajouterAuPanier(1, produit.getIdProduit(), quantity);
        showAlert("Success", produit.getNomProduit() + " added to cart!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void goBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client_Main_Interface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Error", "Failed to load main interface.");
            e.printStackTrace();
        }
    }
}
