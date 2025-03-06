package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation implements IService<Reservation> {
    private Connection cnx;

    public ServiceReservation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Reservation reservation) {
        String qry = "INSERT INTO `reservation`(`id_voiture`, `id_billetAvion`, `id_hotel`, `id_client`, `statut`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reservation.getId_voiture());
            pstm.setInt(2, reservation.getId_billetAvion());
            pstm.setInt(3, reservation.getId_hotel());
            pstm.setInt(4, reservation.getId_client());
            pstm.setString(5, reservation.getStatut());

            pstm.executeUpdate();
            System.out.println("Réservation ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réservation: " + e.getMessage());
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String qry = "SELECT * FROM `reservation`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getInt("id_voiture"),
                        rs.getInt("id_billetAvion"),
                        rs.getInt("id_hotel"),
                        rs.getInt("id_client"),
                        rs.getString("statut")
                );
                r.setId_reservation(rs.getInt("id_reservation"));
                reservations.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réservations: " + e.getMessage());
        }

        return reservations;
    }

    @Override
    public void update(Reservation reservation) {
        String qry = "UPDATE `reservation` SET `id_voiture`=?, `id_billetAvion`=?, `id_hotel`=?, `id_client`=?, `statut`=? WHERE `id_reservation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reservation.getId_voiture());
            pstm.setInt(2, reservation.getId_billetAvion());
            pstm.setInt(3, reservation.getId_hotel());
            pstm.setInt(4, reservation.getId_client());
            pstm.setString(5, reservation.getStatut());
            pstm.setInt(6, reservation.getId_reservation());

            pstm.executeUpdate();
            System.out.println("Réservation mise à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la réservation: " + e.getMessage());
        }
    }

    @Override
    public void delete(Reservation reservation) {
        String qry = "DELETE FROM `reservation` WHERE `id_reservation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reservation.getId_reservation());

            pstm.executeUpdate();
            System.out.println("Réservation supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }

    }
    public List<Reservation> getReservationsByClientId(int clientId) {
        List<Reservation> reservations = new ArrayList<>();
        String qry = "SELECT * FROM `reservation` WHERE `id_client` = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, clientId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("id_voiture"),
                        rs.getInt("id_billetAvion"),
                        rs.getInt("id_hotel"),
                        rs.getInt("id_client"),
                        rs.getString("statut")
                );
                reservation.setId_reservation(rs.getInt("id_reservation"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }

        return reservations;
    }
}