package tn.esprit.models;

import java.util.Date;

public class Voiture {

    private int id;
    private String marque;
    private String modele;
    private int annee;
    private double prixParJour;
    private boolean disponible;
    private Date dateDeLocation; // Nouvel attribut
    private Date dateDeRemise;   // Nouvel attribut

    // Constructeur complet
    public Voiture(int id, String marque, String modele, int annee, double prixParJour, boolean disponible, Date dateDeLocation, Date dateDeRemise) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.prixParJour = prixParJour;
        this.disponible = disponible;
        this.dateDeLocation = dateDeLocation;
        this.dateDeRemise = dateDeRemise;
    }

    // Constructeur par défaut
    public Voiture() {
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", annee=" + annee +
                ", prixParJour=" + prixParJour +
                ", disponible=" + disponible +
                ", dateDeLocation=" + dateDeLocation +
                ", dateDeRemise=" + dateDeRemise +
                '}';
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(double prixParJour) {
        this.prixParJour = prixParJour;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getDateDeLocation() {
        return dateDeLocation;
    }

    public void setDateDeLocation(Date dateDeLocation) {
        this.dateDeLocation = dateDeLocation;
    }

    public Date getDateDeRemise() {
        return dateDeRemise;
    }

    public void setDateDeRemise(Date dateDeRemise) {
        this.dateDeRemise = dateDeRemise;
    }
}