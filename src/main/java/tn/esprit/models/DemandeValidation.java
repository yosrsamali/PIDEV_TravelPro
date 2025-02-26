package tn.esprit.models;

import java.time.LocalDateTime;

public class DemandeValidation {
    private int id;
    private int id_client;
    private String statut;
    private LocalDateTime dateDemande;
    private String nomClient;

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public DemandeValidation() {

    }

    public DemandeValidation(int id, int id_client, String statut, LocalDateTime dateDemande) {
        this.id = id;
        this.id_client = id_client;
        this.statut = statut;
        this.dateDemande = dateDemande;
    }

    public DemandeValidation( int id_client) {

        this.id_client = id_client;
        this.statut = "En attente";
        this.dateDemande = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getidClient() {
        return id_client;
    }

    public void setidClient(int id_client) {
        this.id_client = id_client;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    @Override
    public String toString() {
        return "DemandeValidation{" +
                "id=" + id +
                ", id_client=" + id_client +
                ", statut='" + statut + '\'' +
                ", dateDemande=" + dateDemande +
                '}';
    }
}
