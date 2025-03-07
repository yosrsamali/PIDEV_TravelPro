package tn.esprit.services;

import tn.esprit.models.DemandeValidation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDemandeValidation {
    private Connection cnx;

    public ServiceDemandeValidation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Ajouter une demande de validation
    public void add(DemandeValidation demande) {
        String qry = "INSERT INTO demande_validation (id_client,date_demande) VALUES (?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, demande.getidClient());
            pstm.setTimestamp(2, Timestamp.valueOf(demande.getDateDemande()));

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    demande.setId(generatedKeys.getInt(1)); // Récupération de l'ID auto-généré
                }
                System.out.println("✅ Demande ajoutée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la demande : " + e.getMessage());
        }
    }

    // Récupérer toutes les demandes
    public List<DemandeValidation> getAll() {
        List<DemandeValidation> demandes = new ArrayList<>();
        String query = "SELECT d.id_client,d.id, u.nom_utilisateur AS nomUtilisateur, d.statut FROM demande_validation d JOIN client c ON d.id_client = c.id_client JOIN utilisateur u ON c.id_utilisateur = u.id_utilisateur";

        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DemandeValidation demande = new DemandeValidation();
                demande.setidClient(rs.getInt("id_client"));
                demande.setId(rs.getInt("id"));
                demande.setNomClient(rs.getString("nomUtilisateur"));
                demande.setStatut(rs.getString("statut"));
                demandes.add(demande);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des demandes : " + e.getMessage());
        }
        return demandes;
    }


    /*public List<DemandeValidation> getAll() {
        List<DemandeValidation> demandes = new ArrayList<>();
        String qry = "SELECT * FROM demande_validation";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                DemandeValidation demande = new DemandeValidation(
                        rs.getInt("id"),
                        rs.getInt("id_client"),
                        rs.getString("statut"),
                        rs.getTimestamp("date_demande").toLocalDateTime()
                );
                demandes.add(demande);
            }
            System.out.println("✅ Liste des demandes récupérée !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des demandes : " + e.getMessage());
        }

        return demandes;
    }

    */


    // Mettre à jour le statut d'une demande
    public void updateStatut(int id, String newStatut) {
        String qry = "UPDATE demande_validation SET statut = ? WHERE id = ?";
        try {

            PreparedStatement pstm = cnx.prepareStatement(qry);

            pstm.setString(1, newStatut);

            pstm.setInt(2, id);


            int rowsUpdated = pstm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Statut mis à jour !");
            } else {
                System.out.println("⚠️ Aucune modification, demande non trouvée.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }

    // Supprimer une demande par ID
    public void delete(int id) {
        String qry = "DELETE FROM demande_validation WHERE id = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Demande supprimée !");
            } else {
                System.out.println("⚠️ Aucune demande trouvée à supprimer.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression de la demande : " + e.getMessage());
        }
    }

    // Récupérer une demande par ID
    public DemandeValidation getById(int id) {
        String qry = "SELECT * FROM demande_validation WHERE id = ?";
        DemandeValidation demande = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                demande = new DemandeValidation(
                        rs.getInt("id"),
                        rs.getInt("id_client"),
                        rs.getString("statut"),
                        rs.getTimestamp("date_demande").toLocalDateTime()
                );
                System.out.println("✅ Demande récupérée avec succès !");
            } else {
                System.out.println("⚠️ Aucune demande trouvée avec l'ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération de la demande : " + e.getMessage());
        }

        return demande;
    }

    // Récupérer les demandes d'un client spécifique
    public List<DemandeValidation> getByClientId(int idClient) {
        List<DemandeValidation> demandes = new ArrayList<>();
        String qry = "SELECT * FROM demande_validation WHERE id_client = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, idClient);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                DemandeValidation demande = new DemandeValidation(
                        rs.getInt("id"),
                        rs.getInt("id_client"),
                        rs.getString("statut"),
                        rs.getTimestamp("date_demande").toLocalDateTime()
                );
                demandes.add(demande);
            }

            if (demandes.isEmpty()) {
                System.out.println("⚠️ Aucune demande trouvée pour le client ID: " + idClient);
            } else {
                System.out.println("✅ Liste des demandes du client récupérée !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des demandes : " + e.getMessage());
        }

        return demandes;
    }


    public boolean demandeExiste(int idClient) {
        String query = "SELECT COUNT(*) FROM demande_validation WHERE id_client = ? AND statut = 'En attente'";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, idClient);
            ResultSet rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Une demande en attente existe déjà
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la vérification de la demande : " + e.getMessage());
        }
        return false;
    }




}
