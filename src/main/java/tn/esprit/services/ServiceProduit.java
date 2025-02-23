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
        String qry = "INSERT INTO produit (nom_produit, prix_achat, quantite_produit, image_path) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, produit.getNomProduit());
            pstm.setDouble(2, produit.getPrixAchat());
            pstm.setInt(3, produit.getQuantiteProduit());
            pstm.setString(4, produit.getImagePath());

            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String qry = "SELECT * FROM produit";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Produit p = new Produit();
                p.setIdProduit(rs.getInt("id_produit"));
                p.setNomProduit(rs.getString("nom_produit"));
                p.setPrixAchat(rs.getDouble("prix_achat"));
                p.setQuantiteProduit(rs.getInt("quantite_produit"));
                p.setPrixVente(rs.getDouble("prix_vente"));
                p.setImagePath(rs.getString("image_path"));

                produits.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }

    @Override
    public void delete(Produit produit) {
        String qry = "DELETE FROM produit WHERE id_produit=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, produit.getIdProduit());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Produit produit) {
        try {
            String qry = "UPDATE produit SET nom_produit = ?, prix_achat = ?, quantite_produit = ?, image_path = ? WHERE id_produit = ?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, produit.getNomProduit());
            pstm.setDouble(2, produit.getPrixAchat());
            pstm.setInt(3, produit.getQuantiteProduit());
            pstm.setString(4, produit.getImagePath());
            pstm.setInt(5, produit.getIdProduit());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
