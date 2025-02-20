package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Produit;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IService<Produit> {
    private Connection cnx;

    public ServiceProduit() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Produit produit) {
        // Insert the produit with the new fields
        String qry = "INSERT INTO `produit`(`nom_produit`, `prix_achat`, `quantite_produit`) VALUES (?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, produit.getNomProduit());
            pstm.setDouble(2, produit.getPrixAchat());
            pstm.setInt(3, produit.getQuantiteProduit());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String qry = "SELECT * FROM `produit`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Produit p = new Produit();
                p.setIdProduit(rs.getInt("id_produit"));
                p.setNomProduit(rs.getString("nom_produit"));
                p.setPrixAchat(rs.getDouble("prix_achat"));
                p.setQuantiteProduit(rs.getInt("quantite_produit"));

                // As prix_vente is generated in the database, we can retrieve it directly
                p.setPrixVente(rs.getDouble("prix_vente"));

                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return produits;
    }

    @Override
    public void update(Produit produit) {
        // Update the produit with the new fields
        String qry = "UPDATE `produit` SET `nom_produit`=?, `prix_achat`=?, `quantite_produit`=? WHERE `id_produit`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, produit.getNomProduit());
            pstm.setDouble(2, produit.getPrixAchat());
            pstm.setInt(3, produit.getQuantiteProduit());
            pstm.setInt(4, produit.getIdProduit());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Produit produit) {
        // Delete the produit based on its id
        String qry = "DELETE FROM `produit` WHERE `id_produit`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, produit.getIdProduit());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
