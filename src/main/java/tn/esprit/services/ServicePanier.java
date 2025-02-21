
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

    public void updateProductQuantity(int idPanier, int idProduit, int newQuantity) {
        String query = "UPDATE panier_produit SET quantite = ? WHERE id_panier = ? AND id_produit = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, idPanier);
            ps.setInt(3, idProduit);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void deleteProductFromCart(int idPanier, int idProduit) {
        String query = "DELETE FROM panier_produit WHERE id_panier = ? AND id_produit = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, idPanier);
            ps.setInt(2, idProduit);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve products by panier ID
    public List<PanierProduit> getPanierProduitsById(int panierId) {
        List<PanierProduit> panierProduits = new ArrayList<>();
        String query = "SELECT pp.id_panier, pp.id_produit, pp.quantite, p.nom_produit, p.prix_vente " +
                "FROM panier_produit pp " +
                "JOIN produit p ON pp.id_produit = p.id_produit " +
                "WHERE pp.id_panier = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, panierId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PanierProduit panierProduit = new PanierProduit();
                panierProduit.setIdPanier(rs.getInt("id_panier"));
                panierProduit.setIdProduit(rs.getInt("id_produit"));
                panierProduit.setQuantite(rs.getInt("quantite"));
                panierProduit.setNomProduit(rs.getString("nom_produit")); // Set product name
                panierProduit.setPrixVente(rs.getDouble("prix_vente"));

                panierProduits.add(panierProduit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return panierProduits;
    }

    public void ajouterAuPanier(int idPanier, int idProduit, int quantity) {
        String query = "INSERT INTO panier_produit (id_panier, id_produit, quantite) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantite = quantite + VALUES(quantite)";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {

            pstmt.setInt(1, idPanier);
            pstmt.setInt(2, idProduit);
            pstmt.setInt(3, quantity);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added to cart successfully!");
            } else {
                System.out.println("Failed to add product to cart.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}