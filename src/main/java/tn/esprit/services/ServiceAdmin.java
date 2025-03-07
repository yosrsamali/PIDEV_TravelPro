package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Admin;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.*;
import java.util.List;

public class ServiceAdmin implements IService<Admin> {
    private Connection cnx;

    public ServiceAdmin() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Admin admin) {
        String qry = "INSERT INTO `admin`(`id_utilisateur`) VALUES (?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, admin.getId());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    admin.setIdAdmin(generatedKeys.getInt(1)); // Récupération de l'ID auto-généré
                }
            }
            System.out.println(" Admin ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'ajout d'un admin : " + e.getMessage());
        }
    }




    @Override
    public List<Admin> getAll() {
        List<Admin> admins = new ArrayList<>();
        String qry = "SELECT * FROM `admin`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Admin a = new Admin();
                a.setIdAdmin(rs.getInt("id_admin"));  // Vérifie si c'est bien "id_admin" et pas "id_Admin"
                a.setId(rs.getInt("id_utilisateur"));

                admins.add(a);
            }
            System.out.println("✅ Liste des admins récupérée !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des admins : " + e.getMessage());
        }

        return admins;
    }

    @Override
    public void update(Admin admin) {
        String qry = "UPDATE `admin` SET `id_utilisateur`=? WHERE `id_admin`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, admin.getId());
            pstm.setInt(2, admin.getIdAdmin());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Admin mis à jour !");
            } else {
                System.out.println("⚠️ Aucune modification, admin non trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour d'un admin : " + e.getMessage());
        }
    }

    @Override
    public void delete(Admin admin) {
        String qry = "DELETE FROM `admin` WHERE `id_admin`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, admin.getIdAdmin());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Admin supprimé !");
            } else {
                System.out.println("⚠️ Aucun admin trouvé à supprimer.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression d'un admin : " + e.getMessage());
        }
    }


    public Admin getById(int idAdmin) {
        String qry = "SELECT * FROM `admin` WHERE `id_admin` = ?";
        Admin admin = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, idAdmin);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setIdAdmin(rs.getInt("id_admin"));
                admin.setId(rs.getInt("id_utilisateur"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération de l'admin : " + e.getMessage());
        }

        return admin;
    }

    public List<Map<String, Object>> getDetailsAdmin(int idAdmin) {
        List<Map<String, Object>> detailsList = new ArrayList<>();
        String qry = "SELECT u.id AS utilisateur_id, u.nom, u.prenom, u.mail, u.password, u.role, " +
                "a.id_admin " +
                "FROM utilisateur u " +
                "JOIN admin a ON u.id = a.id_utilisateur " +
                "WHERE a.id_admin = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, idAdmin);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Map<String, Object> details = new HashMap<>();
                details.put("id_admin", rs.getInt("id_admin"));
                details.put("id_utilisateur", rs.getInt("utilisateur_id"));
                details.put("nom", rs.getString("nom"));
                details.put("prenom", rs.getString("prenom"));
                details.put("mail", rs.getString("mail"));
                details.put("password", rs.getString("password"));
                details.put("role", rs.getString("role"));

                detailsList.add(details);
            }

            if (detailsList.isEmpty()) {
                System.out.println("⚠️ Aucun administrateur trouvé avec l'ID: " + idAdmin);
            } else {
                System.out.println("✅ Détails de l'administrateur récupérés avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des détails de l'administrateur : " + e.getMessage());
        }

        return detailsList;
    }



    public Admin getAdminParIdUtilisateur(int id_utilisateur) {
        Admin admin = null;
        String qry = "SELECT * FROM admin WHERE id_utilisateur = ?";

        try {
            PreparedStatement pstmt = cnx.prepareStatement(qry);
            pstmt.setInt(1, id_utilisateur);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setIdAdmin(rs.getInt("id_admin"));
                admin.setId(rs.getInt("id_utilisateur"));
               }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'admin : " + e.getMessage());
        }

        return admin;
    }



}
