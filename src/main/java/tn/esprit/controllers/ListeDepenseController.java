package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.utils.MyDatabase;
import tn.esprit.models.deponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ListeDepenseController {

    @FXML
    private TableView<deponse> deponseTableView;
    @FXML
    private TableColumn<deponse, Integer> colId;
    @FXML
    private TableColumn<deponse, Integer> colQuantiteTotal;
    @FXML
    private TableColumn<deponse, Double> colPrixAchat;
    @FXML
    private TableColumn<deponse, Double> colTVA;
    @FXML
    private TableColumn<deponse, Double> colTotal;
    @FXML
    private TableColumn<deponse, Void> colActions;

    // Liste observable qui contiendra les données récupérées
    ObservableList<deponse> depensesList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialiser les colonnes avec les propriétés de la classe deponse
        colId.setCellValueFactory(new PropertyValueFactory<>("id_deponse"));
        colQuantiteTotal.setCellValueFactory(new PropertyValueFactory<>("quantite_total"));
        colPrixAchat.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
        colTVA.setCellValueFactory(new PropertyValueFactory<>("tva"));
        colTVA.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Récupérer les données depuis la base de données et les ajouter à la table
        loadDepensesFromDatabase();


        // Définir la cell factory pour la colonne Actions
        colActions.setCellFactory(param -> new TableCell<deponse, Void>() {
            private final Button btnModifier = new Button("Modifier");
            private final Button btnSupprimer = new Button("Supprimer");
            private final HBox hbox = new HBox(10, btnModifier, btnSupprimer);

            {
                btnModifier.setOnAction(event -> modifierDepense(getTableRow().getItem()));
                btnSupprimer.setOnAction(event -> supprimerDepense(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });
    }

    // Méthode pour récupérer les données depuis la base de données
    private void loadDepensesFromDatabase() {
        String query = "SELECT * FROM deponse"; // SQL pour récupérer toutes les données de la table `deponse`

        try (Connection conn = MyDatabase.getInstance().getCnx();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Récupérer les données de chaque ligne du résultat
                int id_deponse = rs.getInt("id_deponse");
                int quantite_total = rs.getInt("quantite_total");
                double prix_achat = rs.getDouble("prix_achat");
                double tva = rs.getDouble("tva");
                double total=rs.getDouble("total");

                // Ajouter chaque objet `deponse` à la liste observable
                depensesList.add(new deponse(id_deponse, quantite_total, prix_achat, tva, total));
            }

            // Vérifier la taille de la liste observable
            System.out.println("Nombre de dépenses récupérées: " + depensesList.size());

            // Lier la liste observable avec la TableView
            deponseTableView.setItems(depensesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void modifierDepense(deponse dep) {
        // Logique pour modifier la dépense
        System.out.println("Modifier la dépense: " + dep.getId_deponse());
    }

    private void supprimerDepense(deponse dep) {
        // Appel de la méthode delete pour supprimer la dépense dans la base de données
        MyDatabase.getInstance().delete(dep);

        // Suppression de la dépense de la liste observable (TableView)
        depensesList.remove(dep);

        // Message dans la console pour vérifier
        System.out.println("Dépense supprimée de la base et de la TableView: " + dep.getId_deponse());
    }

}
