package tn.esprit.models;

public class PanierProduit {

    private int idPanier, idProduit, quantite;

    public PanierProduit() {
        this.quantite = 1; // Default value
    }

    public PanierProduit(int idPanier, int idProduit, int quantite) {
        this.idPanier = idPanier;
        this.idProduit = idProduit;
        this.quantite = quantite;
    }

    public PanierProduit(int idPanier, int idProduit) {
        this.idPanier = idPanier;
        this.idProduit = idProduit;
        this.quantite = 1; // Default value
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

    @Override
    public String toString() {
        return "PanierProduit{" +
                "idPanier=" + idPanier +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                '}';
    }
}
