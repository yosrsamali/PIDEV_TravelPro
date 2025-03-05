package tn.esprit.controllers;

import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import tn.esprit.utils.MyDatabase;
import tn.esprit.models.Revenue;

import java.net.URL;
import java.sql.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListRevenueController implements Initializable {

    @FXML
    private VBox vboxContainer; // Conteneur pour les cartes

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerRevenues();
    }

    private void chargerRevenues() {
        String query = "SELECT * FROM revenue";

        try (Connection cnx = MyDatabase.getInstance().getCnx();
             Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(query)) {

            while (rs.next()) {
                int idRevenue = rs.getInt("id_revenue");
                String typeRevenue = rs.getString("type_revenue");
                Date dateRevenue = rs.getDate("date_revenue");
                double montantTotal = rs.getDouble("montant_total");
                double commission = rs.getDouble("commission");

                // Calcul du bénéfice
                double benefice = (montantTotal * commission) / 100;

                // Création et ajout de la carte dans le conteneur VBox
                vboxContainer.getChildren().add(creerCarteRevenue(idRevenue, typeRevenue, dateRevenue.toLocalDate(), montantTotal, commission, benefice));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BorderPane creerCarteRevenue(int id, String typeRevenue, LocalDate dateRevenue, double montantTotal, double commission, double benefice) {
        // Conteneur principal de la carte
        BorderPane card = new BorderPane();
        card.getStyleClass().add("card");  // Style de la carte depuis CSS

        // Titre (Type de Revenue)
        Label lblTypeRevenue = new Label(typeRevenue);
        lblTypeRevenue.setFont(new Font("Arial", 18));
        lblTypeRevenue.getStyleClass().add("card-title");

        card.setTop(lblTypeRevenue);

        // Contenu principal de la carte
        HBox content = new HBox(20);
        content.getStyleClass().add("card-content");

        // Ajout des labels pour chaque information

        Label lblDateRevenue = new Label("Date : " + dateRevenue);
        lblDateRevenue.getStyleClass().add("card-text");

        Label lblMontantTotal = new Label("Montant Total : " + montantTotal + " TND");
        lblMontantTotal.getStyleClass().add("card-text");

        Label lblCommission = new Label("Commission : " + commission + " %");
        lblCommission.getStyleClass().add("card-text");

        // Ajout du label pour le bénéfice
        Label lblBenefice = new Label("Bénéfice : " + String.format("%.2f", benefice) + " TND");
        lblBenefice.getStyleClass().add("card-benefice");

        // Ajout des labels au HBox
        content.getChildren().addAll( lblDateRevenue, lblMontantTotal, lblCommission, lblBenefice);

        // Affectation du contenu à la carte
        card.setCenter(content);

        return card;
    }


}
