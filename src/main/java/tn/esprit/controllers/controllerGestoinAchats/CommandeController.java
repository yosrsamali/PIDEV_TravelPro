package tn.esprit.controllers.controllerGestoinAchats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Commande;
import tn.esprit.services.ServiceCommande;

import java.io.IOException;
import java.util.List;

public class CommandeController {

    @FXML
    private Button btnGoToAdminMainInterface;
    @FXML
    private FlowPane commandCards;  // FlowPane to display the cards

    private final ServiceCommande serviceCommand = new ServiceCommande();

    @FXML
    public void initialize() {
        btnGoToAdminMainInterface.setOnAction(event -> switchScene("InterfacesGestionAchat/Admin_Main_Interface.fxml"));
        loadCommands(); // Call the method correctly
    }

    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) btnGoToAdminMainInterface.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCommands() {
        List<Commande> commands = serviceCommand.getAll(); // ✅ No static call
        for (Commande command : commands) {
            HBox card = createCard(command);
            commandCards.getChildren().add(card);
        }
    }

    private HBox createCard(Commande command) {
        HBox card = new HBox(20);
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");

        Label clientIdLabel = new Label("Client ID: " + command.getIdClient());
        Label totalLabel = new Label("Montant Total: " + command.getMontantTotal());
        Label dateLabel = new Label("Date: " + command.getDateCommande());
        Label statusLabel = new Label("Statut: " + command.getStatus());

        card.getChildren().addAll(clientIdLabel, totalLabel, dateLabel, statusLabel);
        return card;
    }
}
