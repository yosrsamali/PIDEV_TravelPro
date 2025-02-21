package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Commande;
import tn.esprit.models.CommandeProduit;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande implements IService<Commande> {
    private Connection cnx;

    public ServiceCommande() {
        cnx = MyDatabase.getInstance().getCnx();
    }
    // Add a new order

    public void add(Commande commande) {
        try {
            String query = "INSERT INTO commande (id_client, montant_total, date_commande, status) VALUES (?, ?, NOW(), ?)";
            PreparedStatement stmt = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, commande.getIdClient());
            stmt.setDouble(2, commande.getMontantTotal());
            stmt.setString(3, commande.getStatus());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idCommande = generatedKeys.getInt(1);
                commande.setIdCommande(idCommande);
                for (CommandeProduit cp : commande.getProduits()) {
                    addCommandeProduit(idCommande, cp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add products to the order
    private void addCommandeProduit(int idCommande, CommandeProduit cp) {
        try {
            String query = "INSERT INTO commande_produit (id_commande, id_produit, quantite, prix_vente) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1, idCommande);
            stmt.setInt(2, cp.getIdProduit());
            stmt.setInt(3, cp.getQuantite());
            stmt.setDouble(4, cp.getPrixVente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all orders
    public List<Commande> getAll() {
        List<Commande> commandes = new ArrayList<>();
        try {
            String query = "SELECT * FROM commande";
            PreparedStatement stmt = cnx.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCommande(rs.getInt("id_commande"));
                commande.setIdClient(rs.getInt("id_client"));
                commande.setMontantTotal(rs.getDouble("montant_total"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setStatus(rs.getString("status"));
                commande.setProduits(getCommandeProduits(commande.getIdCommande()));

                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    @Override
    public void update(Commande commande) {

    }

    @Override
    public void delete(Commande commande) {

    }

    // Get products for a specific order
    private List<CommandeProduit> getCommandeProduits(int idCommande) {
        List<CommandeProduit> produits = new ArrayList<>();
        try {
            String query = "SELECT * FROM commande_produit WHERE id_commande = ?";
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                produits.add(new CommandeProduit(
                        rs.getInt("id_commande"),
                        rs.getInt("id_produit"),
                        rs.getInt("quantite"),
                        rs.getDouble("prix_vente")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }
}
