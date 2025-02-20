package tn.esprit.models;

public class PanierProduit {
    private int idPanier, idProduit, quantite;
    private double prixVente; // New attribute for displaying only prix_vente

    public PanierProduit() {
        this.quantite = 1; // Default value
    }

    public PanierProduit(int idPanier, int idProduit, int quantite, double prixVente) {
        this.idPanier = idPanier;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    @Override
    public String toString() {
        return "PanierProduit{" +
                "idPanier=" + idPanier +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                ", prixVente=" + prixVente +
                '}';
    }
}
