package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceReservation;

public class GestionReservation {
    @FXML
    private TextField tfIdVoiture;
    @FXML
    private TextField tfIdBilletAvion;
    @FXML
    private TextField tfIdHotel;
    @FXML
    private TextField tfIdClient;
    @FXML
    private TextField tfStatut;

    @FXML
    private Label lbReservations;

    IService<Reservation> sr = new ServiceReservation();

    @FXML
    public void ajouterReservation(ActionEvent actionEvent) {
        Reservation reservation = new Reservation(
                Integer.parseInt(tfIdVoiture.getText()),
                Integer.parseInt(tfIdBilletAvion.getText()),
                Integer.parseInt(tfIdHotel.getText()),
                Integer.parseInt(tfIdClient.getText()),
                tfStatut.getText()
        );

        sr.add(reservation);
    }

    @FXML
    public void afficherReservations(ActionEvent actionEvent) {
        lbReservations.setText(sr.getAll().toString());
    }
}