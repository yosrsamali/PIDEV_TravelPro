package tn.esprit.models;

import java.time.LocalDate;

public class deponse {
    private int id_deponse;
    private int id_produit;
    private int quantite_produit;
    private double prix_achat;
    private LocalDate date_achat; // Utilisation de LocalDate pour la date

    // Constructeur vide
    public deponse() {}

    // Constructeur avec tous les champs
    public deponse(int id_deponse, int id_produit, int quantite_produit, double prix_achat, LocalDate date_achat) {
        this.id_deponse = id_deponse;
        this.id_produit = id_produit;
        this.quantite_produit = quantite_produit;
        this.prix_achat = prix_achat;
        this.date_achat = date_achat;
    }

    // Getters et Setters
    public int getId_deponse() {
        return id_deponse;
    }

    public void setId_deponse(int id_deponse) {
        this.id_deponse = id_deponse;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQuantite_produit() {
        return quantite_produit;
    }

    public void setQuantite_produit(int quantite_produit) {
        this.quantite_produit = quantite_produit;
    }

    public double getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(double prix_achat) {
        this.prix_achat = prix_achat;
    }

    public LocalDate getDate_achat() {
        return date_achat;
    }

    public void setDate_achat(LocalDate date_achat) {
        this.date_achat = date_achat;
    }

    @Override
    public String toString() {
        return "Deponse{" +
                "id_deponse=" + id_deponse +
                ", id_produit=" + id_produit +
                ", quantite_produit=" + quantite_produit +
                ", prix_achat=" + prix_achat +
                ", date_achat=" + date_achat +
                '}';
    }
}
