package tn.esprit.models;

public class Panier {

    private int idPanier, idClient;
    private double montantTotal;

    public Panier() {
    }

    public Panier(int idPanier, int idClient, double montantTotal) {
        this.idPanier = idPanier;
        this.idClient = idClient;
        this.montantTotal = montantTotal;
    }

    public Panier(int idClient, double montantTotal) {
        this.idClient = idClient;
        this.montantTotal = montantTotal;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
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

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + idPanier +
                ", idClient=" + idClient +
                ", montantTotal=" + montantTotal +
                '}';
    }
}