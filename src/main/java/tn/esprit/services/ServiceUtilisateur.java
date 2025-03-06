package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;
import tn.esprit.utils.MailService;
import org.mindrot.jbcrypt.BCrypt; // Import de BCrypt pour le hachage
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
        // Hachage du mot de passe
        String motDePasseHache = BCrypt.hashpw(utilisateur.getPassword(), BCrypt.gensalt());

        String qry = "INSERT INTO `utilisateur`(`nom_utilisateur`, `prenom`, `mail_utilisateur`, `mot_de_passe_utilisateur`, `role_utilisateur`, `code_verification`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getMail());
            pstm.setString(4, motDePasseHache);  // Sauvegarde du mot de passe haché

            pstm.setString(5, utilisateur.getRole());
            pstm.setString(6, utilisateur.getCodeVerification());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    utilisateur.setEtat(false);
                    utilisateur.setId(generatedKeys.getInt(1)); // Récupération de l'ID auto-généré
                }
            }
            System.out.println("✅ Utilisateur ajouté avec succès !");

            String codeVerification = utilisateur.getCodeVerification(); // Générer le code
            String sujet = "Votre Code de Vérification";
            MailService.envoyerMail(utilisateur.getMail(), sujet, codeVerification);

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout d'un utilisateur : " + e.getMessage());
        }
    }

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur"; // Removed backticks (optional)

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_utilisateur"));
                u.setNom(rs.getString("nom_utilisateur"));
                u.setPrenom(rs.getString("prenom"));
                u.setMail(rs.getString("mail_utilisateur"));
                u.setPassword(rs.getString("mot_de_passe_utilisateur")); // Mot de passe haché
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
        String qry = "UPDATE utilisateur SET nom_utilisateur=?, prenom=?, mail_utilisateur=?, role_utilisateur=? WHERE id_utilisateur=?";

        try {

            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getMail());
            pstm.setString(4, utilisateur.getRole());
            pstm.setInt(5, utilisateur.getId());

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
        String qry = "SELECT * FROM utilisateur WHERE mail_utilisateur = ?";

        try {
            PreparedStatement pst = cnx.prepareStatement(qry);
            pst.setString(1, mail);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String motDePasseHache = rs.getString("mot_de_passe_utilisateur");

                // Vérification du mot de passe
                if (BCrypt.checkpw(password, motDePasseHache)) {
                    utilisateur = new Utilisateur();
                    utilisateur.setEtat(rs.getBoolean("etat"));
                    utilisateur.setId(rs.getInt("id_utilisateur"));
                    utilisateur.setNom(rs.getString("nom_utilisateur"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setMail(rs.getString("mail_utilisateur"));
                    utilisateur.setPassword(rs.getString("mot_de_passe_utilisateur"));
                    utilisateur.setRole(rs.getString("role_utilisateur"));
                } else {
                    System.out.println("❌ Mot de passe incorrect");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }

        return utilisateur; // Retourne null si aucun utilisateur n'est trouvé ou si le mot de passe ne correspond pas
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

    // ... (le reste des méthodes inchangées)

    public List<Utilisateur> getAllAdmin() {
        List<Utilisateur> admins = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur WHERE role = 'admin'";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur admin = new Utilisateur();
                admin.setId(rs.getInt("id_utilisateur"));
                admin.setNom(rs.getString("nom_utilisateur"));
                admin.setPrenom(rs.getString("prenom"));
                admin.setMail(rs.getString("mail_utilisateur"));
                admin.setPassword(rs.getString("mot_de_passe_utilisateur"));
                admin.setRole(rs.getString("role_utilisateur"));

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
                utilisateur.setId(rs.getInt("id_utilisateur"));
                utilisateur.setNom(rs.getString("nom_utilisateur"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setMail(rs.getString("mail_utilisateur"));
                utilisateur.setPassword(rs.getString("mot_de_passe_utilisateur"));
                utilisateur.setRole(rs.getString("role_utilisateur"));

                clients.add(utilisateur);
            }
            System.out.println("✅ Liste des utilisateurs avec rôle 'client' récupérée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des clients : " + e.getMessage());
        }

        return clients;
    }

    public void updateEtatUtilisateur(int idUtilisateur, boolean etat) {
        String qry = "UPDATE utilisateur SET etat=? WHERE id_utilisateur=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setBoolean(1, etat);
            pstm.setInt(2, idUtilisateur);
            pstm.executeUpdate();
            System.out.println("✅ État de l'utilisateur mis à jour !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour de l'état : " + e.getMessage());
        }
    }
}
