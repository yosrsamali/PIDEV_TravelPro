package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IService<Utilisateur> {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = MyDatabase.getInstance().getCnx();
    }
    @Override
    public void add(Utilisateur utilisateur) {

        String qry = "INSERT INTO `utilisateur`(`nom_utilisateur`, `prenom`, `mail_utilisateur`, `mot_de_passe_utilisateur`, `role_utilisateur`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getMail());
            pstm.setString(4, utilisateur.getPassword());

            pstm.setString(5, utilisateur.getRole());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    utilisateur.setId(generatedKeys.getInt(1)); // Récupération de l'ID auto-généré
                }
            }
            System.out.println("✅ Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout d'un utilisateur : " + e.getMessage());
        }
    }
/*
    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String qry = "SELECT * FROM `utilisateur`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_utilisateur "));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setMail(rs.getString("mail"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));

                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return utilisateurs;
    }
*/


    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur"; // Removed backticks (optional)

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_utilisateur")); // Fixed extra space
                u.setNom(rs.getString("nom_utilisateur"));
                u.setPrenom(rs.getString("prenom"));
                u.setMail(rs.getString("mail_utilisateur"));
                u.setPassword(rs.getString("mot_de_passe_utilisateur"));
                u.setRole(rs.getString("role_utilisateur"));

                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }

        return utilisateurs;
    }


    @Override
    public void update(Utilisateur utilisateur) {
        String qry = "UPDATE utilisateur SET nom_utilisateur=?, prenom=?, mail_utilisateur=?, mot_de_passe_utilisateur=?, role_utilisateur=? WHERE id_utilisateur=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getMail());
            pstm.setString(4, utilisateur.getPassword());
            pstm.setString(5, utilisateur.getRole());
            pstm.setInt(6, utilisateur.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Utilisateur utilisateur) {
        String qry = "DELETE FROM `utilisateur` WHERE `id_utilisateur`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, utilisateur.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public Utilisateur getByMailAndPassword(String mail, String password) {
        Utilisateur utilisateur = null;
        String qry = "SELECT * FROM utilisateur WHERE mail_utilisateur = ? AND mot_de_passe_utilisateur = ?";

        try {
            PreparedStatement pst = cnx.prepareStatement(qry);
            pst.setString(1, mail);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id_utilisateur"));
                utilisateur.setNom(rs.getString("nom_utilisateur"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setMail(rs.getString("mail_utilisateur"));
                utilisateur.setPassword(rs.getString("mot_de_passe_utilisateur"));
                utilisateur.setRole(rs.getString("role_utilisateur"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }

        return utilisateur; // Retourne null si aucun utilisateur n'est trouvé
    }

    public Utilisateur getById(int id) {
        Utilisateur utilisateur = null;
        String qry = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";

        try {
            PreparedStatement pst = cnx.prepareStatement(qry);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id_utilisateur"));
                utilisateur.setNom(rs.getString("nom_utilisateur"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setMail(rs.getString("mail_utilisateur"));
                utilisateur.setPassword(rs.getString("mot_de_passe_utilisateur"));
                utilisateur.setRole(rs.getString("role_utilisateur"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }

        return utilisateur; // Retourne null si aucun utilisateur n'est trouvé
    }


    public List<Utilisateur> getAllAdmin() {
        List<Utilisateur> admins = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur WHERE role = 'admin'";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur admin = new Utilisateur();
                admin.setId(rs.getInt("id_utilisateur "));
                admin.setNom(rs.getString("nom"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setMail(rs.getString("mail"));
                admin.setPassword(rs.getString("password"));
                admin.setRole(rs.getString("role"));

                admins.add(admin);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des admins : " + e.getMessage());
        }

        return admins;
    }

    public List<Utilisateur> getClients() {
        List<Utilisateur> clients = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur WHERE role = 'client'";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id_utilisateur "));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setMail(rs.getString("mail"));
                utilisateur.setPassword(rs.getString("password"));
                utilisateur.setRole(rs.getString("role"));

                clients.add(utilisateur);
            }
            System.out.println("✅ Liste des utilisateurs avec rôle 'client' récupérée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des clients : " + e.getMessage());
        }

        return clients;
    }




}
