package tn.esprit.controllers.controllerGestoinAchats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class AdminMainInterface {

    @FXML
    private Button btnManageProducts;

    @FXML
    private Button btnViewOrders;
    @FXML
    private Button btngoback;


    @FXML
    public void initialize() {
        btnManageProducts.setOnAction(event -> switchScene("Admin_Manages_Produit_Interface.fxml"));
        btnViewOrders.setOnAction(event -> switchScene("Admin_Afficher_Commande_Interface.fxml"));
        btngoback.setOnAction(event -> switchScene("Main_Interface.fxml"));
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) btnManageProducts.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace(System.err); // Explicitly log to the error output stream
        }
    }

}
