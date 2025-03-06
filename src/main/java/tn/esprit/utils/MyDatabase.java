<<<<<<< HEAD
=======
<<<<<<< HEAD

=======
>>>>>>> service_financier
>>>>>>> d3a237b0cedf5db29bbc82b99157c53cf558ac15
package tn.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
<<<<<<< HEAD
    private final String URL ="jdbc:mysql://127.0.0.1:3306/travelpro";
    private final String USERNAME ="root";
    private final String PASSWORD = "";
    private Connection  cnx ;

    private MyDatabase() {
        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);
=======
    private final String URL = "jdbc:mysql://127.0.0.1:3306/travelpro2";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private Connection cnx;

    private MyDatabase() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
>>>>>>> d3a237b0cedf5db29bbc82b99157c53cf558ac15
            System.out.println("connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

<<<<<<< HEAD

=======
>>>>>>> d3a237b0cedf5db29bbc82b99157c53cf558ac15
    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getCnx() {
<<<<<<< HEAD
        return cnx;
    }
}
=======
        // Vérifier si la connexion est fermée, et la rouvrir si nécessaire
        try {
            if (cnx == null || cnx.isClosed()) {
                cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connexion réouverte...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}
>>>>>>> d3a237b0cedf5db29bbc82b99157c53cf558ac15
