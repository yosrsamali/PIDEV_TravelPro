package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.deponse;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicedeponse implements IService<deponse> {
    private Connection cnx ;

    public Servicedeponse(){
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(deponse deponse) {
        // SQL query to insert a new deponse
        String qry = "INSERT INTO `deponse`(`quantite_total`, `prix_achat`, `tva`) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, deponse.getQuantite_total());
            pstm.setDouble(2, deponse.getPrix_achat());
            pstm.setDouble(3, deponse.getTva());

            // Execute the insert operation
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la dépense : " + e.getMessage());
        }
    }

    @Override
    public List<deponse> getAll() {
        List<deponse> deponses = new ArrayList<>();
        String qry = "SELECT * FROM `deponse`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                // Créer un objet deponse en utilisant les données récupérées de la base de données
                deponse d = new deponse(

                        rs.getInt("id_deponse"),
                        rs.getInt("quantite_total"),
                        rs.getDouble("tva"),
                        rs.getDouble("prix_achat"),
                        rs.getDouble("total")
                );

                // Initialiser l'ID de la dépense
                d.setId_deponse(rs.getInt("id_deponse"));

                // Ajouter l'objet deponse à la liste
                deponses.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des dépenses : " + e.getMessage());
        }

        return deponses;
    }

    @Override
    public void update(deponse deponse) {
        // Cette méthode peut être implémentée pour mettre à jour une dépense existante.
        String qry = "UPDATE `deponse` SET `quantite_total`=?, `prix_achat`=?, `tva`=? WHERE `id_deponse`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, deponse.getQuantite_total());
            pstm.setDouble(2, deponse.getPrix_achat());
            pstm.setDouble(3, deponse.getTva());
            pstm.setInt(4, deponse.getId_deponse());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la dépense : " + e.getMessage());
        }
    }

    @Override
    public void delete(deponse deponse) {
        // Requête SQL pour supprimer la dépense par ID
        String qry = "DELETE FROM deponse WHERE id_deponse = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            // Paramétrer l'ID de la dépense à supprimer
            pstm.setInt(1, deponse.getId_deponse());

            // Exécuter la requête de suppression
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

