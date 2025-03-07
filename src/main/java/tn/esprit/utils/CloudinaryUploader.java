package tn.esprit.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUploader {

    private static final String CLOUD_NAME = "dm4vfjtms";
    private static final String API_KEY = "198957285284643";
    private static final String API_SECRET = "GdhXq6MoND8oudSnGvMVibJ3XFU";

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", CLOUD_NAME,
            "api_key", API_KEY,
            "api_secret", API_SECRET));

    public static String uploadFile(File file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url"); // URL publique de l'image
    }
}
