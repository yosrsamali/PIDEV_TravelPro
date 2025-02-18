package tn.esprit.utils;

import tn.esprit.models.deponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
    private final String URL = "jdbc:mysql://127.0.0.1:3306/travelpro2";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private Connection cnx;

    private MyDatabase() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

    // Ajouter la méthode delete() ici
    public void delete(deponse dep) {
        String query = "DELETE FROM deponse WHERE id_deponse = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setInt(1, dep.getId_deponse());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dépense supprimée: " + dep.getId_deponse());
            } else {
                System.out.println("Aucune dépense trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la dépense : " + e.getMessage());
        }
    }
}
