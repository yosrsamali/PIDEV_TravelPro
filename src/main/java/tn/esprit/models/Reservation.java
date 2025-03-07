package tn.esprit.models;

public class Reservation {
    private int id_reservation;
    private int id_voiture;
    private int id_billetAvion;
    private int id_hotel;
    private int id_client;
    private String statut;

    // Constructeur
    public Reservation(int id_voiture, int id_billetAvion, int id_hotel, int id_client,String statut) {
        this.id_voiture = id_voiture;
        this.id_billetAvion = id_billetAvion;
        this.id_hotel = id_hotel;
        this.id_client = id_client;
        this.statut = statut;
    }

    // Getters et Setters
    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getId_voiture() {
        return id_voiture;
    }

    public void setId_voiture(int id_voiture) {
        this.id_voiture = id_voiture;
    }

    public int getId_billetAvion() {
        return id_billetAvion;
    }

    public void setId_billetAvion(int id_billetAvion) {
        this.id_billetAvion = id_billetAvion;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }


    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", id_voiture=" + id_voiture +
                ", id_billetAvion=" + id_billetAvion +
                ", id_hotel=" + id_hotel +
                ", id_client=" + id_client +
                ", statut='" + statut + '\'' +
                '}';
    }
}