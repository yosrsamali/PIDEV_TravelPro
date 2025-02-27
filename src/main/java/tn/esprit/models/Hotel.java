package tn.esprit.models;

import java.sql.Date;

public class Hotel {
    private int id;
    private String nom;
    private String ville;
    private double prixParNuit;
    private boolean disponible;
    private int nombreEtoile;
    private String typeDeChambre;
    private Date dateCheckIn;  // Nouvelle colonne
    private Date dateCheckOut; // Nouvelle colonne

    // Constructeur avec les nouvelles colonnes
    public Hotel(String nom, String ville, double prixParNuit, boolean disponible, int nombreEtoile, String typeDeChambre, Date dateCheckIn, Date dateCheckOut) {
        this.nom = nom;
        this.ville = ville;
        this.prixParNuit = prixParNuit;
        this.disponible = disponible;
        this.nombreEtoile = nombreEtoile;
        this.typeDeChambre = typeDeChambre;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public double getPrixParNuit() {
        return prixParNuit;
    }

    public void setPrixParNuit(double prixParNuit) {
        this.prixParNuit = prixParNuit;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getNombreEtoile() {
        return nombreEtoile;
    }

    public void setNombreEtoile(int nombreEtoile) {
        this.nombreEtoile = nombreEtoile;
    }

    public String getTypeDeChambre() {
        return typeDeChambre;
    }

    public void setTypeDeChambre(String typeDeChambre) {
        this.typeDeChambre = typeDeChambre;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    @Override
    public String toString() {
        return getNom() + " - " + getVille() + " (" + getNombreEtoile() + " étoiles) - " + getPrixParNuit() + " €/nuit";
    }
}
