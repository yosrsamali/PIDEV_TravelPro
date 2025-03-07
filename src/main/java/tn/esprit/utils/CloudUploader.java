package tn.esprit.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudUploader {

    private static final String BUCKET_NAME = "travelpro_94dd1.appspot.com"; // Vérifie sur Google Cloud Storage
    private static final String CREDENTIALS_PATH = "/serviceAccountKey.json"; // Assure-toi qu'il est dans src/main/resources

    public static String uploadFile(File file) throws IOException {
        // Charger les credentials
        InputStream serviceAccount = CloudUploader.class.getResourceAsStream(CREDENTIALS_PATH);

        if (serviceAccount == null) {
            throw new RuntimeException("❌ ERREUR : Le fichier " + CREDENTIALS_PATH + " est introuvable !");
        }

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();

        // Vérifier que le bucket existe
        Bucket bucket = storage.get(BUCKET_NAME);
        if (bucket == null) {
            throw new RuntimeException("❌ ERREUR : Le bucket '" + BUCKET_NAME + "' est introuvable !");
        }

        // Nom du fichier dans Firebase Storage
        String fileName = "images/" + file.getName();

        // Upload du fichier
        Blob blob = bucket.create(fileName, Files.readAllBytes(file.toPath()));

        // Retourner l'URL publique du fichier
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName;
    }
}
