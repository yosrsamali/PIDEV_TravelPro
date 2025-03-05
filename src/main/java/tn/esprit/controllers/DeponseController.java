package tn.esprit.controllers;

import javafx.fxml.Initializable;
import tn.esprit.utils.MyDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DeponseController implements Initializable {

    @FXML
    private ComboBox<String> cbNomProduit;
    @FXML
    private TextField tfQuantiteProduit;
    @FXML
    private TextField tfPrixAchat;
    @FXML
    private DatePicker dpDateAchat;
    @FXML
    private Button btnAjouter;

    private Map<String, Integer> produitsMap = new HashMap<>();
    private Connection cnx;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cnx = MyDatabase.getInstance().getCnx();
        remplirListeProduits();
    }

    private void remplirListeProduits() {
        String query = "SELECT id_produit, nom_produit FROM produit";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(query)) {

            while (rs.next()) {
                int idProduit = rs.getInt("id_produit");
                String nomProduit = rs.getString("nom_produit");
                cbNomProduit.getItems().add(nomProduit);
                produitsMap.put(nomProduit, idProduit);
            }
        } catch (SQLException e) {
            afficherErreur("❌ Erreur lors du chargement des produits : " + e.getMessage());
        }
    }

    @FXML
    private void ajouterDeponse() {
        if (cnx == null) {
            afficherErreur("❌ Connexion à la base de données non disponible !");
            return;
        }

        String nomProduit = cbNomProduit.getValue();
        if (nomProduit == null || nomProduit.isEmpty()) {
            afficherErreur("⚠️ Veuillez sélectionner un produit.");
            return;
        }

        Integer idProduit = produitsMap.get(nomProduit);
        if (idProduit == null) {
            afficherErreur("❌ Produit introuvable !");
            return;
        }

        int quantite;
        double prixAchat;
        LocalDate dateAchat = dpDateAchat.getValue();

        try {
            quantite = Integer.parseInt(tfQuantiteProduit.getText().trim());
            prixAchat = Double.parseDouble(tfPrixAchat.getText().trim());

            if (quantite <= 0 || prixAchat <= 0) {
                afficherErreur("⚠️ Les valeurs de quantité et prix doivent être positives.");
                return;
            }
            if (dateAchat == null) {
                afficherErreur("⚠️ Veuillez choisir une date d'achat.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("❌ Veuillez entrer des valeurs numériques valides.");
            return;
        }

        String query = "INSERT INTO deponse (id_produit, quantite_produit, prix_achat, date_achat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            cnx.setAutoCommit(false);

            pstm.setInt(1, idProduit);
            pstm.setInt(2, quantite);
            pstm.setDouble(3, prixAchat);
            pstm.setDate(4, Date.valueOf(dateAchat));

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                cnx.commit();
                afficherInfo("✅ Dépense ajoutée avec succès !");

                // 🔄 Redirection après ajout
                redirigerVersListeDepense();
                // Envoi de l'e-mail
                //String emailBody = "Une nouvelle dépense a été ajoutée.\nProduit: " + nomProduit + "\nQuantité: " + quantite + "\nPrix unitaire: " + prixAchat + " TND\nTotal: " + (quantite * prixAchat) + " TND";
               // SendGridMailer.sendEmail("25k01a2003c@gmail.com", "Nouvelle Dépense Ajoutée avec succes", emailBody);
            } else {
                afficherErreur("⚠️ Échec de l'ajout de la dépense.");
            }
        } catch (SQLException e) {
            try {
                cnx.rollback();
            } catch (SQLException rollbackEx) {
                afficherErreur("❌ Erreur de rollback : " + rollbackEx.getMessage());
            }
            afficherErreur("❌ Erreur SQL lors de l'ajout de la dépense : " + e.getMessage());
        } finally {
            try {
                if (cnx != null && !cnx.isClosed()) {
                    cnx.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Impossible de réactiver AutoCommit, mais cela n'empêche pas la redirection.");
            }
        }
    }

    private void redirigerVersListeDepense() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listdepense.fxml"));
            AnchorPane root = loader.load();

            // Obtenir la scène actuelle et la remplacer
            Stage stage = (Stage) cbNomProduit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            afficherErreur("❌ Erreur lors du chargement de la page des dépenses : " + e.getMessage());
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}