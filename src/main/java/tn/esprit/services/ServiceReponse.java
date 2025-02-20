package tn.esprit.services;

import tn.esprit.models.Reponse;
import tn.esprit.utils.MyDatabase;
import tn.esprit.interfaces.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceReponse implements IService<Reponse> {
    private final Connection cnx;

    public ServiceReponse() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Reponse r) {
        String qry = "INSERT INTO reponses_avis (id_avis, reponse, date_reponse) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, r.getId_avis());
            pstm.setString(2, r.getReponse());
            pstm.setTimestamp(3, r.getDate_reponse() != null ? new Timestamp(r.getDate_reponse().getTime()) : null);
            pstm.executeUpdate();

            // Récupérer l'ID généré
            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    r.setId_reponse(generatedId);
                    System.out.println("Réponse ajoutée avec succès avec ID : " + generatedId);

                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réponse : " + e.getMessage());
        }
    }

    @Override
    public List<Reponse> getAll() {
        List<Reponse> reponseList = new ArrayList<>();
        String qry = "SELECT * FROM reponses_avis";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {
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

    public Optional<Reponse> getById(int id) {
        String qry = "SELECT * FROM reponses_avis WHERE id_avis = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Reponse(
                            rs.getInt("id_reponse"),
                            rs.getInt("id_avis"),
                            rs.getString("reponse"),
                            rs.getTimestamp("date_reponse")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la réponse : " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Reponse> getReponsesByAvisId(int avisId) {
        List<Reponse> reponses = new ArrayList<>();
        String query = "SELECT * FROM reponses_avis WHERE id_avis = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, avisId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reponse reponse = new Reponse(
                        resultSet.getInt("id_reponse"),
                        resultSet.getInt("id_avis"),
                        resultSet.getString("reponse"),
                        resultSet.getTimestamp("date_reponse")
                );
                reponses.add(reponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reponses;
    }

    @Override
    public void update(Reponse r) {
        String qry = "UPDATE reponses_avis SET id_avis = ?, reponse = ?, date_reponse = ? WHERE id_reponse = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, r.getId_avis());
            pstm.setString(2, r.getReponse());
            pstm.setTimestamp(3, r.getDate_reponse() != null ? new Timestamp(r.getDate_reponse().getTime()) : null);
            pstm.setInt(4, r.getId_reponse());
            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Réponse mise à jour avec succès !");
            } else {
                System.out.println("Aucune réponse trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la réponse : " + e.getMessage());
        }
    }

    @Override
    public void delete(Reponse r) {
        String qry = "DELETE FROM reponses_avis WHERE id_reponse = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, r.getId_reponse());
            int rowsDeleted = pstm.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Réponse supprimée avec succès !");
            } else {
                System.out.println("Aucune réponse trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réponse : " + e.getMessage());
        }
    }
}
