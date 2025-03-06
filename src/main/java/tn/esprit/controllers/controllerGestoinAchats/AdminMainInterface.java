package tn.esprit.controllers.controllerGestoinAchats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class AdminMainInterface {

    @FXML
    private Button btnManageProducts;

    @FXML
    private Button btnViewOrders;
    @FXML
    private Button btngoback;


    @FXML
    public void initialize() {
        btnManageProducts.setOnAction(event -> switchScene("InterfacesGestionAchat/Admin_Manages_Produit_Interface.fxml"));
        btnViewOrders.setOnAction(event -> switchScene("InterfacesGestionAchat/Admin_Afficher_Commande_Interface.fxml"));
        btngoback.setOnAction(event -> switchScene("InterfacesGestionAchat/Main_Interface.fxml"));
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
