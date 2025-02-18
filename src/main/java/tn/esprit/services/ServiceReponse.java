package tn.esprit.services;

import tn.esprit.models.Reponse;
import tn.esprit.utils.MyDatabase;
import tn.esprit.interfaces.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IService<Reponse> {
    private Connection cnx;

    public ServiceReponse() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Reponse r) {
        String qry = "INSERT INTO reponse (id_avis, reponse, date_reponse) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, r.getId_avis());
            pstm.setString(2, r.getReponse());
            pstm.setTimestamp(3, new Timestamp(r.getDate_reponse().getTime()));
            pstm.executeUpdate();
            System.out.println("Réponse ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réponse : " + e.getMessage());
        }
    }

    @Override
    public List<Reponse> getAll() {
        List<Reponse> reponseList = new ArrayList<>();
        String qry = "SELECT * FROM reponse";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Reponse r = new Reponse(
                        rs.getInt("id_reponse"),
                        rs.getInt("id_avis"),
                        rs.getString("reponse"),
                        rs.getTimestamp("date_reponse")
                );
                reponseList.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réponses : " + e.getMessage());
        }
        return reponseList;
    }

    @Override
    public void update(Reponse r) {
        String qry = "UPDATE reponse SET id_avis = ?, reponse = ?, date_reponse = ? WHERE id_reponse = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, r.getId_avis());
            pstm.setString(2, r.getReponse());
            pstm.setTimestamp(3, new Timestamp(r.getDate_reponse().getTime()));
            pstm.setInt(4, r.getId_reponse());
            pstm.executeUpdate();
            System.out.println("Réponse mise à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la réponse : " + e.getMessage());
        }
    }

    @Override
    public void delete(Reponse r) {
        String qry = "DELETE FROM reponse WHERE id_reponse = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, r.getId_reponse());
            pstm.executeUpdate();
            System.out.println("Réponse supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réponse : " + e.getMessage());
        }
    }
}
