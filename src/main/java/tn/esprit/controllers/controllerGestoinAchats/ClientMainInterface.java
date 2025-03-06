package tn.esprit.controllers.controllerGestoinAchats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class ClientMainInterface {

    public Button btngoback2;
    @FXML
    private Button btnManageCart;

    @FXML
    private Button btnViewStore;

    @FXML
    public void initialize() {
        btnManageCart.setOnAction(event -> switchScene("InterfacesGestionAchat/Client_Manages_Panier_Interface.fxml"));
        btnViewStore.setOnAction(event -> switchScene("InterfacesGestionAchat/Client_Afficher_Boutique_Interface.fxml"));
        btngoback2.setOnAction(event -> switchScene("InterfacesGestionAchat/Main_Interface.fxml"));
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) btnManageCart.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace(System.err); // Explicitly log to the error output stream
        }
    }
}
