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
        String qry = "INSERT INTO `billetavion`(`compagnie`, `class_billet`, `villeDepart`, `villeArrivee`, `dateDepart`, `dateArrivee`, `prix`) VALUES (?,?,?,?,?,?,?)";
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
                BilletAvion b = new BilletAvion(

                        rs.getString("compagnie"),
                        rs.getString("class_billet"),
                        rs.getString("villeDepart"),
                        rs.getString("villeArrivee"),

                        rs.getTimestamp("dateDepart"),
                        rs.getTimestamp("dateArrivee"),
                        rs.getDouble("prix")
                );

                billets.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des billets d'avion: " + e.getMessage());
        }

        return billets;
    }

    @Override
    public void update(BilletAvion billetAvion) {
        String qry = "UPDATE `billetavion` SET `compagnie`=?, `class_billet`=?, `villeDepart`=?, `villeArrivee`=?, `dateDepart`=?, `dateArrivee`=?, `prix`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, billetAvion.getCompagnie());
            pstm.setString(2, billetAvion.getClass_Billet());
            pstm.setString(3, billetAvion.getVilleDepart());
            pstm.setString(4, billetAvion.getVilleArrivee());
            pstm.setDouble(5, billetAvion.getPrix());
            pstm.setTimestamp(6, new Timestamp(billetAvion.getDateDepart().getTime()));
            pstm.setTimestamp(7, new Timestamp(billetAvion.getDateArrivee().getTime()));
            pstm.setInt(8, billetAvion.getId());

            pstm.executeUpdate();
            System.out.println("Billet d'avion mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du billet d'avion: " + e.getMessage());
        }
    }

    @Override
    public void delete(BilletAvion billetAvion) {
        String qry = "DELETE FROM `billetavion` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, billetAvion.getId());

            pstm.executeUpdate();
            System.out.println("Billet d'avion supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du billet d'avion: " + e.getMessage());
        }
    }
}