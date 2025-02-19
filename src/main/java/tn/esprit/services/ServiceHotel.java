package tn.esprit.services;

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
        String qry = "INSERT INTO `hotel`(`nomHotel`, `ville`, `prix_par_nuit`, `disponibilite`, `nombre_etoile`, `type_de_chambre`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, hotel.getNom());
            pstm.setString(2, hotel.getVille());
            pstm.setDouble(3, hotel.getPrixParNuit());
            pstm.setBoolean(4, hotel.isDisponible());
            pstm.setInt(5, hotel.getNombreEtoile());
            pstm.setString(6, hotel.getTypeDeChambre());

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
                        rs.getString("nomHotel"),
                        rs.getString("ville"),
                        rs.getDouble("prix_par_nuit"),
                        rs.getBoolean("disponibilite"),
                        rs.getInt("nombre_etoile"),       // Nouvel attribut
                        rs.getString("type_de_chambre")  // Nouvel attribut
                );
                h.setId(rs.getInt("id")); // Assurez-vous de définir l'ID
                hotels.add(h);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des hôtels: " + e.getMessage());
        }

        return hotels;
    }

    @Override
    public void update(Hotel hotel) {
        String qry = "UPDATE `hotel` SET `nomHotel`=?, `ville`=?, `prix_par_nuit`=?, `disponibilite`=?, `nombre_etoile`=?, `type_de_chambre`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, hotel.getNom());
            pstm.setString(2, hotel.getVille());
            pstm.setDouble(3, hotel.getPrixParNuit());
            pstm.setBoolean(4, hotel.isDisponible());
            pstm.setInt(5, hotel.getNombreEtoile());
            pstm.setString(6, hotel.getTypeDeChambre());
            pstm.setInt(7, hotel.getId());

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
}