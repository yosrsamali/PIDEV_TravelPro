package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import tn.esprit.models.evenement;
import tn.esprit.services.ServiceEvenement;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;

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
    private Button AddProductBtn;

    @FXML
    private VBox AvisPage;

    @FXML
    private TableView<?> AvisTableView;

    @FXML
    private VBox EventsInterface;

    @FXML
    private Pane ListContainer;

    @FXML
    private Pane ListContainer1;

    @FXML
    private Pane ListContainer11;


    @FXML
    private Pane UpperSection;

    @FXML
    private Pane UpperSection1;

    @FXML
    private Pane UpperSection11;

    @FXML
    private TableColumn<?, ?> actionsColumnA;

    @FXML
    private TableColumn<?, ?> dateColumnP;

    @FXML
    private DatePicker dateEvent;

    @FXML
    private DatePicker dateEventInput;



    @FXML
    private TextArea desEvent1;


    // table view Evennement
    @FXML
    TableView<evenement>evennementTableView;
    @FXML
    TableColumn<evenement, Integer> idColumn;
    @FXML
    TableColumn<evenement, String> nomColumn;
    @FXML
    TableColumn<evenement, String> lieuColumn;
    @FXML
    TableColumn<evenement, Date> dateDColumn;
    @FXML
    TableColumn<evenement, Date> dateFColumn;
    @FXML
    TableColumn<evenement, String> typeColumn;
    @FXML
    TableColumn<evenement, String> imageColumn;
    @FXML
    TableColumn<evenement,Void> actionsColumn;
    @FXML
    private TableColumn<?, ?> idAvisColumn;

    @FXML
    private TableColumn<?, ?> idColumnParticipants;

    @FXML
    private TableColumn<?, ?> idcommentaireColumn;

    @FXML
    private TableColumn<?, ?> ideventcolumnn;

    @FXML
    private TableColumn<?, ?> ideventcolumnnA;

    @FXML
    private TableColumn<?, ?> iduserrColumn;

    @FXML
    private TableColumn<?, ?> iduserrColumnA;
    @FXML
    private Button AddImageBtn;
    @FXML
    private TextField imageEvent1;
    @FXML
    private TextField imageEvent;
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
    private DatePicker dateEvent2;
    @FXML
    private DatePicker dateEventInput2;

    @FXML
    private TextField      TypeEventInput2;
    @FXML
    private TextField  LieuIEventnput2;
    @FXML
    private TextField      ImageEventInput2;
    //@FXML
    //private TableView<?> participantsTableView;


    @FXML
    private VBox eventsVBox;
    @FXML
    private VBox eventsVBox2;


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

    /*public void data()
    {
        //intialisation table view evennement
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        dateDColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebutE"));
        dateFColumn.setCellValueFactory(new PropertyValueFactory<>("dateFinE"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        actionsColumn.setCellFactory(createButtonCellFactory());
        ListContainer.setItems(FXCollections.observableArrayList( e.getAll()));
        ObservableList<evenement> oList = FXCollections.observableArrayList(e.getAll());
        FilteredList<evenement> filteredData = new FilteredList<evenement>(oList, b -> true);

        SortedList<evenement> sortedList = new SortedList <evenement>(filteredData);
        sortedList.comparatorProperty().bind(evennementTableView.comparatorProperty())    ;
        evennementTableView.setItems(sortedList);
    }*/
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

        Label idLabel = new Label("ID: " + event.getIdEvent());
        idLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Nom: " + event.getNomEvent());
        nameLabel.setStyle("-fx-font-size: 14px;");

        Label locationLabel = new Label("Lieu: " + event.getLieu());
        locationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label dateLabel = new Label("Du " + event.getDateDebutE().toString() + " au " + event.getDateFinE().toString());
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label typeLabel = new Label("Type: " + event.getType());
        typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        System.out.println(event.getImage());
        ImageView imageView = new ImageView();
        if (event.getImage() != null && !event.getImage().isEmpty()) {
            try {
                File file = new File(event.getImage());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString()); // Convertir chemin local en URL valide
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

        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER_LEFT);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            this.e.delete(event.getIdEvent());
            Notifications.create()
                    .title("Événement supprimé")
                    .text("L'événement a été supprimé avec succès")
                    .position(Pos.BOTTOM_RIGHT)
                    .showConfirm();
            data(); // Refresh list after deletion
        });

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            System.out.println("Editing event: " + event.getIdEvent());
            nomEventIInput.setText(event.getNomEvent());
            TypeEventInput.setText(event.getType());
            LieuIEventnput.setText(event.getLieu());
            dateEventInput.setValue(event.getDateDebutE().toLocalDate());
            dateEvent.setValue(event.getDateFinE().toLocalDate());
            ImageEventInput.setText(event.getImage());
            i=event.getIdEvent();
            EventsInterface.setVisible(false);
            AddEventPage.setVisible(false);
            ParticipantsPage.setVisible(false);
            AvisPage.setVisible(false);
            UpdateEventPage.setVisible(true);
        });


        buttonsContainer.getChildren().addAll(editButton,deleteButton);

        card.getChildren().addAll(idLabel, nameLabel, locationLabel, dateLabel, typeLabel, imageView, buttonsContainer);
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

                      /*  editButton.setOnAction(event -> {
                            evenement evennement = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + evennement.getIdEvent());
                            EventsInterface.setVisible(false);
                            EventsInterface.setManaged(false);
                            AddEventPage.setVisible(false);
                            AddEventPage.setManaged(false);
                            ParticipantsPage.setVisible(false);
                            ParticipantsPage.setManaged(false);
                            AvisPage.setVisible(false);
                            AvisPage.setManaged(false);


                            UpdateEventPage.setVisible(true);
                            UpdateEventPage.setManaged(true);
                            i=evennement.getIdEvent();

                            nomEvent1.setText(evennement.getNomEvent());
                            typeEvent1.setText(evennement.getType());
                            desEvent1.setText(evennement.getDescription());
                            LocalDate date=evennement.getDate().toLocalDate();
                            dateEvent1.setValue(date);
                            imageEvent1.setText(evennement.getImage());
                            prix1.setText(String.valueOf(evennement.getPrix()));
                            localisation1.setText(evennement.getLocalisation());

                        });*/


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
            evenement e1 = new evenement(nomEventIInput.getText(),LieuIEventnput.getText(),datee,datee2, TypeEventInput.getText(),1,ImageEventInput.getText());
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
        java.sql.Date datee = java.sql.Date.valueOf(dateEvent.getValue());
        java.sql.Date datee2 = java.sql.Date.valueOf(dateEventInput.getValue());
        System.out.println("edittttt : "+i);

        evenement e2=new evenement(i,nomEventIInput2.getText(),LieuIEventnput2.getText(),datee,datee2, TypeEventInput2.getText(),ImageEventInput2.getText(),1);
        System.out.println(e2);
        System.out.println(i);
        e.update(e2,i);
        nomEventIInput2.clear();
        dateEvent2.setValue(null);
        dateEventInput2.setValue(null);
        TypeEventInput2.clear();
        LieuIEventnput2.clear();
        ImageEventInput2.clear();


        Notifications notificationBuilder = Notifications.create()
                .title("Evenement modifié ")
                .text("votre Evenement a été modifié avec succes")
                .graphic(null)

                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("modifié avec succes");
                    }
                });
        notificationBuilder.showConfirm();
        EventsInterface.setVisible(true);
        EventsInterface.setManaged(true);
        data();


        UpdateEventPage.setVisible(false);
        UpdateEventPage.setManaged(false);


    }
}
