package tn.esprit.Controllers.contolleruser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Admin;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.utils.SessionManager;

import java.io.IOException;

public class GestionAdmin {
    @FXML
    private Button btnAjouter2;

    private final IService<Utilisateur> su = new ServiceUtilisateur();
    private final ServiceAdmin serviceAdmin = new ServiceAdmin();
    private Utilisateur user;



    @FXML
    private void Add(ActionEvent event) {
        if (event.getSource() == btnAjouter2) {
            System.out.println(" Bouton Ajouter cliqué");

            // Vérifier si l'utilisateur existe
            if (user == null) {
                System.out.println(" Aucun utilisateur sélectionné !");
                return;
            }

            // Ajouter l'utilisateur à la base de données
            su.add(user);
            System.out.println("✅ Utilisateur ajouté avec succès !");

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

    private void redirectToGereAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GereAdmin.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et transmettre les données de l'admin
            GereAdmin gereAdminController = loader.getController();

            // Changer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion de l'Administrateur");
            stage.show();
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du chargement de GereAdmin.fxml : " + e.getMessage());
        }
    }

    @FXML
    private void handleRetour(ActionEvent event) throws IOException {
        // Charger le fichier user.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));

        // Obtenir la scène actuelle et changer vers user.fxml
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
