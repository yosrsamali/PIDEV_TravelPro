package tn.esprit.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import tn.esprit.utils.MyDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListDeponseController implements Initializable {

    @FXML
    private VBox vboxContainer; // Conteneur pour les cartes

    @FXML
    private TextField searchField;

    private List<BorderPane> cartesDepenses = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerDepenses();
    }

    private void chargerDepenses() {
        String query = "SELECT d.id_deponse, p.nom_produit, d.quantite_produit, d.prix_achat "
                + "FROM deponse d JOIN produit p ON d.id_produit = p.id_produit";

        try (Connection cnx = MyDatabase.getInstance().getCnx();
             Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(query)) {

            while (rs.next()) {
                int idDeponse = rs.getInt("id_deponse");
                String nomProduit = rs.getString("nom_produit");
                int quantite = rs.getInt("quantite_produit");
                double prixAchat = rs.getDouble("prix_achat");

                // Calcul du total à l'affichage
                double total = quantite * prixAchat;

                BorderPane carte = creerCarteDepense(idDeponse, nomProduit, quantite, prixAchat, total);
                cartesDepenses.add(carte);  // Ajoute la carte à la liste
                vboxContainer.getChildren().add(carte);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BorderPane creerCarteDepense(int id, String nomProduit, int quantite, double prixAchat, double total) {
        // Conteneur principal
        BorderPane card = new BorderPane();
        card.getStyleClass().add("card"); // Ajout du style depuis CSS

        // Titre (Nom du Produit)
        Label lblProduit = new Label(nomProduit);
        lblProduit.setFont(new Font("Arial", 18));
        lblProduit.getStyleClass().add("card-title");

        card.setTop(lblProduit);

        // Contenu principal
        HBox content = new HBox(20);
        content.getStyleClass().add("card-content");

        Label lblQuantite = new Label("Quantité : " + quantite);
        lblQuantite.getStyleClass().add("card-text");

        Label lblPrix = new Label("Prix : " + prixAchat + " TND");
        lblPrix.getStyleClass().add("card-text");

        Label lblTotal = new Label("Total : " + total + " TND");
        lblTotal.getStyleClass().add("card-total");

        content.getChildren().addAll(lblQuantite, lblPrix, lblTotal);
        card.setCenter(content);

        // Ajouter les boutons Modifier et Supprimer
        HBox buttonBox = new HBox(10);
        Button btnModifier = new Button("Modifier");
        btnModifier.setOnAction(event -> modifierDepense(id, lblQuantite, lblPrix, lblTotal)); // Action de modification
        btnModifier.getStyleClass().add("button");

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> supprimerDepense(id)); // Action de suppression
        btnSupprimer.getStyleClass().add("button");

        buttonBox.getChildren().addAll(btnModifier, btnSupprimer);
        card.setBottom(buttonBox);

        return card;
    }

    // Cette méthode sera appelée lors du clic sur le bouton "Gérer Revenues"
    @FXML
    public void handleRevenue() {
        try {
            // Charger revenue.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/revenue.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour afficher la gestion des revenues
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("---- Gestion Revenue -----");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour modifier une dépense
    private void modifierDepense(int id_deponse, Label lblQuantite, Label lblPrix, Label lblTotal) {
        // Ouvrir une nouvelle fenêtre pour modifier la dépense
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15; -fx-alignment: center; -fx-background-color: linear-gradient(to bottom, #37B7C3, #071952);");

        // Récupérer les données de la dépense à modifier
        int quantite = Integer.parseInt(lblQuantite.getText().split(":")[1].trim());
        double prixAchat = Double.parseDouble(lblPrix.getText().split(":")[1].trim().split(" ")[0]);
        String labelStyle = "-fx-font-size: 16px; -fx-text-fill: #ffffff; -fx-font-weight: bold;";

        // Création des champs pour la modification
        Label lblQuantiteLabel = new Label("Quantité");
        lblQuantiteLabel.setStyle(labelStyle);

        TextField tfQuantite = new TextField(String.valueOf(quantite));

        Label lblPrixAchat = new Label("Prix d'Achat");
        lblPrixAchat.setStyle(labelStyle);

        TextField tfPrixAchat = new TextField(String.valueOf(prixAchat));

        Label lblTotalModifie = new Label("Total : ");
        lblTotalModifie.setFont(new Font("Arial", 14));
        lblTotalModifie.setOpacity(0);

        // Calcul automatique du total quand le prix ou la quantité change
        tfQuantite.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int newQuantite = Integer.parseInt(newValue);
                double total = newQuantite * prixAchat;
                lblTotalModifie.setText("Total : " + total + " TND");
            } catch (NumberFormatException e) {
                lblTotalModifie.setText("Total : 0 TND");
            }
        });

        tfPrixAchat.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double newPrix = Double.parseDouble(newValue);
                double total = quantite * newPrix;
                lblTotalModifie.setText("Total : " + total + " TND");
            } catch (NumberFormatException e) {
                lblTotalModifie.setText("Total : 0 TND");
            }
        });

        Button btnSave = new Button("Enregistrer");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSave.setOnAction(event -> {
            // Récupérer les nouvelles valeurs
            int newQuantite = Integer.parseInt(tfQuantite.getText());
            double newPrixAchat = Double.parseDouble(tfPrixAchat.getText());
            double newTotal = newQuantite * newPrixAchat;

            // Mettre à jour la base de données
            modifierDansBaseDeDonnees(id_deponse, newQuantite, newPrixAchat);

            // Mettre à jour le label Total dans la carte (interface)
            lblTotal.setText("Total : " + newTotal + " TND");

            // Fermer la fenêtre
            stage.close();
        });

        vbox.getChildren().addAll(lblQuantiteLabel, tfQuantite, lblPrixAchat, tfPrixAchat, lblTotalModifie, btnSave);

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Modifier Dépense");
        stage.setScene(scene);
        stage.show();
    }

    private void modifierDansBaseDeDonnees(int id_deponse, int newQuantite, double newPrixAchat) {
        String query = "UPDATE deponse SET quantite_produit = ?, prix_achat = ? WHERE id_deponse = ?";

        try (Connection cnx = MyDatabase.getInstance().getCnx()) {
            if (cnx != null && !cnx.isClosed()) {
                try (PreparedStatement pst = cnx.prepareStatement(query)) {
                    pst.setInt(1, newQuantite);
                    pst.setDouble(2, newPrixAchat);
                    pst.setInt(3, id_deponse);

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Dépense modifiée avec succès.");
                        // Rafraîchir la liste après modification
                        vboxContainer.getChildren().clear();
                        chargerDepenses();
                    }
                }
            } else {
                System.out.println("La connexion est fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void supprimerDepense(int id) {
        String query = "DELETE FROM deponse WHERE id_deponse = ?";

        try (Connection cnx = MyDatabase.getInstance().getCnx()) {
            if (cnx != null && !cnx.isClosed()) {
                try (PreparedStatement pst = cnx.prepareStatement(query)) {
                    pst.setInt(1, id);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Dépense supprimée avec succès.");
                        // Rafraîchir la liste après suppression
                        vboxContainer.getChildren().clear();
                        chargerDepenses();
                    }
                }
            } else {
                System.out.println("La connexion est fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        filtrerDepenses(searchField.getText());
    }

    @FXML
    private void filtrerDepenses(String searchText) {
        System.out.println("Recherche : " + searchText);  // Vérifie ce que tu cherches
        vboxContainer.getChildren().clear(); // Effacer les cartes actuelles

        if (searchText == null || searchText.isEmpty()) {
            vboxContainer.getChildren().addAll(cartesDepenses);
        } else {
            List<BorderPane> cartesFiltrees = cartesDepenses.stream()
                    .filter(carte -> {
                        Node topNode = carte.getTop();
                        if (topNode instanceof Label lblProduit) {
                            System.out.println("Label text: " + lblProduit.getText());  // Vérifie le texte du Label
                            return lblProduit.getText().toLowerCase().contains(searchText.toLowerCase());
                        }
                        return false;
                    })
                    .toList();

            if (cartesFiltrees.isEmpty()) {
                Label noResult = new Label("Aucun résultat trouvé.");
                noResult.setFont(new Font("Arial", 16));
                noResult.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                vboxContainer.getChildren().add(noResult);
            } else {
                vboxContainer.getChildren().addAll(cartesFiltrees);
            }
        }
    }

    @FXML
    public void filtrageDepenses(KeyEvent keyEvent) {
        String searchText = searchField.getText().trim();
        System.out.println("Recherche par clavier : " + searchText);  // Vérifie ce que tu cherches
        filtrerDepenses(searchText);
    }
}
