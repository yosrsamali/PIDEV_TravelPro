/*package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Mainfx extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tools1.fxml"));

        Parent root = loader.load();
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.setTitle("Ajouter");
        stage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }
}*/
package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class Mainfx extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tools1.fxml"));
        Parent root = loader.load();

        // Set scene size
        double width = 1200; // Adjust as needed
        double height = 800; // Adjust as needed
        Scene scene = new Scene(root, width, height);

        stage.setScene(scene);
        stage.setTitle("TravelPro");

        // Center the window
        centerStage(stage, width, height);

        stage.show();
    }

    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
