package tn.esprit.controllers.controllerfinance;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomefinanceController {

    @FXML
    private AnchorPane mainContent;




    @FXML
    private void showAddDepense() {
        loadView("viewfinance/depense.fxml");
    }

    @FXML
    private void showListDepense() {
        loadView("viewfinance/listdepense.fxml");
    }

    @FXML
    private void showAddRevenue() {
        loadView("viewfinance/revenue.fxml");
    }

    @FXML
    private void showListRevenue() {
        loadView("viewfinance/listrevenue.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            System.out.println("Chargement de : " + fxmlFile);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));

            if (loader.getLocation() == null) {
                System.out.println("❌ Erreur : Fichier FXML '" + fxmlFile + "' introuvable !");
                return;
            }

            AnchorPane view = loader.load();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(view);
            System.out.println("✅ " + fxmlFile + " chargé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
