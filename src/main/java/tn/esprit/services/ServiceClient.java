package tn.esprit.services;
<<<<<<< HEAD

import tn.esprit.models.Client;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient {
=======
import java.util.List;
import java.util.*;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Client;
import tn.esprit.utils.MyDatabase;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.SmsService;
import java.sql.*;
import java.util.ArrayList;

public class ServiceClient implements IService<Client> {
>>>>>>> user
    private Connection cnx;

    public ServiceClient() {
        cnx = MyDatabase.getInstance().getCnx();
    }

<<<<<<< HEAD
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
=======
    @Override
    public void add(Client client) {
        String qry = "INSERT INTO `client`(`id_utilisateur`, `num_tel_client`, `addresse_client`) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, client.getId());
            pstm.setString(2, client.getNumTel());
            pstm.setString(3, client.getAdresse());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setIdClient(generatedKeys.getInt(1)); // Récupération de l'ID auto-généré
                }
            }
            System.out.println("Client ajouté avec succès !");
            SmsService.sendSms("+216"+client.getNumTel(), "Votre code de vérification est : " + client.getCodeVerification());

        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'ajout d'un client : " + e.getMessage());
        }
    }


    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String qry = "SELECT * FROM `client`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Client c = new Client();
                c.setIdClient(rs.getInt("id_Client"));
                c.setId(rs.getInt("id_Utilisateur"));
                c.setNumTel(rs.getString("num_Tel"));
                c.setAdresse(rs.getString("adresse"));
                c.setImageUrl(rs.getString("image_url"));
                clients.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return clients;
    }

    @Override
    public void update(Client client) {
        String qry = "UPDATE client SET num_tel_client=?, addresse_client=?,image_url=? WHERE id_client=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, client.getNumTel());
            pstm.setString(2, client.getAdresse());
            pstm.setString(3, client.getImageUrl());

            pstm.setInt(4, client.getIdClient());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client mis à jour !");
            } else {
                System.out.println(" Aucune modification, client non trouvé.");
            }
        } catch (SQLException e) {
            System.out.println(" Erreur lors de la mise à jour du client : " + e.getMessage());
        }
    }

    @Override
    public void delete(Client client) {
        String qry = "DELETE FROM client WHERE id_client=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, client.getIdClient());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Client supprimé !");
            } else {
                System.out.println("⚠️ Aucun client trouvé à supprimer.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du client : " + e.getMessage());
        }
    }


    public Client getById(int idClient) {
        String qry = "SELECT * FROM `client` WHERE `id_client` = ?";
        Client client = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, idClient);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setId(rs.getInt("id_utilisateur"));
                client.setNumTel(rs.getString("num_tel_client"));
                client.setAdresse(rs.getString("addresse_client"));
                client.setImageUrl(rs.getString("image_url"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération du client : " + e.getMessage());
        }

        return client;
    }

    public List<Map<String, Object>> getDetailsClient(int idClient) {
        List<Map<String, Object>> detailsList = new ArrayList<>();
        String qry = "SELECT u.id AS utilisateur_id, u.nom, u.prenom, u.mail, u.password, u.role, " +
                "c.id_client, c.num_tel, c.adresse " +
                "FROM utilisateur u " +
                "JOIN client c ON u.id = c.id_utilisateur " +
                "WHERE c.id_client = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, idClient);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Map<String, Object> details = new HashMap<>();
                details.put("id_client", rs.getInt("id_client"));
                details.put("id_utilisateur", rs.getInt("utilisateur_id"));
                details.put("nom", rs.getString("nom"));
                details.put("prenom", rs.getString("prenom"));
                details.put("mail", rs.getString("mail"));
                details.put("password", rs.getString("password"));
                details.put("role", rs.getString("role"));
                details.put("num_tel", rs.getString("num_tel"));
                details.put("adresse", rs.getString("adresse"));

                detailsList.add(details);
            }

            if (detailsList.isEmpty()) {
                System.out.println("⚠️ Aucun client trouvé avec l'ID: " + idClient);
            } else {
                System.out.println("✅ Détails du client récupérés avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des détails du client : " + e.getMessage());
        }

        return detailsList;
    }

public Client getClientParIdUtilisateur(int id_utilisateur) {
    Client client = null;
    String qry = "SELECT * FROM client WHERE id_utilisateur = ?";
    try {
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, id_utilisateur);
        ResultSet rs = pstmt.executeQuery();
        System.out.println("id_utilisateur");
        System.out.println((id_utilisateur));
        if (rs.next()) {
            client = new Client();
            client.setIdClient(rs.getInt("id_client"));
            client.setId(rs.getInt("id_utilisateur"));
            client.setNumTel(rs.getString("num_tel_client"));
            client.setAdresse(rs.getString("addresse_client"));
            client.setImageUrl(rs.getString("image_url"));
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la récupération du client : " + e.getMessage());
    }
    return client;
}

>>>>>>> user
}
