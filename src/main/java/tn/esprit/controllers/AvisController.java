package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Avis;
import tn.esprit.services.ServiceAvis;

import java.sql.Timestamp;
import java.util.Date;

public class AvisController {

    @FXML
    private TextField noteField;

    @FXML
    private TextField commentaireField;

    @FXML
    private Button submitButton;

    private final ServiceAvis serviceAvis = new ServiceAvis();

    @FXML
    public void initialize() {

        submitButton.setOnAction(event -> ajouterAvis());
    }

    private void ajouterAvis() {
        try {

            int note = Integer.parseInt(noteField.getText().trim());
            String commentaire = commentaireField.getText().trim();

            // Validation simple
            if (note < 0 || note > 10) {
                System.out.println("❌ La note doit être entre 0 et 10.");
                return;
            }
            if (commentaire.isEmpty()) {
                System.out.println("❌ Le commentaire ne peut pas être vide.");
                return;
            }


            Avis avis = new Avis(0, note, commentaire, new Timestamp(new Date().getTime()), false);


            serviceAvis.add(avis);
            System.out.println("✅ Avis ajouté avec succès !");


            noteField.clear();
            commentaireField.clear();
        } catch (NumberFormatException e) {
            System.out.println("❌ Veuillez entrer un nombre valide pour la note.");
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'ajout de l'avis : " + e.getMessage());
        }
    }
}
