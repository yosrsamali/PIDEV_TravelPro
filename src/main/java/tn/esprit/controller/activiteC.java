package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.services.ServiceActivite;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class activiteC implements Initializable {

    @FXML
    private TableView<Activite> activiteTableView;

    @FXML
    private TableColumn<Activite, Integer> idColumn;

    @FXML
    private TableColumn<Activite, String> nomColumn;

    @FXML
    private TableColumn<Activite, String> descriptionColumn;

    @FXML
    private TableColumn<Activite, Date> dateDebutColumn;

    @FXML
    private TableColumn<Activite, Date> dateFinColumn;

    @FXML
    private TableColumn<Activite, Integer> idEventColumn;

    @FXML
    private TextField nomActiviteInput;

    @FXML
    private TextArea descriptionInput;

    @FXML
    private DatePicker dateDebutInput;

    @FXML
    private DatePicker dateFinInput;

    @FXML
    private TextField idEventInput;

    @FXML
    private TextField searchInput;

    @FXML
    private TextField filterInput;

    private ServiceActivite serviceActivite = new ServiceActivite();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation des colonnes de la TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idActivite"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomActivite"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebutA"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFinA"));
        idEventColumn.setCellValueFactory(new PropertyValueFactory<>("idEvent"));

        // Chargement des données dans la TableView
        loadActivites();
    }

    private void loadActivites() {
        List<Activite> activites = serviceActivite.getAll();
        ObservableList<Activite> observableList = FXCollections.observableArrayList(activites);
        activiteTableView.setItems(observableList);
    }

    @FXML
    void addActivite(ActionEvent event) {
        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() || dateDebutInput.getValue() == null || dateFinInput.getValue() == null || idEventInput.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            Activite activite = new Activite();
            activite.setNomActivite(nomActiviteInput.getText());
            activite.setDescription(descriptionInput.getText());
            activite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            activite.setDateFinA(Date.valueOf(dateFinInput.getValue()));
            activite.setIdEvent(Integer.parseInt(idEventInput.getText()));

            serviceActivite.add(activite);
            loadActivites(); // Recharger les données après l'ajout

            // Réinitialiser les champs
            nomActiviteInput.clear();
            descriptionInput.clear();
            dateDebutInput.setValue(null);
            dateFinInput.setValue(null);
            idEventInput.clear();

            showAlert("Succès", "Activité ajoutée avec succès.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID de l'événement doit être un nombre.");
        }
    }

    @FXML
    void updateActivite(ActionEvent event) {
        Activite selectedActivite = activiteTableView.getSelectionModel().getSelectedItem();
        if (selectedActivite == null) {
            showAlert("Erreur", "Veuillez sélectionner une activité à modifier.");
            return;
        }

        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() || dateDebutInput.getValue() == null || dateFinInput.getValue() == null || idEventInput.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            selectedActivite.setNomActivite(nomActiviteInput.getText());
            selectedActivite.setDescription(descriptionInput.getText());
            selectedActivite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            selectedActivite.setDateFinA(Date.valueOf(dateFinInput.getValue()));
            selectedActivite.setIdEvent(Integer.parseInt(idEventInput.getText()));

            serviceActivite.update(selectedActivite, selectedActivite.getIdActivite());
            loadActivites(); // Recharger les données après la mise à jour

            showAlert("Succès", "Activité mise à jour avec succès.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID de l'événement doit être un nombre.");
        }
    }

    @FXML
    void deleteActivite(ActionEvent event) {
        Activite selectedActivite = activiteTableView.getSelectionModel().getSelectedItem();
        if (selectedActivite == null) {
            showAlert("Erreur", "Veuillez sélectionner une activité à supprimer.");
            return;
        }

        serviceActivite.delete(selectedActivite.getIdActivite());
        loadActivites(); // Recharger les données après la suppression

        showAlert("Succès", "Activité supprimée avec succès.");
    }

    @FXML
    void searchActivite(ActionEvent event) {
        String searchText = searchInput.getText().trim();
        if (searchText.isEmpty()) {
            loadActivites(); // Recharger toutes les activités si le champ de recherche est vide
            return;
        }

        List<Activite> activites = serviceActivite.getAll();
        ObservableList<Activite> filteredList = FXCollections.observableArrayList();

        for (Activite activite : activites) {
            if (String.valueOf(activite.getIdActivite()).contains(searchText) ||
                    activite.getNomActivite().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(activite);
            }
        }

        activiteTableView.setItems(filteredList);
    }

    @FXML
    void filterActivite(ActionEvent event) {
        String filterText = filterInput.getText().trim();
        if (filterText.isEmpty()) {
            loadActivites(); // Recharger toutes les activités si le champ de filtre est vide
            return;
        }

        List<Activite> activites = serviceActivite.getAll();
        ObservableList<Activite> filteredList = FXCollections.observableArrayList();

        for (Activite activite : activites) {
            if (String.valueOf(activite.getIdActivite()).contains(filterText) ||
                    activite.getNomActivite().toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(activite);
            }
        }

        activiteTableView.setItems(filteredList);
    }

    @FXML
    void sortById(ActionEvent event) {
        activiteTableView.getItems().sort(Comparator.comparingInt(Activite::getIdActivite));
    }

    @FXML
    void sortByName(ActionEvent event) {
        activiteTableView.getItems().sort(Comparator.comparing(Activite::getNomActivite));
    }

    @FXML
    void refreshActivites(ActionEvent event) {
        loadActivites(); // Recharge toutes les activités
        searchInput.clear(); // Vide le champ de recherche
        filterInput.clear(); // Vide le champ de filtre
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void retourner(ActionEvent actionEvent) {
        // Charger l'interface AjouterUser
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tools1.fxml"));
            Parent root = fxmlLoader.load();

            // Changer la scène de la même fenêtre (Stage)
            Stage stage = (Stage) dateDebutInput.getScene().getWindow(); // Récupérer le Stage actuel
            stage.setScene(new Scene(root)); // Remplacer la scène avec l'interface d'ajout
            stage.setTitle("travelPro"); // Mettre à jour le titre de la fenêtre
        } catch (IOException e) {
            e.printStackTrace();
            afficherAlerte("Erreur", "Impossible de charger l'interface d'ajout.");
        }
    }

    private void afficherAlerte(String erreur, String s) {
    }
}