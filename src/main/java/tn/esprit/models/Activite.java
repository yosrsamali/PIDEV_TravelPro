package tn.esprit.models;

import java.sql.Date;

public class Activite {
    private int idActivite;
    private String nomActivite;
    private String description;
    private Date dateDebutA; // Utilisation de java.sql.Date
    private Date dateFinA;   // Utilisation de java.sql.Date
    private int idEvent;

    public Activite() {}

    public Activite(String nomActivite, String description, Date dateDebutA, Date dateFinA, int idEvent) {
        this.nomActivite = nomActivite;
        this.description = description;
        this.dateDebutA = dateDebutA;
        this.dateFinA = dateFinA;
        this.idEvent = idEvent;
    }

    // Getters et setters
    public int getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(int idActivite) {
        this.idActivite = idActivite;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebutA() {
        return dateDebutA;
    }

    public void setDateDebutA(Date dateDebutA) {
        this.dateDebutA = dateDebutA;
    }

    public Date getDateFinA() {
        return dateFinA;
    }

    public void setDateFinA(Date dateFinA) {
        this.dateFinA = dateFinA;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "idActivite=" + idActivite +
                ", nomActivite='" + nomActivite + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutA=" + dateDebutA +
                ", dateFinA=" + dateFinA +
                ", idEvent=" + idEvent +
                '}';
    }


}