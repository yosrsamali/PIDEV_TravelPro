package tn.esprit.models;

import java.sql.Date; // Importer java.sql.Date pour compatibilité avec la BDD

public class evenement {
    private int idEvent;
    private String nomEvent;
    private String lieu;
    private Date dateDebutE; // Utiliser java.sql.Date
    private Date dateFinE;   // Utiliser java.sql.Date
    private String type;
    private String image;
    private int idReservation;
    private double latitude;
    private double longitude;


    public evenement() {}

    public evenement(String nomEvent, String lieu, Date dateDebutE, Date dateFinE, String type, int idReservation,String image, double latitude, double longitude) {
        this.nomEvent = nomEvent;
        this.lieu = lieu;
        this.dateDebutE = dateDebutE;
        this.dateFinE = dateFinE;
        this.type = type;
        this.idReservation = idReservation;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public evenement(String nomEvent, String lieu, Date dateDebutE, Date dateFinE, String type,String image) {
        this.nomEvent = nomEvent;
        this.lieu = lieu;
        this.dateDebutE = dateDebutE;
        this.dateFinE = dateFinE;
        this.type = type;
        this.image = image;

    }

    public evenement(int idEvent, String nomEvent, String lieu, Date dateDebutE, Date dateFinE, String type,String image,int idReservation) {
        this.idEvent = idEvent;
        this.nomEvent = nomEvent;
        this.idReservation = idReservation;
        this.image = image;
        this.type = type;
        this.dateFinE = dateFinE;
        this.dateDebutE = dateDebutE;
        this.lieu = lieu;
    }

    public int getIdEvent() { return idEvent; }
    public void setIdEvent(int idEvent) { this.idEvent = idEvent; }

    public String getNomEvent() { return nomEvent; }
    public void setNomEvent(String nomEvent) { this.nomEvent = nomEvent; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public Date getDateDebutE() { return dateDebutE; } // Reste en java.sql.Date
    public void setDateDebutE(Date dateDebutE) { this.dateDebutE = dateDebutE; } // Utiliser java.sql.Date

    public Date getDateFinE() { return dateFinE; } // Reste en java.sql.Date
    public void setDateFinE(Date dateFinE) { this.dateFinE = dateFinE; } // Utiliser java.sql.Date

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    @Override
    public String toString() {
        return "evenement{" +
                ", nomEvent='" + nomEvent + '\'' +
                ", lieu='" + lieu + '\'' +
                ", dateDebutE=" + dateDebutE +
                ", dateFinE=" + dateFinE +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", idReservation=" + idReservation +
                '}';
    }
}
