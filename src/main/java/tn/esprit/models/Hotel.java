package tn.esprit.models;

public class Hotel {
    private int id;
    private String nom;
    private String ville;
    private double prixParNuit;
    private boolean disponible;
    private int nombreEtoile;       // Nouvel attribut
    private String typeDeChambre;   // Nouvel attribut

    // Constructeur
    public Hotel(String nom, String ville, double prixParNuit, boolean disponible, int nombreEtoile, String typeDeChambre) {
        this.nom = nom;
        this.ville = ville;
        this.prixParNuit = prixParNuit;
        this.disponible = disponible;
        this.nombreEtoile = nombreEtoile;
        this.typeDeChambre = typeDeChambre;
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

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", ville='" + ville + '\'' +
                ", prixParNuit=" + prixParNuit +
                ", disponible=" + disponible +
                ", nombreEtoile=" + nombreEtoile +
                ", typeDeChambre='" + typeDeChambre + '\'' +
                '}';
    }
}