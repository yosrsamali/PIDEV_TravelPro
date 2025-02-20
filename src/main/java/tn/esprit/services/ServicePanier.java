
package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Panier;
import tn.esprit.models.PanierProduit;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePanier implements IService<Panier> {
    private Connection cnx;

    public ServicePanier() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Panier panier) {
        String qry = "INSERT INTO `panier`(`id_client`, `montant_total`) VALUES (?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, panier.getIdClient());
            pstm.setDouble(2, panier.getMontantTotal());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<PanierProduit> getAllPanierProduits() {
        List<PanierProduit> cartItems = new ArrayList<>();
        try {
            String query = "SELECT pp.id_panier, pp.id_produit, pp.quantite, p.prix_vente " +
                    "FROM panier_produit pp " +
                    "JOIN produit p ON pp.id_produit = p.id_produit";

            PreparedStatement stmt = cnx.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PanierProduit panierProduit = new PanierProduit(
                        rs.getInt("id_panier"),
                        rs.getInt("id_produit"),
                        rs.getInt("quantite"),
                        rs.getDouble("prix_vente")
                );
                cartItems.add(panierProduit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    @Override
    public List<Panier> getAll() {
        List<Panier> paniers = new ArrayList<>();
        String qry = "SELECT * FROM `panier`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Panier p = new Panier();
                p.setIdPanier(rs.getInt("id_panier"));
                p.setIdClient(rs.getInt("id_client"));
                p.setMontantTotal(rs.getDouble("montant_total"));
                paniers.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return paniers;
    }

    @Override
    public void update(Panier panier) {
        String qry = "UPDATE `panier` SET `id_client`=?, `montant_total`=? WHERE `id_panier`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, panier.getIdClient());
            pstm.setDouble(2, panier.getMontantTotal());
            pstm.setInt(3, panier.getIdPanier());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Panier panier) {
        String qry = "DELETE FROM `panier` WHERE `id_panier`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, panier.getIdPanier());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to retrieve products by panier ID
    public List<PanierProduit> getPanierProduitsById(int panierId) {
        List<PanierProduit> panierProduits = new ArrayList<>();

        String query = "SELECT * FROM panier_produit	 WHERE id_panier = ?";

        try ( // Use your actual DB connection class
             PreparedStatement statement = cnx.prepareStatement(query)) {

            // Set the parameter for the query
            statement.setInt(1, panierId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                PanierProduit panierProduit = new PanierProduit();
                panierProduit.setIdPanier(resultSet.getInt("id_panier"));
                panierProduit.setIdProduit(resultSet.getInt("id_produit"));
                panierProduit.setQuantite(resultSet.getInt("quantite"));

                panierProduits.add(panierProduit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return panierProduits;
    }
}