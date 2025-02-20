package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Revenue;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RevenueService implements IService<Revenue> {
    private Connection cnx;

    public RevenueService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void add(Revenue revenue) {
        String qry = "INSERT INTO `Revenue`(`type_revenue`, `reference_id`, `date_revenue`, `montant_total`, `commission`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, revenue.getTypeRevenue());
            pstm.setString(2, revenue.getReferenceId());

            // Utilisez l'instance revenue pour appeler getDateRevenue()
            pstm.setDate(3, Date.valueOf(revenue.getDateRevenue())); // Utilisation de l'objet revenue
            pstm.setDouble(4, revenue.getMontantTotal());
            pstm.setDouble(5, revenue.getCommission());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                // Affichage de l'alerte
                System.out.println("✅ Revenue Ajouter avec succès : ");
            } else {
                System.out.println("⚠️ Aucune ligne insérée !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la revenue : " + e.getMessage());
        }
    }



    @Override
    public List<Revenue> getAll() {
        List<Revenue> revenues = new ArrayList<>();
        String qry = "SELECT * FROM `Revenue`";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                // Créez un objet Revenue à partir des données retournées
                Revenue revenue = new Revenue(
                        rs.getInt("id_revenue"),
                        rs.getString("type_revenue"),
                        rs.getString("reference_id"),
                        rs.getDate("date_revenue").toLocalDate(),  // Conversion de Date SQL à LocalDate
                        rs.getDouble("montant_total"),
                        rs.getDouble("commission")
                );

                revenues.add(revenue);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des revenues : " + e.getMessage());
        }
        return revenues;
    }

    @Override
    public void update(Revenue revenue) {
        String qry = "UPDATE `Revenue` SET `type_revenue`=?, `reference_id`=?, `date_revenue`=?, `montant_total`=?, `commission`=? WHERE `id_revenue`=?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, revenue.getTypeRevenue());
            pstm.setString(2, revenue.getReferenceId());
            pstm.setDate(3, Date.valueOf(revenue.getDateRevenue())); // Utilisation de l'objet revenue
            pstm.setDouble(4, revenue.getMontantTotal());
            pstm.setDouble(5, revenue.getCommission());
            pstm.setInt(6, revenue.getIdRevenue());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la revenue : " + e.getMessage());
        }
    }

    @Override
    public void delete(Revenue revenue) {
        String qry = "DELETE FROM `Revenue` WHERE id_revenue = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, revenue.getIdRevenue());
            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Revenue supprimée avec succès : " + revenue.getIdRevenue());
            } else {
                System.out.println("⚠️ Aucune revenue trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression de la revenue : " + e.getMessage());
        }
    }
}
