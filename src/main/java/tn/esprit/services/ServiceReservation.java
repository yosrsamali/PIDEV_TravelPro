package tn.esprit.services;

import tn.esprit.models.reservation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation {
    private Connection cnx;

    // Constructeur : Connexion à la base de données
    public ServiceReservation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // ✅ Ajouter une réservation
    public void add(reservation res) {
        if (res.getDateDebut() == null || res.getDateFin() == null) {
            System.out.println("❌ Erreur : Les dates ne doivent pas être null !");
            return;
        }

        System.out.println("Ajout de la réservation avec : " +
                "Date de début = " + res.getDateDebut() +
                ", Date de fin = " + res.getDateFin());

        String qry = "INSERT INTO `reservation`(`datedebut`, `datefin`, `statut`, `idservice`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setDate(1, new java.sql.Date(res.getDateDebut().getTime()));
            pstm.setDate(2, new java.sql.Date(res.getDateFin().getTime()));
            pstm.setString(3, res.getStatut());
            pstm.setInt(4, res.getIdService()); // Ajout de idservice

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                res.setIdReservation(rs.getInt(1));
            }
            System.out.println("✅ Réservation ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }
    }

    // ✅ Récupérer toutes les réservations
    public List<reservation> getAll() {
        List<reservation> reservations = new ArrayList<>();
        String qry = "SELECT * FROM `reservation`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                reservation res = new reservation();
                res.setIdReservation(rs.getInt("idreservation"));
                res.setDateDebut(rs.getDate("datedebut"));
                res.setDateFin(rs.getDate("datefin"));
                res.setStatut(rs.getString("statut"));
                res.setIdService(rs.getInt("idservice")); // Ajout de idservice
                reservations.add(res);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des réservations : " + e.getMessage());
        }
        return reservations;
    }

    // ✅ Mettre à jour une réservation
    public void update(reservation res,int id) {
        if (res.getDateDebut() == null || res.getDateFin() == null) {
            System.out.println("❌ Erreur : Les dates ne doivent pas être null !");
            return;
        }

        String qry = "UPDATE `reservation` SET `datedebut` = ?, `datefin` = ?, `statut` = ?, `idservice` = ? WHERE `idreservation` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setDate(1, new java.sql.Date(res.getDateDebut().getTime()));
            pstm.setDate(2, new java.sql.Date(res.getDateFin().getTime()));
            pstm.setString(3, res.getStatut());
            pstm.setInt(4, res.getIdService()); // Ajout de idservice
            pstm.setInt(5, id);
            pstm.executeUpdate();
            System.out.println("✅ Réservation mise à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour de la réservation : " + e.getMessage());
        }
    }

    // ✅ Supprimer une réservation
    public void delete(int id) {
        String qry = "DELETE FROM `reservation` WHERE `idreservation` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            System.out.println("✅ Réservation supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }


}
