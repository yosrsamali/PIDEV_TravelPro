package tn.esprit.services;

import tn.esprit.models.Avis;
import tn.esprit.utils.MyDatabase;
import tn.esprit.interfaces.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAvis implements IService<Avis> {
    private Connection cnx;

    public ServiceAvis() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Avis a) {
        String qry = "INSERT INTO avis (note, commentaire, date_publication) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, a.getNote());
            pstm.setString(2, a.getCommentaire());
            pstm.setTimestamp(3, new Timestamp(a.getDate_publication().getTime()));
            pstm.executeUpdate();
            System.out.println("Avis ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'avis : " + e.getMessage());
        }
    }

    @Override
    public List<Avis> getAll() {
        List<Avis> avisList = new ArrayList<>();
        String qry = "SELECT * FROM avis";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Avis a = new Avis(
                        rs.getInt("id_avis"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date_publication")
                );
                avisList.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des avis : " + e.getMessage());
        }
        return avisList;
    }

    @Override
    public void update(Avis a) {
        String qry = "UPDATE avis SET note = ?, commentaire = ?, date_publication = ? WHERE id_avis = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, a.getNote());
            pstm.setString(2, a.getCommentaire());
            pstm.setTimestamp(3, new Timestamp(a.getDate_publication().getTime()));
            pstm.setInt(4, a.getId_avis());
            pstm.executeUpdate();
            System.out.println("Avis mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'avis : " + e.getMessage());
        }
    }

    @Override
    public void delete(Avis a) {
        String qry = "DELETE FROM avis WHERE id_avis = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, a.getId_avis());
            pstm.executeUpdate();
            System.out.println("Avis supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'avis : " + e.getMessage());
        }
    }
}
