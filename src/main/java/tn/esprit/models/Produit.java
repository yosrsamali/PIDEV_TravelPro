package tn.esprit.models;

public class Produit {

    private int idProduit, quantiteProduit;
    private String nomProduit;
    private double prixProduit;

    public Produit() {
    }

    public Produit(int idProduit, String nomProduit, double prixProduit, int quantiteProduit) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.quantiteProduit = quantiteProduit;
    }

    public Produit(String nomProduit, double prixProduit, int quantiteProduit) {
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.quantiteProduit = quantiteProduit;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public double getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", prixProduit=" + prixProduit +
                ", quantiteProduit=" + quantiteProduit +
                '}';
    }
}