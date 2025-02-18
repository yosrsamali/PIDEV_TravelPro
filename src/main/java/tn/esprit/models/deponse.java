package tn.esprit.models;

public class deponse {
    private int id_deponse;
    private int quantite_total;
    private double prix_achat;
    private double tva;
    private double total;

    // Constructeur sans arguments
    public deponse(int quantiteTotal, double prixAchat, double tva) {}

    // Constructeur avec tous les arguments (id, quantite, prix_achat, tva)
    public deponse(Integer id_deponse, Integer quantite_total, Double prix_achat, Double tva, Double total) {
        this.id_deponse = id_deponse;
        this.quantite_total = quantite_total;
        this.prix_achat = prix_achat;
        this.tva = tva;
        this.total = total;
    }

    // Getters et setters
    public int getId_deponse() {
        return id_deponse;
    }

    public void setId_deponse(int id_deponse) {
        this.id_deponse = id_deponse;
    }

    public int getQuantite_total() {
        return quantite_total;
    }

    public void setQuantite_total(int quantite_total) {
        this.quantite_total = quantite_total;
    }

    public double getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(double prix_achat) {
        this.prix_achat = prix_achat;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "deponse{" +
                "id_deponse=" + id_deponse +
                ", quantite_total=" + quantite_total +
                ", prix_achat=" + prix_achat +
                ", tva=" + tva +
                ", total=" + total +
                '}';
    }
}
