package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

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
    private DatePicker dateEvent1;

    @FXML
    private DatePicker dateEvent2;

    @FXML
    private TextArea desEvent1;



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
    private TextField imageEvent1;

    @FXML
    private TextField localisation;

    @FXML
    private TextField localisation1;

    @FXML
    private TextField nomEvent;

    @FXML
    private TextField nomEvent1;

    @FXML
    private TableView<?> participantsTableView;

    @FXML
    private TextField prix;

    @FXML
    private TextField prix1;

    @FXML
    private TextField typeEvent1;

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
    void OnUpdate(ActionEvent event) {

    }

    @FXML
    void addEvent(ActionEvent event) {

    }

    @FXML
    void choisirImage1(ActionEvent event) {

    }



    @FXML
    void map(ActionEvent event) {

    }

    @FXML
    void GoToEventPage(ActionEvent event) {
        AddEventPage.setVisible(false);
        eventInterfaces.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the default page visible at startup
        EventsInterface.setVisible(true);
        AddEventPage.setVisible(false);
        ParticipantsPage.setVisible(false);
        AvisPage.setVisible(false);
        UpdateEventPage.setVisible(false);

        System.out.println("Application started: EventsInterface is set to visible");
    }

}
