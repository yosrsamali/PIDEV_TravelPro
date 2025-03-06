package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Reservation;
import tn.esprit.models.Voiture;
import tn.esprit.models.BilletAvion;
import tn.esprit.models.Hotel;
import tn.esprit.services.ServiceReservation;
import tn.esprit.services.ServiceVoiture;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.services.ServiceHotel;

public class ModifierReservationController {

    @FXML
    private Label lblIdVoiture;
    @FXML
    private Label lblIdBilletAvion;
    @FXML
    private Label lblIdHotel;
    @FXML
    private Label lblIdClient;
    @FXML
    private TextField tfStatut;

    private Reservation reservation;
    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ServiceVoiture serviceVoiture = new ServiceVoiture();
    private final ServiceBilletAvion serviceBilletAvion = new ServiceBilletAvion();
    private final ServiceHotel serviceHotel = new ServiceHotel();

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        displayReservationDetails();
    }

    private void displayReservationDetails() {
        // Détails de la voiture
        if (reservation.getId_voiture() == 0) {
            lblIdVoiture.setText("Non spécifié");
        } else {
            Voiture voiture = serviceVoiture.getById(reservation.getId_voiture());
            lblIdVoiture.setText(voiture != null ? voiture.getMarque() + " " + voiture.getModele() : "Inconnu");
        }

        // Détails du billet avion
        if (reservation.getId_billetAvion() == 0) {
            lblIdBilletAvion.setText("Non spécifié");
        } else {
            BilletAvion billet = serviceBilletAvion.getById(reservation.getId_billetAvion());
            lblIdBilletAvion.setText(billet != null ? billet.getCompagnie() + " (" + billet.getVilleDepart() + " -> " + billet.getVilleArrivee() + ")" : "Inconnu");
        }

        // Détails de l'hôtel
        if (reservation.getId_hotel() == 0) {
            lblIdHotel.setText("Non spécifié");
        } else {
            Hotel hotel = serviceHotel.getById(reservation.getId_hotel());
            lblIdHotel.setText(hotel != null ? hotel.toString() : "Inconnu");
        }

        // Détails du client
        lblIdClient.setText("ID " + reservation.getId_client());

        // Statut (modifiable)
        tfStatut.setText(reservation.getStatut());
    }

    @FXML
    private void modifierReservation() {
        if (reservation == null) {
            System.out.println("Erreur : Aucune réservation sélectionnée.");
            return;
        }

        String statut = tfStatut.getText();
        if (statut == null || statut.isEmpty()) {
            System.out.println("Erreur : Le statut est vide.");
            return;
        }

        reservation.setStatut(statut);
        serviceReservation.update(reservation);

        // Envoyer un e-mail en fonction du statut
        String clientEmail = "yassin.abida@esprit.tn"; // Remplacez par l'e-mail du client
        if (clientEmail == null || clientEmail.isEmpty()) {
            System.out.println("Erreur : L'e-mail du client est vide.");
            return;
        }

        String subject;
        String htmlContent;

        if (statut.equalsIgnoreCase("Confirmé")) {
            subject = "Confirmation de votre réservation";
            htmlContent = createConfirmationEmail(reservation);
        } else if (statut.equalsIgnoreCase("Annulé")) {
            subject = "Annulation de votre réservation";
            htmlContent = createCancellationEmail(reservation);
        } else {
            System.out.println("Statut inconnu : " + statut);
            return;
        }

        // Envoyer l'e-mail
        EmailService emailService = new EmailService("yassin.abida00@gmail.com", "kbng hjxw ijwr zfhr");
        emailService.sendEmail(clientEmail, subject, htmlContent);

        // Fermer la fenêtre
        Stage stage = (Stage) tfStatut.getScene().getWindow();
        stage.close();
    }
    private String createConfirmationEmail(Reservation reservation) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Confirmation de réservation</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 0; }\n" +
                "        .container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }\n" +
                "        h1 { color: #007BFF; text-align: center; }\n" +
                "        .welcome-message { font-size: 18px; margin-bottom: 20px; }\n" +
                "        .details { margin-bottom: 20px; }\n" +
                "        .details h2 { color: #007BFF; margin-bottom: 10px; }\n" +
                "        .details p { margin: 5px 0; }\n" +
                "        .footer { text-align: center; margin-top: 20px; font-size: 14px; color: #777; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Confirmation de réservation</h1>\n" +
                "        <div class=\"welcome-message\">\n" +
                "            Bonjour,<br>\n" +
                "            Votre réservation a été confirmée avec succès. Voici les détails :\n" +
                "        </div>\n" +
                "        <div class=\"details\">\n" +
                "            <h2>Détails de la réservation</h2>\n" +
                "            <p><strong>Voiture :</strong> " + lblIdVoiture.getText() + "</p>\n" +
                "            <p><strong>Billet d'avion :</strong> " + lblIdBilletAvion.getText() + "</p>\n" +
                "            <p><strong>Hôtel :</strong> " + lblIdHotel.getText() + "</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Merci de nous faire confiance. Pour toute question, contactez-nous à <a href=\"mailto:support@travelpro.com\">support@travelpro.com</a>.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    private String createCancellationEmail(Reservation reservation) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Annulation de réservation</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; margin: 0; padding: 0; }\n" +
                "        .container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }\n" +
                "        h1 { color: #FF0000; text-align: center; }\n" +
                "        .welcome-message { font-size: 18px; margin-bottom: 20px; }\n" +
                "        .details { margin-bottom: 20px; }\n" +
                "        .details h2 { color: #FF0000; margin-bottom: 10px; }\n" +
                "        .details p { margin: 5px 0; }\n" +
                "        .footer { text-align: center; margin-top: 20px; font-size: 14px; color: #777; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Annulation de réservation</h1>\n" +
                "        <div class=\"welcome-message\">\n" +
                "            Bonjour,<br>\n" +
                "            Nous vous informons que votre réservation a été annulée. Voici les détails :\n" +
                "        </div>\n" +
                "        <div class=\"details\">\n" +
                "            <h2>Détails de la réservation</h2>\n" +
                "            <p><strong>Voiture :</strong> " + lblIdVoiture.getText() + "</p>\n" +
                "            <p><strong>Billet d'avion :</strong> " + lblIdBilletAvion.getText() + "</p>\n" +
                "            <p><strong>Hôtel :</strong> " + lblIdHotel.getText() + "</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Pour toute question, contactez-nous à <a href=\"mailto:support@travelpro.com\">support@travelpro.com</a>.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}