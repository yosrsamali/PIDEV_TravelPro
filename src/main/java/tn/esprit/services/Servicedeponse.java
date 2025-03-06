package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.deponse;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Servicedeponse implements IService<deponse> {
    private Connection cnx;

    public Servicedeponse() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void add(deponse deponse) {
        String qry = "INSERT INTO `deponse`(`id_produit`, `quantite_produit`, `prix_achat`, `Date_achat`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, deponse.getId_produit()); // Clé étrangère
            pstm.setInt(2, deponse.getQuantite_produit());
            pstm.setDouble(3, deponse.getPrix_achat());
            pstm.setDate(4, Date.valueOf(deponse.getDate_achat())); // Convertir LocalDate → SQL Date

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Dépense ajoutée avec succès !");
            } else {
                System.out.println("⚠️ Aucune ligne insérée !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la dépense : " + e.getMessage());
        }
    }

    @Override
    public List<deponse> getAll() {
        List<deponse> deponses = new ArrayList<>();
        String qry = "SELECT * FROM `deponse`";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                deponse d = new deponse(
                        rs.getInt("id_deponse"),
                        rs.getInt("id_produit"),
                        rs.getInt("quantite_produit"),
                        rs.getDouble("prix_achat"),
                        rs.getDate("Date_achat").toLocalDate() // Conversion Date SQL -> LocalDate
                );

                deponses.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des dépenses : " + e.getMessage());
        }
        return deponses;
    }

    @Override
    public void update(deponse deponse) {
        String qry = "UPDATE `deponse` SET `id_produit`=?, `quantite_produit`=?, `prix_achat`=?, `Date_achat`=? WHERE `id_deponse`=?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, deponse.getId_produit());
            pstm.setInt(2, deponse.getQuantite_produit());
            pstm.setDouble(3, deponse.getPrix_achat());
            pstm.setDate(4, Date.valueOf(deponse.getDate_achat())); // Conversion LocalDate -> Date SQL
            pstm.setInt(5, deponse.getId_deponse());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la dépense : " + e.getMessage());
        }
    }

    @Override
    public void delete(deponse deponse) {
        String qry = "DELETE FROM deponse WHERE id_deponse = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, deponse.getId_deponse());
            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Dépense supprimée: " + deponse.getId_deponse());
            } else {
                System.out.println("Aucune dépense trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la dépense : " + e.getMessage());
        }
    }
}
