package tn.esprit.controllers.controllerGestoinAchats;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tn.esprit.models.Produit;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.ServiceProduit;
import tn.esprit.interfaces.IService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AjouterProduit_Panier {

    @FXML
    private GridPane productGridPane;

    @FXML
    private Button btnBack;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnClear;

    private IService<Produit> sp = new ServiceProduit();
    private ServicePanier servicePanier = new ServicePanier();

    private final Map<Integer, Spinner<Integer>> spinnerMap = new HashMap<>();

    @FXML
    public void initialize() {
        loadProducts(sp.getAll());
        btnBack.setOnAction(event -> goBackToMain());
        btnClear.setOnAction(event -> clearSearch());

        // Add listener for real-time search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterProducts(newValue));
    }

    private void loadProducts(List<Produit> produits) {
        productGridPane.getChildren().clear(); // Clear previous items
        int col = 0;
        int row = 0;

        for (Produit produit : produits) {
            VBox card = createProductCard(produit);

            productGridPane.add(card, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createProductCard(Produit produit) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10; -fx-alignment: center; -fx-border-color: #3fbfee; -fx-border-width: 2;");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        File file = new File(produit.getImagePath());
        if (file.exists()) {
            imageView.setImage(new Image(file.toURI().toString()));
        } else {
            imageView.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
        }

        Label nameLabel = new Label(produit.getNomProduit());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label priceLabel = new Label(String.format("%.2f TND", produit.getPrixVente()));

        Spinner<Integer> spinner = new Spinner<>(1, produit.getQuantiteProduit(), 1);
        spinnerMap.put(produit.getIdProduit(), spinner);

        Button btnAdd = new Button("Add to Cart");
        btnAdd.setStyle("-fx-background-color: #3ab3e7; -fx-text-fill: white;");
        btnAdd.setOnAction(event -> addToCart(produit, spinner.getValue()));

        card.getChildren().addAll(imageView, nameLabel, priceLabel, spinner, btnAdd);
        return card;
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

    private void clearSearch() {
        searchField.clear();
    }

    private void filterProducts(String query) {
        List<Produit> filteredProducts = sp.getAll().stream()
                .filter(p -> p.getNomProduit().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        loadProducts(filteredProducts);
    }
}