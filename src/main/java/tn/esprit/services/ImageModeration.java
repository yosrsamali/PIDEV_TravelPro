package tn.esprit.services;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class ImageModeration {
    public static void main(String[] args) throws Exception {
        String apiKey = "AIzaSyCAgTmOrjn55W8U10JjgqV7DZ_envkySKM"; // Remplacez par votre clé API
        String imageUrl = "https://example.com/image.jpg"; // URL de l'image à tester

        URL url = new URL("https://vision.googleapis.com/v1/images:annotate?key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Corps de la requête JSON
        String jsonInput = "{ \"requests\": [ { \"features\": [ { \"type\": \"SAFE_SEARCH_DETECTION\" } ], \"image\": { \"source\": { \"imageUri\": \"" + imageUrl + "\" } } } ] }";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Lire la réponse de l'API
        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        // Analyse de la réponse JSON
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray responses = jsonResponse.getJSONArray("responses");
        JSONObject safeSearch = responses.getJSONObject(0).getJSONObject("safeSearchAnnotation");

        // Résultats de l'analyse
        System.out.println("Violence: " + safeSearch.getString("violence"));
        System.out.println("Pornographie: " + safeSearch.getString("adult"));
        System.out.println("Contenu médical: " + safeSearch.getString("medical"));
        System.out.println("Contenu inapproprié: " + safeSearch.getString("spoof"));
    }
}
