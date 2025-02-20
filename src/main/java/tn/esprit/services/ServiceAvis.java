package tn.esprit.services;

import tn.esprit.models.Avis;
import tn.esprit.utils.MyDatabase;
import tn.esprit.interfaces.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class ServiceAvis implements IService<Avis> {
    private final Connection cnx;

    public ServiceAvis() {
        this.cnx = MyDatabase.getInstance().getCnx();
    }

   /* @Override
    public void add(Avis a) {
        String qry = "INSERT INTO avis (note, commentaire, date_publication, est_accepte) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, a.getNote());
            pstm.setString(2, a.getCommentaire());
            pstm.setTimestamp(3, a.getDate_publication() != null ? new Timestamp(a.getDate_publication().getTime()) : null);
            pstm.setBoolean(4, a.isEstAccepte());
            pstm.executeUpdate();
            System.out.println("Avis ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'avis : " + e.getMessage());
        }
    }
*/
   @Override
   public void add(Avis a) {
       String qry = "INSERT INTO avis (note, commentaire, date_publication, est_accepte) VALUES (?, ?, ?, ?)";
       try (PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
           pstm.setInt(1, a.getNote());
           pstm.setString(2, a.getCommentaire());
           pstm.setTimestamp(3, a.getDate_publication() != null ? new Timestamp(a.getDate_publication().getTime()) : null);
           pstm.setBoolean(4, a.isEstAccepte());

           int affectedRows = pstm.executeUpdate();
           if (affectedRows > 0) {
               try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                   if (generatedKeys.next()) {
                       int generatedId = generatedKeys.getInt(1);
                       a.setId_avis(generatedId); // Mettre à jour l'objet Avis avec son ID
                       System.out.println("✅ Avis ajouté avec succès ! ID généré : " + generatedId);
                   } else {
                       System.out.println("⚠️ L'ajout a réussi, mais aucun ID n'a été retourné.");
                   }
               }
           }
       } catch (SQLException e) {
           System.err.println("❌ Erreur lors de l'ajout de l'avis : " + e.getMessage());
       }
   }



    @Override
    public List<Avis> getAll() {
        List<Avis> avisList = new ArrayList<>();
        String qry = "SELECT * FROM avis";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id_avis"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date_publication"),
                        rs.getBoolean("est_accepte")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des avis : " + e.getMessage());
        }
        return avisList;
    }

    public List<Avis> getAvisAcceptes() {
        List<Avis> avisAcceptes = new ArrayList<>();
        String qry = "SELECT * FROM avis WHERE est_accepte = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setBoolean(1, Boolean.TRUE);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    avisAcceptes.add(new Avis(
                            rs.getInt("id_avis"),
                            rs.getInt("note"),
                            rs.getString("commentaire"),
                            rs.getTimestamp("date_publication"),
                            rs.getBoolean("est_accepte")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des avis acceptés : " + e.getMessage());
        }
        return avisAcceptes;
    }

    public List<Avis> getAvisNonAcceptes() {
        List<Avis> avisNonAcceptes = new ArrayList<>();
        String qry = "SELECT * FROM avis WHERE est_accepte = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setBoolean(1, Boolean.FALSE); // 0 représente FALSE pour un champ BOOLEAN
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    avisNonAcceptes.add(new Avis(
                            rs.getInt("id_avis"),
                            rs.getInt("note"),
                            rs.getString("commentaire"),
                            rs.getTimestamp("date_publication"),
                            rs.getBoolean("est_accepte")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des avis non acceptés : " + e.getMessage());
        }

        return avisNonAcceptes;
    }



    @Override
    public void update(Avis a) {
        String qry = "UPDATE avis SET note = ?, commentaire = ?, date_publication = ?, est_accepte = ? WHERE id_avis = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, a.getNote());
            pstm.setString(2, a.getCommentaire());
            pstm.setTimestamp(3, a.getDate_publication() != null ? new Timestamp(a.getDate_publication().getTime()) : null);
            pstm.setBoolean(4, a.isEstAccepte());
            pstm.setInt(5, a.getId_avis());
            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Avis mis à jour avec succès !");
            } else {
                System.out.println("Aucun avis mis à jour. ID introuvable.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'avis : " + e.getMessage());
        }
    }

    @Override
    public void delete(Avis a) {
        String qry = "DELETE FROM avis WHERE id_avis = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, a.getId_avis());
            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Avis supprimé avec succès !");
            } else {
                System.out.println("Aucun avis supprimé. ID introuvable.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'avis : " + e.getMessage());
        }
    }


    public Optional<Avis> getById(int id) {
        String qry = "SELECT * FROM avis WHERE id_avis = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id); // On définit l'id de l'avis à récupérer
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) { // Si une ligne est trouvée dans le résultat
                    // Création d'un objet Avis avec les données récupérées de la base de données
                    Avis avis = new Avis(
                            rs.getInt("id_avis"),
                            rs.getInt("note"),
                            rs.getString("commentaire"),
                            rs.getTimestamp("date_publication"),
                            rs.getBoolean("est_accepte")
                    );
                    return Optional.of(avis); // Retourner l'objet Avis dans un Optional
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'avis : " + e.getMessage());
        }
        return Optional.empty(); // Si aucun avis n'est trouvé, on retourne un Optional vide
    }




}
