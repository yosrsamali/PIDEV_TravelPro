package tn.esprit.models;

import java.util.Date;

public class BilletAvion {
    private int id;
    private String compagnie;
    private String class_Billet;
    private String villeDepart;
    private String villeArrivee;
    private Date dateDepart;
    private Date dateArrivee;
    private double prix;

    // Constructeur avec ID
    public BilletAvion(int id, String compagnie, String class_Billet, String villeDepart, String villeArrivee, Date dateDepart, Date dateArrivee, double prix) {
        this.id = id;
        this.compagnie = compagnie;
        this.class_Billet = class_Billet;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.prix = prix;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public String getClass_Billet() {
        return class_Billet;
    }

    public void setClass_Billet(String class_Billet) {
        this.class_Billet = class_Billet;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override

    public String toString() {
        return getCompagnie() + " - " + getPrix() + " €";
    }
}