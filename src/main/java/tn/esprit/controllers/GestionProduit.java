package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceProduit;

public class GestionProduit {
    @FXML
    private TextField tfNomProduit;
    @FXML
    private TextField tfPrixProduit;
    @FXML
    private TextField tfQuantiteProduit;

    IService<Produit> sp = new ServiceProduit();
    @FXML
    private Label lbProduits;

    @FXML
    public void ajouterProduit(ActionEvent actionEvent) {
        Produit p = new Produit();
        p.setNomProduit(tfNomProduit.getText());
        p.setPrixProduit(Double.parseDouble(tfPrixProduit.getText()));
        p.setQuantiteProduit(Integer.parseInt(tfQuantiteProduit.getText()));

        sp.add(p);
    }

    @FXML
    public void afficherProduits(ActionEvent actionEvent) {
        lbProduits.setText(sp.getAll().toString());
    }
}
