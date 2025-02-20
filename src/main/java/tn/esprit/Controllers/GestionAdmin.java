package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Admin;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;
public class GestionAdmin {
    @FXML
    private Button btnAjouter2;
    @FXML
    private final IService<Utilisateur> su = new ServiceUtilisateur();
    private final ServiceAdmin  serviceAdmin= new ServiceAdmin();

    private Utilisateur user;

    public void ajouterdonner(Utilisateur user) {
        this.user = user;
    }

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
            System.out.println("------------------------------cv0-----------------------------");

            // Vérifier si l'utilisateur a bien été ajouté
            if (user.getId() == 0) {
                System.out.println(" Erreur : l'utilisateur n'a pas été ajouté.");
                return;
            }
            System.out.println("------------------------------cv1-----------------------------");
            // Création et ajout du client associé
            Admin newAdmin = new Admin();
            newAdmin.setIdUtilisateur(user.getId());

            System.out.println("------------------------------cv2-----------------------------");

            serviceAdmin.add(newAdmin);
            System.out.println("------------------------------cv3-----------------------------");

            System.out.println(" Utilisateur et client ajoutés avec succès !");
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
