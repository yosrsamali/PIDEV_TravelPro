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
        String qry = "INSERT INTO `voiture`(`marque`, `modele`, `annee`, `prixParJour`, `disponible`, `dateDeLocation`, `dateDeRemise`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, voiture.getMarque());
            pstm.setString(2, voiture.getModele());
            pstm.setInt(3, voiture.getAnnee());
            pstm.setDouble(4, voiture.getPrixParJour());
            pstm.setBoolean(5, voiture.isDisponible());
            pstm.setDate(6, new java.sql.Date(voiture.getDateDeLocation().getTime())); // Conversion de Date en java.sql.Date
            pstm.setDate(7, new java.sql.Date(voiture.getDateDeRemise().getTime()));   // Conversion de Date en java.sql.Date

            pstm.executeUpdate();

            // Récupérer l'ID généré
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                voiture.setId(generatedKeys.getInt(1));
            }

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
                        rs.getInt("id"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getInt("annee"),
                        rs.getDouble("prixParJour"),
                        rs.getBoolean("disponible"),
                        rs.getDate("dateDeLocation"), // Nouvelle colonne
                        rs.getDate("dateDeRemise")    // Nouvelle colonne
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
        String qry = "UPDATE `voiture` SET `marque`=?, `modele`=?, `annee`=?, `prixParJour`=?, `disponible`=?, `dateDeLocation`=?, `dateDeRemise`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, voiture.getMarque());
            pstm.setString(2, voiture.getModele());
            pstm.setInt(3, voiture.getAnnee());
            pstm.setDouble(4, voiture.getPrixParJour());
            pstm.setBoolean(5, voiture.isDisponible());
            pstm.setDate(6, new java.sql.Date(voiture.getDateDeLocation().getTime())); // Conversion de Date en java.sql.Date
            pstm.setDate(7, new java.sql.Date(voiture.getDateDeRemise().getTime()));   // Conversion de Date en java.sql.Date
            pstm.setInt(8, voiture.getId());

            pstm.executeUpdate();
            System.out.println("Voiture mise à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la voiture: " + e.getMessage());
        }
    }
    public Voiture getById(int id) {
        String qry = "SELECT * FROM `voiture` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return new Voiture(
                        rs.getInt("id"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getInt("annee"),
                        rs.getDouble("prixParJour"),
                        rs.getBoolean("disponible"),
                        rs.getDate("dateDeLocation"), // Nouvelle colonne
                        rs.getDate("dateDeRemise")    // Nouvelle colonne
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la voiture: " + e.getMessage());
        }
        return null;
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
    public List<Voiture> getAvailableVoitures(Date dateDeLocation, Date dateDeRemise) {
        List<Voiture> availableVoitures = new ArrayList<>();
        String qry = "SELECT * FROM `voiture` WHERE `dateDeLocation`=? AND `dateDeRemise`=? AND `disponible`=true";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setDate(1, dateDeLocation);
            pstm.setDate(2, dateDeRemise);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Voiture voiture = new Voiture(
                        rs.getInt("id"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getInt("annee"),
                        rs.getDouble("prixParJour"),
                        rs.getBoolean("disponible"),
                        rs.getDate("dateDeLocation"),
                        rs.getDate("dateDeRemise")
                );
                availableVoitures.add(voiture);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des voitures disponibles: " + e.getMessage());
        }

        return availableVoitures;
    }
}