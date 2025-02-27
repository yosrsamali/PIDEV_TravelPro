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
    private FlowPane activiteFlowPane;

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
    private Activite selectedActivite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadActivites();
    }

    private void loadActivites() {
        activiteFlowPane.getChildren().clear();
        List<Activite> activites = serviceActivite.getAll();
        if (activites.isEmpty()) {
            showAlert("Information", "Aucune activité trouvée.");
        } else {
            for (Activite activite : activites) {
                addActiviteCard(activite);
            }
        }
    }

    private void addActiviteCard(Activite activite) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

        Label nomLabel = new Label("Nom: " + activite.getNomActivite());
        Label descriptionLabel = new Label("Description: " + activite.getDescription());
        Label dateDebutLabel = new Label("Date Début: " + activite.getDateDebutA());
        Label dateFinLabel = new Label("Date Fin: " + activite.getDateFinA());

        Button modifierButton = new Button("Modifier");
        Button supprimerButton = new Button("Supprimer");

        modifierButton.setOnAction(event -> modifierActivite(activite));
        supprimerButton.setOnAction(event -> supprimerActivite(activite));

        HBox buttonsBox = new HBox(10, modifierButton, supprimerButton);
        card.getChildren().addAll(nomLabel, descriptionLabel, dateDebutLabel, dateFinLabel, buttonsBox);

        activiteFlowPane.getChildren().add(card);
    }

    @FXML
    void addActivite(ActionEvent event) {
        if (nomActiviteInput.getText().isEmpty() || descriptionInput.getText().isEmpty() || dateDebutInput.getValue() == null || dateFinInput.getValue() == null ) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            int idEvent = Integer.parseInt(idEventInput.getText());

            Activite activite = new Activite();
            activite.setNomActivite(nomActiviteInput.getText());
            activite.setDescription(descriptionInput.getText());
            activite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            activite.setDateFinA(Date.valueOf(dateFinInput.getValue()));
           // activite.setIdEvent(idEvent);

            serviceActivite.add(activite);
            loadActivites();

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
            if (dateDebutInput.getValue().isAfter(dateFinInput.getValue())) {
                showAlert("Erreur", "La date de début doit être avant la date de fin.");
                return;
            }

            selectedActivite.setNomActivite(nomActiviteInput.getText());
            selectedActivite.setDescription(descriptionInput.getText());
            selectedActivite.setDateDebutA(Date.valueOf(dateDebutInput.getValue()));
            selectedActivite.setDateFinA(Date.valueOf(dateFinInput.getValue()));
            selectedActivite.setIdEvent(Integer.parseInt(idEventInput.getText()));

            serviceActivite.update(selectedActivite, selectedActivite.getIdActivite());
            loadActivites();

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

        serviceActivite.delete(selectedActivite.getIdActivite());
        loadActivites();
        showAlert("Succès", "Activité supprimée avec succès.");
    }

    private void modifierActivite(Activite activite) {
        nomActiviteInput.setText(activite.getNomActivite());
        descriptionInput.setText(activite.getDescription());
        dateDebutInput.setValue(activite.getDateDebutA().toLocalDate());
        dateFinInput.setValue(activite.getDateFinA().toLocalDate());
        idEventInput.setText(String.valueOf(activite.getIdEvent()));
        selectedActivite = activite;
    }

    private void supprimerActivite(Activite activite) {
        if (activite == null) {
            showAlert("Erreur", "Aucune activité sélectionnée.");
            return;
        }

        serviceActivite.delete(activite.getIdActivite());
        loadActivites();
        showAlert("Succès", "Activité supprimée avec succès.");
    }

    @FXML
    void searchActivite(ActionEvent event) {
        String searchText = searchInput.getText().trim();
        if (searchText.isEmpty()) {
            loadActivites();
            return;
        }

        List<Activite> activites = serviceActivite.getAll();
        activiteFlowPane.getChildren().clear();

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
            loadActivites();
            return;
        }

        List<Activite> activites = serviceActivite.getAll();
        activiteFlowPane.getChildren().clear();

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
        loadActivites();
        searchInput.clear();
        filterInput.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void retourner(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tools1.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) dateDebutInput.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("travelPro");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger l'interface d'ajout.");
        }
    }
}