package tn.esprit.controller.controllerevenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.models.evenement;
import tn.esprit.services.ServiceEvenement;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class evenementC implements Initializable {

    @FXML
    private VBox ParticipantsPage;
    @FXML
    private Pane AddEventPage;
    @FXML
    private VBox eventInterfaces;
    @FXML
    private Button UpdateEvent;
    @FXML
    private Pane UpdateEventPage;
    @FXML
    private VBox AvisPage;
    @FXML
    private VBox EventsInterface;
    @FXML
    private DatePicker dateEvent;
    @FXML
    private DatePicker dateEventInput;
    @FXML
    private TextField LieuIEventnput;
    @FXML
    private TextField ImageEventInput;
    @FXML
    private TextField nomEventIInput;
    @FXML
    private TextField TypeEventInput;
    @FXML
    private TextField nomEventIInput2;
    @FXML
    private TextField      TypeEventInput2;
    @FXML
    private TextField  LieuIEventnput2;
    @FXML
    private TextField      ImageEventInput2;
    @FXML
    private VBox eventsVBox;
    @FXML
    private VBox eventsVBox2;
    @FXML
    private TextField searchInput;
    @FXML
    private TextField filterInput;
    @FXML
    private TextField locationField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField typeEvent1;
    ServiceEvenement e=new ServiceEvenement();
    public  static  int i;
    List<evenement> evennementsList = e.getAll();
    ObservableList<evenement> evennements = FXCollections.observableArrayList(evennementsList);
    @FXML
    void GoToAddEventPage(ActionEvent event) {
        System.out.println("Ajout d'un événement...");
        AddEventPage.setVisible(true);
        EventsInterface.setVisible(false);
        ParticipantsPage.setVisible(false);
        AvisPage.setVisible(false);
        UpdateEventPage.setVisible(false);
    }


    @FXML
    void GoToEventPagee(ActionEvent event) {
        AddEventPage.setVisible(false);
        EventsInterface.setVisible(true);
        ParticipantsPage.setVisible(false);
        AvisPage.setVisible(false);
        UpdateEventPage.setVisible(false);
    }
    @FXML
    void goToAvisPage(ActionEvent event) {
        AddEventPage.setVisible(false);
        EventsInterface.setVisible(false);
        ParticipantsPage.setVisible(false);
        AvisPage.setVisible(true);
        UpdateEventPage.setVisible(false);
    }

    @FXML
    void goToParticipantPage(ActionEvent event) {
        AddEventPage.setVisible(false);
        EventsInterface.setVisible(false);
        ParticipantsPage.setVisible(true);
        AvisPage.setVisible(false);
        UpdateEventPage.setVisible(false);
    }

    @FXML
    void map(ActionEvent event) {

    }

    @FXML
    void GoToEventPage(ActionEvent event) {
        AddEventPage.setVisible(false);
        eventInterfaces.setVisible(true);
    }

    public void data() {
        eventsVBox.getChildren().clear(); // Clear previous items

        List<evenement> evennementsList = e.getAll(); // Fetch events from database
        ObservableList<evenement> observableList = FXCollections.observableArrayList(evennementsList);

        // Filtering (by default, all elements are visible)
        FilteredList<evenement> filteredData = new FilteredList<>(observableList, b -> true);

        // Sorting list
        SortedList<evenement> sortedList = new SortedList<>(filteredData);

        // Add event cards dynamically inside ListContainer
        for (evenement event : sortedList) {
            VBox eventCard = createEventCard(event);
            eventsVBox.getChildren().add(eventCard);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data();
        // Set the default page visible at startup
        setupDynamicSearch();
        EventsInterface.setVisible(true);
        AddEventPage.setVisible(false);
        ParticipantsPage.setVisible(false);
        AvisPage.setVisible(false);
        UpdateEventPage.setVisible(false);

        System.out.println("Application started: EventsInterface is set to visible");
    }



    private VBox createEventCard(evenement event) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #F4F4F4; -fx-padding: 10px; -fx-border-radius: 10px; -fx-border-color: #CCC;");
        card.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Nom: " + event.getNomEvent());
        nameLabel.setStyle("-fx-font-size: 14px;");

        Label locationLabel = new Label("Lieu: " + event.getLieu());
        locationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label dateLabel = new Label("Du " + event.getDateDebutE().toString() + " au " + event.getDateFinE().toString());
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label typeLabel = new Label("Type: " + event.getType());
        typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        ImageView imageView = new ImageView();
        if (event.getImage() != null && !event.getImage().isEmpty()) {
            try {
                File file = new File(event.getImage());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                } else {
                    System.out.println("⚠ Fichier image introuvable: " + event.getImage());
                }
            } catch (Exception ex) {
                System.out.println("⚠ Erreur lors du chargement de l'image: " + event.getImage());
                ex.printStackTrace();
            }
        } else {
            System.out.println("⚠ L'URL de l'image est vide pour l'événement ID: " + event.getIdEvent());
        }

        imageView.setFitWidth(80);
        imageView.setFitHeight(50);

        // Bouton "Voir la carte"
        Button viewMapButton = new Button("Voir la carte");
        viewMapButton.getStyleClass().add("button"); // Appliquer la classe CSS
        viewMapButton.setOnAction(e -> openMapWindow(event.getLatitude(), event.getLongitude()));

        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER_LEFT);

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("button"); // Appliquer la classe CSS
        deleteButton.setOnAction(e -> {
            this.e.delete(event.getIdEvent());
            Notifications.create()
                    .title("Événement supprimé")
                    .text("L'événement a été supprimé avec succès")
                    .position(Pos.BOTTOM_RIGHT)
                    .showConfirm();
            data(); // Rafraîchir la liste après la suppression
        });

        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("button"); // Appliquer la classe CSS
        editButton.setOnAction(e -> {
            System.out.println("Editing event: " + event.getIdEvent());
            nomEventIInput.setText(event.getNomEvent());
            TypeEventInput.setText(event.getType());
            LieuIEventnput.setText(event.getLieu());
            dateEventInput.setValue(event.getDateDebutE().toLocalDate());
            dateEvent.setValue(event.getDateFinE().toLocalDate());
            ImageEventInput.setText(event.getImage());
            i = event.getIdEvent();
            EventsInterface.setVisible(false);
            AddEventPage.setVisible(false);
            ParticipantsPage.setVisible(false);
            AvisPage.setVisible(false);
            UpdateEventPage.setVisible(true);
        });

        buttonsContainer.getChildren().addAll(editButton, deleteButton, viewMapButton);

        card.getChildren().addAll(nameLabel, locationLabel, dateLabel, typeLabel, imageView, buttonsContainer);
        return card;
    }


    Callback<TableColumn<evenement, Void>, TableCell<evenement, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<evenement, Void>, TableCell<evenement, Void>>() {
            @Override
            public TableCell<evenement, Void> call(final TableColumn<evenement, Void> param) {
                return new TableCell<evenement, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            evenement evennement = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + evennement.getIdEvent());
                            e.delete(evennement.getIdEvent());
                            Notifications notificationBuilder = Notifications.create()
                                    .title("Evenement supprimé ")
                                    .text("votre Evenement a été supprimé avec succes")
                                    .graphic(null)

                                    .position(Pos.BOTTOM_RIGHT)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("supprimé avec succes");
                                        }
                                    });
                            notificationBuilder.showConfirm();
                            data();


                        });




                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set buttons as graphic in the cell
                            setGraphic(createButtonPane());
                        }
                    }

                    private Button createButton(String buttonText) {
                        Button button = new Button(buttonText);
                        button.setMinSize(60, 20);
                        return button;
                    }

                    private HBox createButtonPane() {
                        HBox buttonPane = new HBox(5); // spacing between buttons
                        buttonPane.getChildren().addAll(deleteButton, editButton);
                        return buttonPane;
                    }
                };
            }
        };
    }
    public void addEvent(ActionEvent actionEvent) {
        // idha ken ykhali les champs ferghin
        if (nomEventIInput.getText().isEmpty() || TypeEventInput.getText().isEmpty() || LieuIEventnput.getText().isEmpty() || ImageEventInput.getText().isEmpty() || dateEvent == null  ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");

            alert.showAndWait();
        }
        //idha ken event date tkoun a9al mn date mte3 lyoum
        else if(NoDate())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Oups !");
            alert.setContentText(" Event Date should be greater than today");
            alert.showAndWait();

        }
        // idha ken event name ykoun fih ar9am$
        else if(nomEventIInput.getText().matches(".*\\d.*"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Oups !");
            alert.setContentText(" Event Name shouldn't have numbers ");
            alert.showAndWait();

        }

        else {
            java.sql.Date datee = java.sql.Date.valueOf(dateEvent.getValue());
            java.sql.Date datee2 = java.sql.Date.valueOf(dateEventInput.getValue());
            evenement e1 = new evenement(nomEventIInput.getText(),LieuIEventnput.getText(),datee,datee2, TypeEventInput.getText(),1,ImageEventInput.getText(),Float.parseFloat(latitudeField.getText()),Float.parseFloat(longitudeField.getText()));
            e.add(e1);
            nomEventIInput.clear();
            dateEvent.setValue(null);
            dateEventInput.setValue(null);
            TypeEventInput.clear();
            LieuIEventnput.clear();
            ImageEventInput.clear();
            Notifications notificationBuilder = Notifications.create()
                    .title("Evenement ajouté ")
                    .text("votre Evenement a été ajouté avec succes")
                    .graphic(null)

                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("ajouté avec succes");
                        }
                    });
            notificationBuilder.showConfirm();
            EventsInterface.setVisible(true);
            EventsInterface.setManaged(true);
            data();


            AddEventPage.setVisible(false);
            AddEventPage.setManaged(false);

        }
    }
    public void choisirImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"));

        ImageEventInput.setText(fileChooser.showOpenDialog(null).getAbsolutePath());
    }

    public void choisirImage1(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"));

        ImageEventInput.setText(fileChooser.showOpenDialog(null).getAbsolutePath());
    }
    private boolean NoDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate myDate = dateEvent.getValue();
        int comparisonResult = myDate.compareTo(currentDate);
        boolean test = true;
        if (comparisonResult < 0) {
            // myDate est antérieure à currentDate
            test = true;
        } else if (comparisonResult > 0) {
            // myDate est postérieure à currentDate
            test = false;
        }
        return test;
    }
    public void OnUpdate(ActionEvent actionEvent) {
        // Récupérer l'événement existant pour éviter d'écraser des données non modifiées
        evenement existingEvent = e.getEvenementById(i);
        if (existingEvent == null) {
            System.out.println("❌ Événement non trouvé !");
            return;
        }

        // Récupérer uniquement les valeurs modifiées et garder les anciennes si elles ne sont pas changées
        String updatedNomEvent = !nomEventIInput2.getText().isEmpty() ? nomEventIInput2.getText() : existingEvent.getNomEvent();
        String updatedLieu = !LieuIEventnput2.getText().isEmpty() ? LieuIEventnput2.getText() : existingEvent.getLieu();
        java.sql.Date updatedDateDebutE = (dateEvent.getValue() != null) ? java.sql.Date.valueOf(dateEvent.getValue()) : existingEvent.getDateDebutE();
        java.sql.Date updatedDateFinE = (dateEventInput.getValue() != null) ? java.sql.Date.valueOf(dateEventInput.getValue()) : existingEvent.getDateFinE();
        String updatedType = !TypeEventInput2.getText().isEmpty() ? TypeEventInput2.getText() : existingEvent.getType();
        String updatedImage = !ImageEventInput2.getText().isEmpty() ? ImageEventInput2.getText() : existingEvent.getImage();
        int updatedIdReservation = existingEvent.getIdReservation();

        // Créer un objet avec les valeurs mises à jour
        evenement updatedEvent = new evenement(
                i, updatedNomEvent, updatedLieu, updatedDateDebutE, updatedDateFinE, updatedType, updatedImage, updatedIdReservation
        );

        // Mise à jour en base de données
        e.update(updatedEvent, i);

        // Afficher une notification de succès
        Notifications notificationBuilder = Notifications.create()
                .title("Événement modifié")
                .text("Votre événement a été modifié avec succès")
                .graphic(null)
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> System.out.println("Modification réussie"));

        notificationBuilder.showConfirm();

        // Rafraîchir les données affichées
        data();

        // Afficher la page principale et masquer la page de mise à jour
        EventsInterface.setVisible(true);
        EventsInterface.setManaged(true);
        UpdateEventPage.setVisible(false);
        UpdateEventPage.setManaged(false);
    }


    public void gotoactivitie(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewevenement/tools.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et remplacer le contenu
            dateEvent.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void setupDynamicSearch() {
        // Créer une ObservableList à partir de la liste des événements
        ObservableList<evenement> observableList = FXCollections.observableArrayList(e.getAll());

        // Créer une FilteredList pour filtrer les événements
        FilteredList<evenement> filteredData = new FilteredList<>(observableList, b -> true);

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
                return evt.getNomEvent().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(evt.getIdEvent()).contains(lowerCaseFilter);
            });

            // Mettre à jour l'affichage avec les résultats filtrés
            updateEventCards(filteredData);
        });

        // Afficher les événements filtrés
        updateEventCards(filteredData);
    }
    private void updateEventCards(FilteredList<evenement> filteredData) {
        // Effacer les cartes existantes
        eventsVBox.getChildren().clear();

        // Ajouter les cartes des événements filtrés
        for (evenement event : filteredData) {
            VBox eventCard = createEventCard(event);
            eventsVBox.getChildren().add(eventCard);
        }
    }

    @FXML
    void refreshEvenements(ActionEvent event) {
        searchInput.clear(); // Vider le champ de recherche
        data(); // Recharger toutes les données
    }

    @FXML
    public void handleSearchLocation() {
        String query = locationField.getText();
        if (query.isEmpty()) return;

        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedQuery;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "GestionStade/1.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONArray results = new JSONArray(response.toString());
            if (results.length() > 0) {
                JSONObject firstResult = results.getJSONObject(0);
                double lat = firstResult.getDouble("lat");
                double lon = firstResult.getDouble("lon");

                latitudeField.setText(String.valueOf(lat));
                longitudeField.setText(String.valueOf(lon));
            } else {
                latitudeField.setText("Non trouvé");
                longitudeField.setText("Non trouvé");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openMapWindow(double latitude, double longitude) {
        // Coordonnées de la Tour Eiffel pour tester


        System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);

        try {
            Stage mapStage = new Stage();
            mapStage.setTitle("Carte de l'événement");

            WebView webView = new WebView();
            webView.setPrefSize(600, 400);

            String mapUrl = "https://www.openstreetmap.org/export/embed.html?bbox=" +
                    (longitude - 0.01) + "%2C" + (latitude - 0.01) + "%2C" +
                    (longitude + 0.01) + "%2C" + (latitude + 0.01) + "&amp;layer=mapnik";
            webView.getEngine().load(mapUrl);

            Scene scene = new Scene(new StackPane(webView));
            mapStage.setScene(scene);
            mapStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la carte.");
        }
    }

    private void showAlert(String erreur, String s) {
    }

    @FXML
    void ouvrirStatistiques(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewevenement/statistiquesEvenement.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Statistiques des Événements");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la fenêtre des statistiques.");
        }
    }




}
