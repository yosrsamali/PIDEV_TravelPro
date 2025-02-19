package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Voiture;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceVoiture implements IService<Voiture> {
    private Connection cnx;

    public ServiceVoiture() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Voiture voiture) {
        String qry = "INSERT INTO `voiture`(`marque`, `modele`, `annee`, `prixParJour`, `disponible`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, voiture.getMarque());
            pstm.setString(2, voiture.getModele());
            pstm.setInt(3, voiture.getAnnee());
            pstm.setDouble(4, voiture.getPrixParJour());
            pstm.setBoolean(5, voiture.isDisponible());

            pstm.executeUpdate();
            System.out.println("Voiture ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la voiture: " + e.getMessage());
        }
    }

    @Override
    public List<Voiture> getAll() {
        List<Voiture> voitures = new ArrayList<>();
        String qry = "SELECT * FROM `voiture`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Voiture v = new Voiture(

                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getInt("annee"),
                        rs.getDouble("prixParJour"),
                        rs.getBoolean("disponible")
                );

                voitures.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des voitures: " + e.getMessage());
        }

        return voitures;
    }

    @Override
    public void update(Voiture voiture) {
        String qry = "UPDATE `voiture` SET `marque`=?, `modele`=?, `annee`=?, `prixParJour`=?, `disponible`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, voiture.getMarque());
            pstm.setString(2, voiture.getModele());
            pstm.setInt(3, voiture.getAnnee());
            pstm.setDouble(4, voiture.getPrixParJour());
            pstm.setBoolean(5, voiture.isDisponible());
            pstm.setInt(6, voiture.getId());  // Important!

            pstm.executeUpdate();
            System.out.println("Voiture mise à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la voiture: " + e.getMessage());
        }
    }


    @Override
    public void delete(Voiture voiture) {
        String qry = "DELETE FROM `voiture` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, voiture.getId());

            pstm.executeUpdate();
            System.out.println("Voiture supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la voiture: " + e.getMessage());
        }
    }
}
