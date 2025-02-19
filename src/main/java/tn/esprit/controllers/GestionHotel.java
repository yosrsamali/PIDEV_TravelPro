package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

public class GestionHotel {
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfVille;
    @FXML
    private TextField tfPrixParNuit;
    @FXML
    private TextField tfDisponible;
    @FXML
    private TextField tfNombreEtoile;
    @FXML
    private TextField tfTypeDeChambre;
    @FXML
    private Label lbHotels;

    IService<Hotel> sh = new ServiceHotel();

    @FXML
    public void ajouterHotel(ActionEvent actionEvent) {
        Hotel h = new Hotel(
                tfNom.getText(),
                tfVille.getText(),
                Double.parseDouble(tfPrixParNuit.getText()),
                Boolean.parseBoolean(tfDisponible.getText()),
                Integer.parseInt(tfNombreEtoile.getText()),
                tfTypeDeChambre.getText()
        );

        sh.add(h);
    }

    @FXML
    public void afficherHotels(ActionEvent actionEvent) {
        lbHotels.setText(sh.getAll().toString());
    }
}
