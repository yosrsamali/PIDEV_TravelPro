package tn.esprit.controllers.controllerGestoinAchats;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Commande;
import tn.esprit.services.ServiceCommande;

import java.io.IOException;
import java.util.List;

public class CommandeController {


    @FXML
    private Button btnGoToAdminMainInterface;
    @FXML
    private TableView<Commande> commandTable;
    @FXML
    private TableColumn<Commande, Integer> idColumn;
    @FXML
    private TableColumn<Commande, Integer> clientIdColumn;
    @FXML
    private TableColumn<Commande,String > dateColumn;
    @FXML
    private TableColumn<Commande, Double> totalColumn;
    @FXML
    private TableColumn<Commande, String> statusColum;


    private final ServiceCommande serviceCommand = new ServiceCommande();


    @FXML
    public void initialize() {
        btnGoToAdminMainInterface.setOnAction(event -> switchScene("Admin_Main_Interface.fxml"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        statusColum.setCellValueFactory(new PropertyValueFactory<>("status"));


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
        ObservableList<Commande> commandList = FXCollections.observableArrayList(commands);
        commandTable.setItems(commandList);
    }
}