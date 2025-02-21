package tn.esprit.services;

import tn.esprit.models.Client;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient {
    private Connection cnx;

    public ServiceClient() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // 🔹 Add Client
    public void add(Client client) {
        String query = "INSERT INTO client (addresse_client, num_tel_client) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, client.getAdresseClient());
            pstmt.setString(2, client.getNumTelClient());
            pstmt.executeUpdate();
            System.out.println("Client added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Delete Client
    public void delete(Client client) {
        String query = "DELETE FROM client WHERE id_client = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, client.getIdClient());
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client deleted successfully!");
            } else {
                System.out.println("Client not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Update Client
    public void update(Client client) {
        String query = "UPDATE client SET addresse_client = ?, num_tel_client = ? WHERE id_client = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, client.getAdresseClient());
            pstmt.setString(2, client.getNumTelClient());
            pstmt.setInt(3, client.getIdClient());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client updated successfully!");
            } else {
                System.out.println("Client not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Get All Clients
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("addresse_client"),
                        rs.getString("num_tel_client")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
