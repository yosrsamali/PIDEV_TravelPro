package tn.esprit.models;

import java.util.Date;

public class reservation {
    private int idReservation;
    private Date dateDebut;
    private Date dateFin;
    private String statut;
    private int idService; // Ajout de l'idService

    // Constructeur
    public reservation(int idReservation, Date dateDebut, Date dateFin, String statut, int idService) {
        this.idReservation = idReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.idService = idService;
    }

    public reservation() {}

    // Getters et Setters
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public int getIdService() { return idService; }
    public void setIdService(int idService) { this.idService = idService; }

    // Redéfinition de toString()
    @Override
    public String toString() {
        return "Reservation{" +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statut='" + statut + '\'' +
                ", idService=" + idService +
                '}';
    }
}
