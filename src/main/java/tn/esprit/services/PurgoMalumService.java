package tn.esprit.services; // Ajoutez cette ligne

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class PurgoMalumService {

    // Méthode pour filtrer les mots inappropriés
    public static CompletableFuture<String> filtrerCommentaire(String commentaire) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        // Encoder le commentaire pour l'URL
        String commentaireEncode = URLEncoder.encode(commentaire, StandardCharsets.UTF_8);

        // URL de l'API PurgoMalum
        String url = "http://www.purgomalum.com/service/json?text=" + commentaireEncode;

        // Envoyer la requête GET
        return client.prepare("GET", url)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    if (response.getStatusCode() == 200) {
                        // Extraire le commentaire filtré de la réponse JSON
                        String responseBody = response.getResponseBody();
                        String commentaireFiltre = extraireCommentaireFiltre(responseBody);
                        return commentaireFiltre;
                    } else {
                        throw new RuntimeException("Erreur API : " + response.getStatusCode());
                    }
                })
                .exceptionally(ex -> {
                    // Gérer les exceptions (comme IOException)
                    throw new RuntimeException("Erreur lors de l'appel à l'API PurgoMalum : " + ex.getMessage(), ex);
                })
                .whenComplete((result, ex) -> {
                    try {
                        client.close(); // Fermer le client après utilisation
                    } catch (Exception e) {
                        System.err.println("Erreur lors de la fermeture du client : " + e.getMessage());
                    }
                });
    }

    // Méthode pour extraire le commentaire filtré de la réponse JSON
    private static String extraireCommentaireFiltre(String jsonResponse) {
        // Exemple de réponse JSON : {"result":"Ceci est un commentaire avec des *** !"}
        int startIndex = jsonResponse.indexOf("\"result\":\"") + 10;
        int endIndex = jsonResponse.lastIndexOf("\"");
        return jsonResponse.substring(startIndex, endIndex);
    }
}