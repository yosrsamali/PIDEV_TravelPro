
package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Panier;
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
}