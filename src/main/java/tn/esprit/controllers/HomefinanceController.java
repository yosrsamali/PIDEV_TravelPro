package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.Collections;

public class HomefinanceController {

    @FXML
    private AnchorPane mainContent;




    @FXML
    private void showAddDepense() {
        loadView("depense.fxml");
    }

    @FXML
    private void showListDepense() {
        loadView("listdepense.fxml");
    }

    @FXML
    private void showAddRevenue() {
        loadView("revenue.fxml");
    }

    @FXML
    private void showListRevenue() {
        loadView("listrevenue.fxml");
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
