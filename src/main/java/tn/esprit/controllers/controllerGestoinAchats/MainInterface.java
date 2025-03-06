package tn.esprit.controllers.controllerGestoinAchats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MainInterface {

    @FXML
    private Button btnAdminInterface;
    @FXML
    private Button btnClientInterface;

    @FXML
    public void initialize() {
        btnAdminInterface.setOnAction(event -> switchScene("InterfacesGestionAchat/Admin_Main_Interface.fxml"));
        btnClientInterface.setOnAction(event -> switchScene("InterfacesGestionAchat/Client_Main_Interface.fxml"));
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) btnClientInterface.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
