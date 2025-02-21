package tn.esprit.models;

public class CommandeProduit {
    private int idCommande;
    private int idProduit;
    private int quantite;
    private double prixVente;

    public CommandeProduit(int idCommande, int idProduit, int quantite, double prixVente) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }

    public CommandeProduit(int idCommande, int idProduit, int quantite) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.quantite = quantite;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
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
        return "CommandeProduit{" +
                "idCommande=" + idCommande +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                ", prixVente=" + prixVente +
                '}';
    }
}
