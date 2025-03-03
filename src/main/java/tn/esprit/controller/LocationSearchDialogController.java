package tn.esprit.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class LocationSearchDialogController {

    @FXML
    private TextField addressField;

    @FXML
    private ListView<String> locationList;

    private String selectedLocation;

    @FXML
    public void initialize() {
        // Add a listener to the TextField to fetch suggestions in real-time
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 3) { // Trigger API call only after 3 characters are typed
                fetchLocationSuggestions(newValue);
            } else {
                locationList.getItems().clear(); // Clear the list if the input is too short
            }
        });

        // Handle selection from the ListView
        locationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedLocation = newValue; // Store the selected location
            }
        });
    }

    @FXML
    void handleConfirm() {
        // Close the dialog and return the selected location
        Stage stage = (Stage) locationList.getScene().getWindow();
        stage.close();
    }

    private void fetchLocationSuggestions(String query) {
        List<String> suggestions = getLocationSuggestions(query);
        if (!suggestions.isEmpty()) {
            locationList.getItems().setAll(suggestions); // Update the ListView with new suggestions
        } else {
            locationList.getItems().setAll("No results found."); // Show a message if no results are found
        }
    }

    private List<String> getLocationSuggestions(String query) {
        List<String> suggestions = new ArrayList<>();
        try {
            // Encode the query for the API request
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedQuery;

            // Make the API request
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "YourAppName/1.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONArray results = new JSONArray(response.toString());
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String displayName = result.getString("display_name");
                double lat = result.getDouble("lat");
                double lon = result.getDouble("lon");
                suggestions.add(String.format("%s (Lat: %.6f, Long: %.6f)", displayName, lat, lon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suggestions;
    }

    public String getSelectedLocation() {
        return selectedLocation;
    }
}