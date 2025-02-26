package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

        for (DemandeValidation demande : demandes) {
            Pane card = creerCarteDemande(demande);
            containerDemandes.getChildren().add(card);
        }
    }

    private Pane creerCarteDemande(DemandeValidation demande) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-padding: 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 5);");
        card.setMinWidth(400);

        Label lblId = new Label("ID : " + demande.getId());
        Label lblNomClient = new Label("Nom du client : " + demande.getNomClient());
        Label lblStatut = new Label("Statut actuel : " + demande.getStatut());

        lblId.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        lblNomClient.setStyle("-fx-font-size: 14px;");
        lblStatut.setStyle("-fx-font-size: 14px;");

        ToggleGroup groupStatut = new ToggleGroup();
        RadioButton rbEnAttente = new RadioButton("En attente");
        RadioButton rbAccepte = new RadioButton("Approuvé");
        RadioButton rbRefuse = new RadioButton("Rejeté");

        rbEnAttente.setToggleGroup(groupStatut);
        rbAccepte.setToggleGroup(groupStatut);
        rbRefuse.setToggleGroup(groupStatut);

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
                if ("Approuvé".equals(newStatut)) {
                    ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
                    ServiceClient serviceClient=new ServiceClient();
                    ServiceAdmin serviceAdmin = new ServiceAdmin();
                    Client Cl = serviceClient.getById(demande.getidClient());
                    if (Cl == null) {
                        System.out.println("⚠️ Aucun client trouvé avec ID: " + demande.getidClient());
                        return;
                    }

                    Utilisateur utilisateur = serviceUtilisateur.getById(Cl.getId());
                    if (utilisateur != null) {
                        utilisateur.setRole("Admin");
                        serviceUtilisateur.update(utilisateur);
                        Admin nouvelAdmin = new Admin(Cl.getId());
                        serviceAdmin.add(nouvelAdmin);
                    }
                }  else if ("En attente".equals(newStatut) || "Rejeté".equals(newStatut)) {
                    ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
                    ServiceClient serviceClient=new ServiceClient();
                    Client Cl = serviceClient.getById(demande.getidClient());
                    if (Cl == null) {
                        System.out.println("⚠️ Aucun client trouvé avec ID: " + demande.getidClient());
                        return;
                    }

                    Utilisateur utilisateur = serviceUtilisateur.getById(Cl.getId());

                    utilisateur.setRole("Client");
                serviceUtilisateur.update(utilisateur);
            }
            }
        });

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSupprimer.setOnAction(e -> {
            serviceDemande.delete(demande.getId());
            chargerListeDemandes();
        });

        HBox hboxStatut = new HBox(10, rbEnAttente, rbAccepte, rbRefuse);
        hboxStatut.setStyle("-fx-padding: 5px;");

        card.getChildren().addAll(lblId, lblNomClient, lblStatut, hboxStatut, btnSupprimer);
        return card;
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

   /* @FXML
    private void Add(ActionEvent event) {
        if (event.getSource() == btnAjouter2) {
            System.out.println(" Bouton Ajouter cliqué");
            Utilisateur utilisateur = SessionManager.getInstance().getUtilisateurConnecte();


            Utilisateur user= new Utilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(), utilisateur.getPassword(), utilisateur.getRole());
            // Vérifier si l'utilisateur existe
            if (user == null) {
                System.out.println(" Aucun utilisateur sélectionné !");
                return;
            }
            user.setRole("Admin");
            // Ajouter l'utilisateur à la base de données
            serviceUtilisateur.update(user);
            System.out.println("✅ Utilisateur ajouté avec succès !");
            user.setId(utilisateur.getId());
            // Vérifier si l'utilisateur a bien été ajouté
            if (user.getId() == 0) {
                System.out.println("❌ Erreur : l'utilisateur n'a pas été ajouté.");
                return;
            }

            // Création et ajout de l'administrateur associé
            Admin newAdmin = new Admin();
            newAdmin.setId(user.getId());
            newAdmin.setMail(user.getMail());
            newAdmin.setNom(user.getNom());
            newAdmin.setPrenom(user.getPrenom());
            newAdmin.setRole(user.getRole());
            newAdmin.setPassword(user.getPassword());

            serviceAdmin.add(newAdmin);
            System.out.println("✅ Administrateur ajouté avec succès !");

            // **Ajout de l'admin à la session**
            SessionManager.getInstance().setUtilisateurConnecte(newAdmin);
            System.out.println("✅ Admin ajouté à la session !");

            // **Redirection vers `GereAdmin.fxml`**
            redirectToGereAdmin(event);
        }
    }
*/


}
