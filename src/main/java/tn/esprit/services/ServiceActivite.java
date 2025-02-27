package tn.esprit.services;

import tn.esprit.models.Activite;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivite {
    private Connection cnx;

    public ServiceActivite() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void add(Activite activite) {
        if (activite.getDateDebutA() == null || activite.getDateFinA() == null) {
            System.out.println("❌ Erreur : Les dates ne doivent pas être null !");
            return;
        }

        String qry = "INSERT INTO `activite` (`nomActivite`, `description`, `dateDebutA`, `dateFinA`, `idEvent`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, activite.getNomActivite());
            pstm.setString(2, activite.getDescription());
            pstm.setDate(3, new java.sql.Date(activite.getDateDebutA().getTime()));
            pstm.setDate(4, new java.sql.Date(activite.getDateFinA().getTime()));
            pstm.setInt(5, activite.getIdEvent());

            pstm.executeUpdate();
            System.out.println("✅ Activité ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de l'activité : " + e.getMessage());
        }
    }

    public List<Activite> getAll() {
        List<Activite> activites = new ArrayList<>();
        String qry = "SELECT * FROM `activite`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Activite act = new Activite();
                act.setIdActivite(rs.getInt("idActivite"));
                act.setNomActivite(rs.getString("nomActivite"));
                act.setDescription(rs.getString("description"));
                act.setDateDebutA(rs.getDate("dateDebutA"));
                act.setDateFinA(rs.getDate("dateFinA"));
                act.setIdEvent(rs.getInt("idEvent"));
                activites.add(act);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des activités : " + e.getMessage());
        }
        return activites;
    }

    public void update(Activite activite, int id) {
        if (activite.getDateDebutA() == null || activite.getDateFinA() == null) {
            System.out.println("❌ Erreur : Les dates ne doivent pas être null !");
            return;
        }

        String qry = "UPDATE `activite` SET `nomActivite` = ?, `description` = ?, `dateDebutA` = ?, `dateFinA` = ?, `idEvent` = ? WHERE `idActivite` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, activite.getNomActivite());
            pstm.setString(2, activite.getDescription());
            pstm.setDate(3, new java.sql.Date(activite.getDateDebutA().getTime()));
            pstm.setDate(4, new java.sql.Date(activite.getDateFinA().getTime()));
            pstm.setInt(5, activite.getIdEvent());
            pstm.setInt(6, id);

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Activité mise à jour avec succès !");
            } else {
                System.out.println("⚠️ Aucune activité trouvée avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour de l'activité : " + e.getMessage());
        }
    }

    public void delete(int id) {
        String qry = "DELETE FROM `activite` WHERE `idActivite` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            int rowsDeleted = pstm.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Activité supprimée avec succès !");
            } else {
                System.out.println("⚠️ Aucune activité trouvée avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression de l'activité : " + e.getMessage());
        }
    }
}