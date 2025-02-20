package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Client;
import tn.esprit.services.ServiceClient;





public class GestionClient {

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfTel;

    @FXML
    private Label lblUserInfo;

    @FXML
    private Button btnAjouter;

    private final IService<Utilisateur> su = new ServiceUtilisateur();
    private final ServiceClient serviceClient = new ServiceClient();

    private Utilisateur user;

    public void ajouterdonner(Utilisateur user) {
        this.user = user;
    }

    @FXML
    private void Add(ActionEvent event) {
        if (event.getSource() == btnAjouter) {
            System.out.println(" Bouton Ajouter cliqué !");

            // Vérification des champs
            String address = tfAddress.getText();
            String tel = tfTel.getText();

            if (address.isEmpty() || tel.isEmpty()) {
                System.out.println("⚠ Veuillez remplir tous les champs !");
                return;
            }

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
            Client newClient = new Client();
            newClient.setIdUtilisateur(user.getId());
            newClient.setNumTel(tel);
            newClient.setAdresse(address);
            System.out.println("------------------------------cv2-----------------------------");

            serviceClient.add(newClient);
            System.out.println("------------------------------cv3-----------------------------");

            System.out.println(" Utilisateur et client ajoutés avec succès !");
        }
    }


    @FXML
    private void handleRetour(ActionEvent event) throws IOException {
        // Charger user.fxml (assurez-vous qu'il est dans resources/)
        Parent root = FXMLLoader.load(getClass().getResource("/user.fxml"));

        // Obtenir la scène actuelle et changer vers user.fxml
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
