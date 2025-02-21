package tn.esprit.models;

import java.time.LocalDateTime;
import java.util.List;

public class Commande {
    private int idCommande;
    private int idClient;
    private double montantTotal;
    private LocalDateTime dateCommande;
    private String status;
    private List<CommandeProduit> produits; // List of products in the order

    public Commande() {
        this.status = "pending"; // Default status
    }

    public Commande(int idClient, double montantTotal, List<CommandeProduit> produits) {
        this.idClient = idClient;
        this.montantTotal = montantTotal;
        this.dateCommande = LocalDateTime.now();
        this.status = "pending";
        this.produits = produits;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CommandeProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<CommandeProduit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", idClient=" + idClient +
                ", montantTotal=" + montantTotal +
                ", dateCommande=" + dateCommande +
                ", status='" + status + '\'' +
                ", produits=" + produits +
                '}';
    }
}
