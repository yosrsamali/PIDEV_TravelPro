package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.BilletAvion;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceBilletAvion implements IService<BilletAvion> {
    private Connection cnx;

    public ServiceBilletAvion() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(BilletAvion billetAvion) {
        String qry = "INSERT INTO `billetavion`(`compagnie`, `class_Billet`, `villeDepart`, `villeArrivee`, `dateDepart`, `dateArrivee`, `prix`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, billetAvion.getCompagnie());
            pstm.setString(2, billetAvion.getClass_Billet());
            pstm.setString(3, billetAvion.getVilleDepart());
            pstm.setString(4, billetAvion.getVilleArrivee());
            pstm.setTimestamp(5, new Timestamp(billetAvion.getDateDepart().getTime()));
            pstm.setTimestamp(6, new Timestamp(billetAvion.getDateArrivee().getTime()));

            pstm.setDouble(7, billetAvion.getPrix());

            pstm.executeUpdate();
            System.out.println("Billet d'avion ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du billet d'avion: " + e.getMessage());
        }
    }

    @Override
    public List<BilletAvion> getAll() {
        List<BilletAvion> billets = new ArrayList<>();
        String qry = "SELECT * FROM `billetavion`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                BilletAvion billet = new BilletAvion(
                        rs.getInt("id_billetavion"),
                        rs.getString("compagnie"),
                        rs.getString("class_Billet"),
                        rs.getString("villeDepart"),
                        rs.getString("villeArrivee"),
                        rs.getDate("dateDepart"),
                        rs.getDate("dateArrivee"),
                        rs.getDouble("prix")
                );
                billets.add(billet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des billets: " + e.getMessage());
        }

        return billets;
    }
    @Override
    public void update(BilletAvion billet) {
        String qry = "UPDATE billetavion SET compagnie=?, class_Billet=?, villeDepart=?, villeArrivee=?, dateDepart=?, dateArrivee=?, prix=? WHERE id_billetavion=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, billet.getCompagnie());
            pstm.setString(2, billet.getClass_Billet());
            pstm.setString(3, billet.getVilleDepart());
            pstm.setString(4, billet.getVilleArrivee());
            pstm.setDate(5, new java.sql.Date(billet.getDateDepart().getTime())); // Convertir en java.sql.Date
            pstm.setDate(6, new java.sql.Date(billet.getDateArrivee().getTime())); // Convertir en java.sql.Date
            pstm.setDouble(7, billet.getPrix());
            pstm.setInt(8, billet.getId());

            pstm.executeUpdate();
            System.out.println("Billet d'avion mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du billet d'avion: " + e.getMessage());
        }
    }
    public BilletAvion getById(int id) {
        String qry = "SELECT * FROM `billetavion` WHERE `id_billetavion`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return new BilletAvion(
                        rs.getInt("id"),
                        rs.getString("compagnie"),
                        rs.getString("class_Billet"),
                        rs.getString("villeDepart"),
                        rs.getString("villeArrivee"),
                        rs.getDate("dateDepart"),
                        rs.getDate("dateArrivee"),
                        rs.getDouble("prix")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du billet d'avion: " + e.getMessage());
        }
        return null;
    }
    @Override
    public void delete(BilletAvion billetAvion) {
        String qry = "DELETE FROM `billetavion` WHERE `id_billetavion`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, billetAvion.getId());

            pstm.executeUpdate();
            System.out.println("Billet d'avion supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du billet d'avion: " + e.getMessage());
        }
    }
    public List<BilletAvion> getAvailableBillets(String villeDepart, String villeArrivee, Date dateDepart, String classBillet) {
        List<BilletAvion> availableBillets = new ArrayList<>();
        String qry = "SELECT * FROM `billetavion` WHERE `villeDepart`=? AND `villeArrivee`=? AND `dateDepart`=? AND `class_Billet`=?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, villeDepart);
            pstm.setString(2, villeArrivee);
            pstm.setDate(3, new java.sql.Date(dateDepart.getTime())); // Convertir en java.sql.Date
            pstm.setString(4, classBillet);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                BilletAvion billet = new BilletAvion(
                        rs.getInt("id_billetavion"),
                        rs.getString("compagnie"),
                        rs.getString("class_Billet"),
                        rs.getString("villeDepart"),
                        rs.getString("villeArrivee"),
                        rs.getDate("dateDepart"),
                        rs.getDate("dateArrivee"),
                        rs.getDouble("prix")
                );
                availableBillets.add(billet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des billets disponibles: " + e.getMessage());
        }

        return availableBillets;
    }
    public List<BilletAvion> getAvailableBillets(String villeDepart, String villeArrivee, Date dateDepart, Date dateRetour, String classBillet) {
        List<BilletAvion> availableBillets = new ArrayList<>();
        String qry = "SELECT * FROM `billetavion` WHERE `villeDepart`=? AND `villeArrivee`=? AND `dateDepart`=? AND `dateArrivee`=? AND `class_Billet`=?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, villeDepart);
            pstm.setString(2, villeArrivee);
            pstm.setDate(3, dateDepart); // Date de départ
            pstm.setDate(4, dateRetour); // Date de retour
            pstm.setString(5, classBillet); // Classe du billet

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                BilletAvion billet = new BilletAvion(
                        rs.getInt("id_billetavion"),
                        rs.getString("compagnie"),
                        rs.getString("class_Billet"),
                        rs.getString("villeDepart"),
                        rs.getString("villeArrivee"),
                        rs.getDate("dateDepart"),
                        rs.getDate("dateArrivee"),
                        rs.getDouble("prix")
                );
                availableBillets.add(billet);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des billets disponibles: " + e.getMessage());
        }

        return availableBillets;
    }

}