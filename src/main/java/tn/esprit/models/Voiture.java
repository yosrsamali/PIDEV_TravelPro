package tn.esprit.models;

public class Voiture {

    private int id;
    private String marque;
    private String modele;
    private int annee;
    private double prixParJour;
    private boolean disponible;
    public Voiture( String marque, String modele, int annee, double prixParJour, boolean disponible) {

        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.prixParJour = prixParJour;
        this.disponible = disponible;
    }

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
                '}';
    }
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


}