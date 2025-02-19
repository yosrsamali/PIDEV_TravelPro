package tn.esprit.services;
import tn.esprit.interfaces.IService;
import tn.esprit.models.evenement;

import tn.esprit.models.reservation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceEvenement implements IService {
    private PreparedStatement pste;
    Connection cnx = MyDatabase.getInstance().getCnx();



    /*public void add(evenement event) {
        if (event.getDateDebutE() == null || event.getDateFinE() == null) {
            System.out.println("❌ Erreur : Les dates ne doivent pas être null !");
            return;
        }

        System.out.println("Ajout de la réservation avec : " +
                "Date de début = " + event.getDateDebutE() +
                ", Date de fin = " + event.getDateFinE());

        String qry = "INSERT INTO `evenement`( `nomEvent`, `lieu`,`dateDebutE`, `dateFinE`, `type`, `idReservation`) VALUES (?, ?, ?, ? ,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, event.getNomEvent());
////            pstm.setString(2, event.getLieu());
////            pstm.setDate(3, new java.sql.Date(event.getDateDebutE().getTime()));
////            pstm.setDate(4, new java.sql.Date(event.getDateFinE().getTime()));
////            pstm.setString(5, event.getType());
////            pstm.setInt(6, event.getIdReservation());


            pstm.executeUpdate();


            System.out.println("✅ evenement ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la evenement : " + e.getMessage());
        }
    }

   */ @Override
    public void add(evenement e) {
        String req = "INSERT INTO `evenement`( `nomEvent`, `lieu`,`dateDebutE`, `dateFinE`, `type`, `image`,`idReservation`) VALUES (?, ?, ?, ? ,?,?,?)";
        try {
            pste = cnx.prepareStatement(req);
            pste.setString(1, e.getNomEvent());
            pste.setString(2, e.getLieu());
            pste.setDate(3, e.getDateDebutE());
            pste.setDate(4, e.getDateFinE());
            pste.setString(5, e.getType());
            pste.setString(6, e.getImage());
            pste.setInt(7, e.getIdReservation());
            pste.executeUpdate();
            System.out.println("Evenement créée");
        } catch (Exception ex) {
            Logger.getLogger(ServiceEvenement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<evenement> getAll() {
        List<evenement> evenements = new ArrayList<>();
        String qry = "SELECT * FROM `evenement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                evenement se = new evenement();
                se.setIdEvent(rs.getInt("idEvent"));
                se.setNomEvent(rs.getString("nomEvent"));
                se.setLieu(rs.getString("lieu"));
                se.setDateDebutE(rs.getDate("dateDebutE"));
                se.setDateFinE(rs.getDate("dateFinE"));
                se.setType(rs.getString("type"));
                se.setIdReservation(rs.getInt("idReservation"));
                se.setImage(rs.getString("image"));
                evenements.add(se);

            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des evenments : " + e.getMessage());
        }
        return evenements;
    }
    public void delete(int id) {
        String qry = "DELETE FROM `evenement` WHERE `idEvent` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            int rowsDeleted = pstm.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Événement supprimé avec succès !");
            } else {
                System.out.println("⚠️ Aucun événement trouvé avec l'ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression de l'événement : " + e.getMessage());
        }
    }
    public void update(evenement event, int id) {
        if (event.getDateDebutE() == null || event.getDateFinE() == null) {
            System.out.println("❌ Erreur : Les dates ne doivent pas être null !");
            return;
        }

        String qry = "UPDATE `evenement` SET `nomEvent` = ?, `lieu` = ?, `dateDebutE` = ?, `dateFinE` = ?, `type` = ?,`image`= ?, `idReservation` = ? WHERE `idEvent` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, event.getNomEvent());
            pstm.setString(2, event.getLieu());
            pstm.setDate(3, new java.sql.Date(event.getDateDebutE().getTime()));
            pstm.setDate(4, new java.sql.Date(event.getDateFinE().getTime()));
            pstm.setString(5, event.getType());
            pstm.setString(6, event.getImage());
            pstm.setInt(7, event.getIdReservation()); // Ajout de idReservation
            pstm.setInt(8, id);

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Événement mis à jour avec succès !");
            } else {
                System.out.println("⚠️ Aucun événement trouvé avec l'ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour de l'événement : " + e.getMessage());
        }
    }
}

   /* @Override
    public List getAll() {
        return List.of();
    }
}*/




