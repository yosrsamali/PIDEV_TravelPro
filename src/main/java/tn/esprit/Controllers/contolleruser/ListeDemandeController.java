package tn.esprit.Controllers.contolleruser;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.Admin;
import tn.esprit.models.Client;
import tn.esprit.models.DemandeValidation;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.*;

import java.io.IOException;
import java.util.List;

public class ListeDemandeController {

    @FXML
    private VBox containerDemandes; // Conteneur des cartes

    private final ServiceDemandeValidation serviceDemande = new ServiceDemandeValidation();

    @FXML
    public void initialize() {
        chargerListeDemandes();
    }

    public void chargerListeDemandes() {
        List<DemandeValidation> demandes = serviceDemande.getAll();
        containerDemandes.getChildren().clear(); // Vider avant d'ajouter de nouvelles cartes

        // Create a new HBox for each row
        HBox currentRow = new HBox(15);
        currentRow.setStyle("-fx-alignment: center;");

        // Loop through the demandes and add each card to the current row
        for (int i = 0; i < demandes.size(); i++) {
            DemandeValidation demande = demandes.get(i);
            Pane card = creerCarteDemande(demande);

            // Add the card to the current row
            currentRow.getChildren().add(card);

            // If there are 3 cards in the row, add the row to the container and start a new row
            if ((i + 1) % 3 == 0 || i == demandes.size() - 1) {
                containerDemandes.getChildren().add(currentRow);
                currentRow = new HBox(15); // Start a new row
                currentRow.setStyle("-fx-alignment: center;");
            }
        }
    }

    private Pane creerCarteDemande(DemandeValidation demande) {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ffffff; -fx-border-radius: 15px; -fx-padding: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 5);");
        card.setPrefWidth(300); // Adjust the preferred width of the card

        Label lblNomClient = new Label("Nom du client : " + demande.getNomClient());
        Label lblStatut = new Label("Statut actuel : " + demande.getStatut());

        lblNomClient.setStyle("-fx-font-size: 14px;");
        lblStatut.setStyle("-fx-font-size: 14px;");

        ToggleGroup groupStatut = new ToggleGroup();
        RadioButton rbEnAttente = new RadioButton("En attente");
        RadioButton rbAccepte = new RadioButton("Approuvé");
        RadioButton rbRefuse = new RadioButton("Rejeté");

        rbEnAttente.setToggleGroup(groupStatut);
        rbAccepte.setToggleGroup(groupStatut);
        rbRefuse.setToggleGroup(groupStatut);

        // Default selection based on the current status
        switch (demande.getStatut()) {
            case "En attente":
                rbEnAttente.setSelected(true);
                break;
            case "Approuvé":
                rbAccepte.setSelected(true);
                break;
            case "Rejeté":
                rbRefuse.setSelected(true);
                break;
        }

        groupStatut.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedButton = (RadioButton) newValue;
                String newStatut = selectedButton.getText();
                serviceDemande.updateStatut(demande.getId(), newStatut);
                lblStatut.setText("Statut actuel : " + newStatut);

                // Update the status with additional logic for roles
                updateUserRoleBasedOnStatus(demande, newStatut);
            }
        });

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 10px;");
        btnSupprimer.setOnAction(e -> {
            serviceDemande.delete(demande.getId());
            chargerListeDemandes();
        });

        HBox hboxStatut = new HBox(15, rbEnAttente, rbAccepte, rbRefuse);
        hboxStatut.setStyle("-fx-padding: 5px; -fx-alignment: center;");

        // Style the card background color based on the status
        switch (demande.getStatut()) {
            case "Approuvé":
                card.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745; -fx-border-radius: 15px; -fx-padding: 20px;");
                break;
            case "Rejeté":
                card.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545; -fx-border-radius: 15px; -fx-padding: 20px;");
                break;
            case "En attente":
            default:
                card.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffc107; -fx-border-radius: 15px; -fx-padding: 20px;");
                break;
        }

        card.getChildren().addAll(lblNomClient, lblStatut, hboxStatut, btnSupprimer);
        return card;
    }

    private void updateUserRoleBasedOnStatus(DemandeValidation demande, String newStatut) {
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        ServiceClient serviceClient = new ServiceClient();
        ServiceAdmin serviceAdmin = new ServiceAdmin();

        Client Cl = serviceClient.getById(demande.getidClient());
        if (Cl == null) {
            System.out.println("⚠️ Aucun client trouvé avec ID: " + demande.getidClient());
            return;
        }

        Utilisateur utilisateur = serviceUtilisateur.getById(Cl.getId());
        if (utilisateur != null) {
            if ("Approuvé".equals(newStatut)) {
                utilisateur.setRole("Admin");
                serviceUtilisateur.update(utilisateur);
                Admin nouvelAdmin = new Admin(Cl.getId());
                serviceAdmin.add(nouvelAdmin);
            } else {
                utilisateur.setRole("Client");
                serviceUtilisateur.update(utilisateur);
            }
        }
    }

    @FXML
    private void handleRetour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GereAdmin.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Espace Admin");
            stage.show();
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du retour : " + e.getMessage());
        }
    }
}
