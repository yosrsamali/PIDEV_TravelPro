package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.services.ServiceActivite;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class activiteC implements Initializable {

    @FXML
    private FlowPane activiteFlowPane; // Remplacement de TableView par FlowPane

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
    private Activite selectedActivite; // Pour stocker l'activité sélectionnée

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadActivites(); // Charger les cartes au démarrage
    }

    // Méthode pour charger les activités depuis la base de données
    private void loadActivites() {
        activiteFlowPane.getChildren().clear(); // Vider les cartes existantes
        List<Activite> activites = serviceActivite.getAll();
        if (activites.isEmpty()) {
            showAlert("Information", "Aucune activité trouvée.");
        } else {
            for (Activite activite : activites) {
                addActiviteCard(activite); // Ajouter une carte pour chaque activité
            }
        }
    }

    // Méthode pour ajouter une carte d'activité
    private void addActiviteCard(Activite activite) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

        // Afficher les informations de l'activité
        Label nomLabel = new Label("Nom: " + activite.getNomActivite());
        Label descriptionLabel = new Label("Description: " + activite.getDescription());
        Label dateDebutLabel = new Label("Date Début: " + activite.getDateDebutA());
        Label dateFinLabel = new Label("Date Fin: " + activite.getDateFinA());

        // Ajouter des boutons pour modifier et supprimer
        Button modifierButton = new Button("Modifier");
        Button supprimerButton = new Button("Supprimer");

        // Associer les actions aux boutons
        modifierButton.setOnAction(event -> modifierActivite(activite));
        supprimerButton.setOnAction(event -> supprimerActivite(activite));

        // Ajouter les éléments à la carte
        HBox buttonsBox = new HBox(10, modifierButton, supprimerButton);
        card.getChildren().addAll(nomLabel, descriptionLabel, dateDebutLabel, dateFinLabel, buttonsBox);

        // Ajouter la carte au FlowPane
        activiteFlowPane.getChildren().add(card);
    }

    @FXML
    void addActivite(ActionEvent event) {
        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() || dateDebutInput.getValue() == null || dateFinInput.getValue() == null || idEventInput.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            int idEvent = Integer.parseInt(idEventInput.getText());

            // Vérifier si l'idEvent existe dans la table evenement


            Activite activite = new Activite();
            activite.setNomActivite(nomActiviteInput.getText());
            activite.setDescription(descriptionInput.getText());
            activite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            activite.setDateFinA(Date.valueOf(dateFinInput.getValue()));
            activite.setIdEvent(idEvent);

            serviceActivite.add(activite);
            loadActivites(); // Recharger les cartes après l'ajout

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
        if (selectedActivite == null) {
            showAlert("Erreur", "Veuillez sélectionner une activité à modifier.");
            return;
        }

        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() || dateDebutInput.getValue() == null || dateFinInput.getValue() == null || idEventInput.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            // Vérifier que la date de début est avant la date de fin
            if (dateDebutInput.getValue().isAfter(dateFinInput.getValue())) {
                showAlert("Erreur", "La date de début doit être avant la date de fin.");
                return;
            }

            // Mettre à jour les données de l'activité sélectionnée
            selectedActivite.setNomActivite(nomActiviteInput.getText());
            selectedActivite.setDescription(descriptionInput.getText());
            selectedActivite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            selectedActivite.setDateFinA(Date.valueOf(dateFinInput.getValue()));
            selectedActivite.setIdEvent(Integer.parseInt(idEventInput.getText()));

            // Mettre à jour l'activité dans la base de données
            serviceActivite.update(selectedActivite, selectedActivite.getIdActivite());

            // Recharger les cartes
            loadActivites();

            // Réinitialiser les champs
            nomActiviteInput.clear();
            descriptionInput.clear();
            dateDebutInput.setValue(null);
            dateFinInput.setValue(null);
            idEventInput.clear();

            showAlert("Succès", "Activité mise à jour avec succès.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID de l'événement doit être un nombre.");
        }
    }

    @FXML
    void deleteActivite(ActionEvent event) {
        if (selectedActivite == null) {
            showAlert("Erreur", "Veuillez sélectionner une activité à supprimer.");
            return;
        }

        // Supprimer l'activité de la base de données
        serviceActivite.delete(selectedActivite.getIdActivite());

        // Recharger les cartes
        loadActivites();

        showAlert("Succès", "Activité supprimée avec succès.");
    }

    // Méthode pour pré-remplir les champs du formulaire avec les données de l'activité sélectionnée
    private void modifierActivite(Activite activite) {
        nomActiviteInput.setText(activite.getNomActivite());
        descriptionInput.setText(activite.getDescription());
        dateDebutInput.setValue(activite.getDateDebutA().toLocalDate()); // Conversion correcte
        dateFinInput.setValue(activite.getDateFinA().toLocalDate()); // Conversion correcte
        idEventInput.setText(String.valueOf(activite.getIdEvent()));

        // Stocker l'activité sélectionnée pour la mise à jour
        selectedActivite = activite;
    }

    // Méthode pour supprimer une activité
    private void supprimerActivite(Activite activite) {
        if (activite == null) {
            showAlert("Erreur", "Aucune activité sélectionnée.");
            return;
        }

        // Supprimer l'activité de la base de données
        serviceActivite.delete(activite.getIdActivite());

        // Recharger les cartes
        loadActivites();

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
        activiteFlowPane.getChildren().clear(); // Vider les cartes existantes

        for (Activite activite : activites) {
            if (String.valueOf(activite.getIdActivite()).contains(searchText) ||
                    activite.getNomActivite().toLowerCase().contains(searchText.toLowerCase())) {
                addActiviteCard(activite);
            }
        }
    }

    @FXML
    void filterActivite(ActionEvent event) {
        String filterText = filterInput.getText().trim();
        if (filterText.isEmpty()) {
            loadActivites(); // Recharger toutes les activités si le champ de filtre est vide
            return;
        }

        List<Activite> activites = serviceActivite.getAll();
        activiteFlowPane.getChildren().clear(); // Vider les cartes existantes

        for (Activite activite : activites) {
            if (String.valueOf(activite.getIdActivite()).contains(filterText) ||
                    activite.getNomActivite().toLowerCase().contains(filterText.toLowerCase())) {
                addActiviteCard(activite);
            }
        }
    }

    @FXML
    void sortById(ActionEvent event) {
        List<Activite> activites = serviceActivite.getAll();
        activites.sort(Comparator.comparingInt(Activite::getIdActivite));
        activiteFlowPane.getChildren().clear();
        for (Activite activite : activites) {
            addActiviteCard(activite);
        }
    }

    @FXML
    void sortByName(ActionEvent event) {
        List<Activite> activites = serviceActivite.getAll();
        activites.sort(Comparator.comparing(Activite::getNomActivite));
        activiteFlowPane.getChildren().clear();
        for (Activite activite : activites) {
            addActiviteCard(activite);
        }
    }

    @FXML
    void refreshActivites(ActionEvent event) {
        loadActivites(); // Recharger toutes les cartes
        searchInput.clear(); // Vide le champ de recherche
        filterInput.clear(); // Vide le champ de filtre
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour retourner à l'interface précédente
    public void retourner(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tools1.fxml"));
            Parent root = fxmlLoader.load();

            // Changer la scène de la même fenêtre (Stage)
            Stage stage = (Stage) dateDebutInput.getScene().getWindow(); // Récupérer le Stage actuel
            stage.setScene(new Scene(root)); // Remplacer la scène avec l'interface d'ajout
            stage.setTitle("travelPro"); // Mettre à jour le titre de la fenêtre
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger l'interface d'ajout.");
        }
    }
}