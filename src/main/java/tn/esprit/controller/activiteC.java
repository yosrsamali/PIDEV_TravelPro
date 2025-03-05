package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.models.evenement;
import tn.esprit.services.ServiceActivite;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class activiteC implements Initializable {

    @FXML private GridPane activiteGridPane;
    @FXML private TextField nomActiviteInput;
    @FXML private TextArea descriptionInput;
    @FXML private DatePicker dateDebutInput;
    @FXML private DatePicker dateFinInput;
    @FXML private ComboBox<String> searchCriteriaComboBox;
    @FXML private TextField searchInput;
    @FXML private ComboBox<String> sortCriteriaComboBox;
    @FXML private BarChart<String, Number> activiteBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label averageDurationLabel;

    private ServiceActivite serviceActivite = new ServiceActivite();
    private Activite selectedActivite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser les ComboBox


        // Charger les activités
        loadActivites();
        setupDynamicSearch();
        sortCriteriaComboBox.setItems(FXCollections.observableArrayList("Nom", "Date Début", "Date Fin"));
        sortCriteriaComboBox.getSelectionModel().selectFirst();
        // Générer les statistiques

    }

    private void loadActivites() {
        activiteGridPane.getChildren().clear();
        List<Activite> activites = serviceActivite.getAll();
        if (activites.isEmpty()) {
            showAlert("Information", "Aucune activité trouvée.");
        } else {
            int row = 0, col = 0;
            for (Activite activite : activites) {
                addActiviteCard(activite, row, col);
                col++;
                if (col > 2) { // 3 cartes par ligne
                    col = 0;
                    row++;
                }
            }
        }
    }

    private void addActiviteCard(Activite activite, int row, int col) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.getStyleClass().add("card");

        Label nomLabel = new Label("Nom: " + activite.getNomActivite());
        Label descriptionLabel = new Label("Description: " + activite.getDescription());
        Label dateDebutLabel = new Label("Date Début: " + activite.getDateDebutA());
        Label dateFinLabel = new Label("Date Fin: " + activite.getDateFinA());

        Button modifierButton = new Button("Modifier");
        Button supprimerButton = new Button("Supprimer");

        // Ajouter des Tooltips
        Tooltip.install(modifierButton, new Tooltip("Modifier cette activité"));
        Tooltip.install(supprimerButton, new Tooltip("Supprimer cette activité"));

        modifierButton.setOnAction(event -> modifierActivite(activite));
        supprimerButton.setOnAction(event -> supprimerActivite(activite));

        HBox buttonsBox = new HBox(10, modifierButton, supprimerButton);
        card.getChildren().addAll(nomLabel, descriptionLabel, dateDebutLabel, dateFinLabel, buttonsBox);

        activiteGridPane.add(card, col, row);
    }

    @FXML
    void addActivite(ActionEvent event) {
        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() ||
                dateDebutInput.getValue() == null || dateFinInput.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        if (dateDebutInput.getValue().isAfter(dateFinInput.getValue())) {
            showAlert("Erreur", "La date de début doit être avant la date de fin.");
            return;
        }

        try {
            Activite activite = new Activite();
            activite.setNomActivite(nomActiviteInput.getText());
            activite.setDescription(descriptionInput.getText());
            activite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            activite.setDateFinA(Date.valueOf(dateFinInput.getValue()));

            serviceActivite.add(activite);
            loadActivites();
            clearFields();

            showAlert("Succès", "Activité ajoutée avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'ajout de l'activité.");
            e.printStackTrace();
        }
    }

    @FXML
    void updateActivite(ActionEvent event) {
        if (selectedActivite == null) {
            showAlert("Erreur", "Veuillez sélectionner une activité à modifier.");
            return;
        }

        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() ||
                dateDebutInput.getValue() == null || dateFinInput.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        if (dateDebutInput.getValue().isAfter(dateFinInput.getValue())) {
            showAlert("Erreur", "La date de début doit être avant la date de fin.");
            return;
        }

        try {
            selectedActivite.setNomActivite(nomActiviteInput.getText());
            selectedActivite.setDescription(descriptionInput.getText());
            selectedActivite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            selectedActivite.setDateFinA(Date.valueOf(dateFinInput.getValue()));

            serviceActivite.update(selectedActivite, selectedActivite.getIdActivite());
            loadActivites();
            clearFields();
             // Rafraîchir les statistiques
            showAlert("Succès", "Activité mise à jour avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la mise à jour.");
        }
    }

    @FXML
    void deleteActivite(ActionEvent event) {
        if (selectedActivite == null) {
            showAlert("Erreur", "Veuillez sélectionner une activité à supprimer.");
            return;
        }

        serviceActivite.delete(selectedActivite.getIdActivite());
        loadActivites();
         // Rafraîchir les statistiques
        showAlert("Succès", "Activité supprimée avec succès.");
    }



    @FXML
    void trierActivites(ActionEvent event) {
        String criteria = sortCriteriaComboBox.getValue();
        List<Activite> activites = serviceActivite.getAll();

        switch (criteria) {
            case "Nom":
                activites.sort(Comparator.comparing(Activite::getNomActivite));
                break;
            case "Date Début":
                activites.sort(Comparator.comparing(Activite::getDateDebutA));
                break;
            case "Date Fin":
                activites.sort(Comparator.comparing(Activite::getDateFinA));
                break;
        }

        activiteGridPane.getChildren().clear();
        int row = 0, col = 0;
        for (Activite activite : activites) {
            addActiviteCard(activite, row, col);
            col++;
            if (col > 2) { // 3 cartes par ligne
                col = 0;
                row++;
            }
        }
    }

    @FXML
    void refreshActivites(ActionEvent event) {
        loadActivites();
        searchInput.clear();
    }

    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tools1.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) activiteGridPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("TravelPro");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger l'interface d'accueil.");
        }
    }

    private void modifierActivite(Activite activite) {
        nomActiviteInput.setText(activite.getNomActivite());
        descriptionInput.setText(activite.getDescription());
        dateDebutInput.setValue(activite.getDateDebutA().toLocalDate());
        dateFinInput.setValue(activite.getDateFinA().toLocalDate());
        selectedActivite = activite;
    }

    private void supprimerActivite(Activite activite) {
        serviceActivite.delete(activite.getIdActivite());
        loadActivites();

        showAlert("Succès", "Activité supprimée avec succès.");
    }

    private void clearFields() {
        nomActiviteInput.clear();
        descriptionInput.clear();
        dateDebutInput.setValue(null);
        dateFinInput.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void ouvrirStatistiques(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/statistiques.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Statistiques des Activités");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la fenêtre des statistiques.");
        }
    }
    private void setupDynamicSearch() {
        // Créer une ObservableList à partir de la liste des événements
        ObservableList<Activite> observableList = FXCollections.observableArrayList(serviceActivite.getAll());

        // Créer une FilteredList pour filtrer les événements
        FilteredList<Activite> filteredData = new FilteredList<>(observableList, b -> true);

        // Lier le champ de recherche à la FilteredList
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(evt -> {
                // Si le champ de recherche est vide, afficher tous les événements
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convertir le texte de recherche en minuscules pour une recherche insensible à la casse
                String lowerCaseFilter = newValue.toLowerCase();

                // Vérifier si le nom ou l'ID de l'événement correspond au texte de recherche
                return evt.getNomActivite().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(evt.getDescription()).contains(lowerCaseFilter);
            });

            // Mettre à jour l'affichage avec les résultats filtrés
            updateActiviteCards(filteredData);
        });

        // Afficher les événements filtrés
        updateActiviteCards(filteredData);
    }
    private void updateActiviteCards(FilteredList<Activite> filteredData) {
        // Effacer les cartes existantes
        activiteGridPane.getChildren().clear();

        // Ajouter les cartes des activités filtrées
        int row = 0, col = 0;
        for (Activite activite : filteredData) {
            addActiviteCard(activite, row, col);
            col++;
            if (col > 2) { // 3 cartes par ligne
                col = 0;
                row++;
            }
        }
    }





}