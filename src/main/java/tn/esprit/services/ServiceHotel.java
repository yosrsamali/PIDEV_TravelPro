package tn.esprit.services;

import javafx.scene.control.Alert;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Hotel;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceHotel implements IService<Hotel> {
    private Connection cnx;

    public ServiceHotel() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Hotel hotel) {
        String qry = "INSERT INTO `hotel`(`nom`, `ville`, `prixParNuit`, `disponible`, `nombreEtoile`, `typeDeChambre`, `dateCheckIn`, `dateCheckOut`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, hotel.getNom());
            pstm.setString(2, hotel.getVille());
            pstm.setDouble(3, hotel.getPrixParNuit());
            pstm.setBoolean(4, hotel.isDisponible());
            pstm.setInt(5, hotel.getNombreEtoile());
            pstm.setString(6, hotel.getTypeDeChambre());
            pstm.setDate(7, hotel.getDateCheckIn());
            pstm.setDate(8, hotel.getDateCheckOut());

            pstm.executeUpdate();
            System.out.println("Hotel ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'hôtel: " + e.getMessage());
        }
    }

    @Override
    public List<Hotel> getAll() {
        List<Hotel> hotels = new ArrayList<>();
        String qry = "SELECT * FROM `hotel`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Hotel h = new Hotel(
                        rs.getString("nom"),
                        rs.getString("ville"),
                        rs.getDouble("prixParNuit"),
                        rs.getBoolean("disponible"),
                        rs.getInt("nombreEtoile"),
                        rs.getString("typeDeChambre"),
                        rs.getDate("dateCheckIn"),
                        rs.getDate("dateCheckOut")
                );
                h.setId(rs.getInt("id"));
                hotels.add(h);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des hôtels: " + e.getMessage());
        }

        return hotels;
    }

    public Hotel getById(int id) {
        String qry = "SELECT * FROM `hotel` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return new Hotel(
                        rs.getString("nom"),
                        rs.getString("ville"),
                        rs.getDouble("prixParNuit"),
                        rs.getBoolean("disponible"),
                        rs.getInt("nombreEtoile"),
                        rs.getString("typeDeChambre"),
                        rs.getDate("dateCheckIn"),
                        rs.getDate("dateCheckOut")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'hôtel: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Hotel hotel) {
        String qry = "UPDATE `hotel` SET `nom`=?, `ville`=?, `prixParNuit`=?, `disponible`=?, `nombreEtoile`=?, `typeDeChambre`=?, `dateCheckIn`=?, `dateCheckOut`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, hotel.getNom());
            pstm.setString(2, hotel.getVille());
            pstm.setDouble(3, hotel.getPrixParNuit());
            pstm.setBoolean(4, hotel.isDisponible());
            pstm.setInt(5, hotel.getNombreEtoile());
            pstm.setString(6, hotel.getTypeDeChambre());
            pstm.setDate(7, hotel.getDateCheckIn());
            pstm.setDate(8, hotel.getDateCheckOut());
            pstm.setInt(9, hotel.getId());

            pstm.executeUpdate();
            System.out.println("Hotel mis à jour avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'hôtel: " + e.getMessage());
        }
    }

    @Override
    public void delete(Hotel hotel) {
        String qry = "DELETE FROM `hotel` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, hotel.getId());

            pstm.executeUpdate();
            System.out.println("Hotel supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'hôtel: " + e.getMessage());
        }
    }
    public boolean isHotelInUse(int hotelId) {
        String query = "SELECT COUNT(*) FROM reservation WHERE id_hotel = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retourne true si l'hôtel est en cours d'utilisation
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void deleteByHotelId(int hotelId) {
        String query = "DELETE FROM reservation WHERE id_hotel = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression des réservations associées à l'hôtel.", e);
        }
    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public List<Hotel> getAvailableHotels(String ville, Date dateCheckIn, Date dateCheckOut, String typeDeChambre) {
        List<Hotel> availableHotels = new ArrayList<>();
        String qry = "SELECT * FROM `hotel` WHERE `ville`=? AND `dateCheckIn`=? AND `dateCheckOut`=? AND `typeDeChambre`=?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, ville);
            pstm.setDate(2, dateCheckIn);
            pstm.setDate(3, dateCheckOut);
            pstm.setString(4, typeDeChambre);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Hotel hotel = new Hotel(
                        rs.getString("nom"),
                        rs.getString("ville"),
                        rs.getDouble("prixParNuit"),
                        rs.getBoolean("disponible"),
                        rs.getInt("nombreEtoile"),
                        rs.getString("typeDeChambre"),
                        rs.getDate("dateCheckIn"),
                        rs.getDate("dateCheckOut")
                );
                hotel.setId(rs.getInt("id"));
                availableHotels.add(hotel);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des hôtels disponibles: " + e.getMessage());
        }

        return availableHotels;
    }
}
