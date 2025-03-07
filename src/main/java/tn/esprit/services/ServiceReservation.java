package tn.esprit.services;

<<<<<<< HEAD
import tn.esprit.models.reservation;
import tn.esprit.utils.MyDatabase;

=======
import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.utils.MyDatabase;
>>>>>>> user
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class ServiceReservation {
    private Connection cnx;

    // Constructeur : Connexion à la base de données
=======
public class ServiceReservation implements IService<Reservation> {
    private Connection cnx;

>>>>>>> user
    public ServiceReservation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> user
