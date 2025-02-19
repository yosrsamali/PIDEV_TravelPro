package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceVoiture;

import java.util.List;

public class GestionVoiture {
    @FXML
    private TextField tfMarque;
    @FXML
    private TextField tfModele;
    @FXML
    private TextField tfAnnee;
    @FXML
    private TextField tfPrixParJour;
    @FXML
    private TextField tfDisponible;
    @FXML
    private TextField tfId; // Champ pour l'ID de la voiture à mettre à jour ou supprimer
    @FXML
    private Label lbVoitures;

    IService<Voiture> sv = new ServiceVoiture();

    @FXML
    public void ajouterVoiture(ActionEvent actionEvent) {
        Voiture v = new Voiture(
                tfMarque.getText(),
                tfModele.getText(),
                Integer.parseInt(tfAnnee.getText()),
                Double.parseDouble(tfPrixParJour.getText()),
                Boolean.parseBoolean(tfDisponible.getText())
        );

        sv.add(v);
        afficherVoitures(null); // Rafraîchir l'affichage après l'ajout
    }

    @FXML
    public void afficherVoitures(ActionEvent actionEvent) {
        lbVoitures.setText(sv.getAll().toString());
    }

    @FXML
    public void mettreAJourVoiture(ActionEvent actionEvent) {
        Voiture v = new Voiture(
                tfMarque.getText(),
                tfModele.getText(),
                Integer.parseInt(tfAnnee.getText()),
                Double.parseDouble(tfPrixParJour.getText()),
                Boolean.parseBoolean(tfDisponible.getText())
        );
        v.setId(Integer.parseInt(tfId.getText())); // Récupérer l'ID de la voiture à mettre à jour

        sv.update(v);
        afficherVoitures(null); // Rafraîchir l'affichage après la mise à jour
    }

    @FXML
    public void supprimerVoiture(ActionEvent actionEvent) {
        Voiture v = new Voiture();
        v.setId(Integer.parseInt(tfId.getText())); // Récupérer l'ID de la voiture à supprimer

        sv.delete(v);
        afficherVoitures(null); // Rafraîchir l'affichage après la suppression
    }

    @FXML
    public void afficherDetailsVoiture(ActionEvent actionEvent) {
        int id = Integer.parseInt(tfId.getText()); // Récupérer l'ID de la voiture à afficher
        List<Voiture> voitures = sv.getAll();
        for (Voiture v : voitures) {
            if (v.getId() == id) {
                lbVoitures.setText(v.toString());
                return;
            }
        }
        lbVoitures.setText("Voiture non trouvée.");
    }
}