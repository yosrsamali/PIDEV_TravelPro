package tn.esprit.models;

public class Produit {


    private int idProduit, quantiteProduit;
    private String nomProduit;
    private double prixAchat;
    private double prixVente;

    public Produit() {
    }
    public Produit(int idProduit) {
        this.idProduit = idProduit;
    }

    public Produit(int idProduit, String nomProduit, double prixAchat, int quantiteProduit, double prixVente) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.prixAchat = prixAchat;
        this.quantiteProduit = quantiteProduit;
        this.prixVente = prixVente;
    }

    public Produit(String nomProduit, double prixAchat, int quantiteProduit) {
        this.nomProduit = nomProduit;
        this.prixAchat = prixAchat;
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

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public double getPrixVente() {
        return prixVente;
    }

    // Assuming prixVente is calculated or retrieved directly from the database as a generated field.
    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", prixAchat=" + prixAchat +
                ", quantiteProduit=" + quantiteProduit +
                ", prixVente=" + prixVente +
                '}';
    }
}
