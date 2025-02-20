package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceHotel;

public class ModifierHotelController {

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

    private Hotel hotel;
    private ServiceHotel serviceHotel = new ServiceHotel();

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        tfNom.setText(hotel.getNom());
        tfVille.setText(hotel.getVille());
        tfPrixParNuit.setText(String.valueOf(hotel.getPrixParNuit()));
        tfDisponible.setText(hotel.isDisponible() ? "Oui" : "Non");
        tfNombreEtoile.setText(String.valueOf(hotel.getNombreEtoile()));
        tfTypeDeChambre.setText(hotel.getTypeDeChambre());
    }

    @FXML
    private void handleModifier() {
        // Mettre à jour l'hôtel
        hotel.setNom(tfNom.getText());
        hotel.setVille(tfVille.getText());
        hotel.setPrixParNuit(Double.parseDouble(tfPrixParNuit.getText()));
        hotel.setDisponible(tfDisponible.getText().equalsIgnoreCase("Oui"));
        hotel.setNombreEtoile(Integer.parseInt(tfNombreEtoile.getText()));
        hotel.setTypeDeChambre(tfTypeDeChambre.getText());

        serviceHotel.update(hotel);
        ((javafx.stage.Stage) tfNom.getScene().getWindow()).close(); // Fermer la fenêtre
    }
}