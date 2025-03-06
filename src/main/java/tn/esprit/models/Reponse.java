package tn.esprit.models;

import java.util.Date;

public class Reponse {
    private int id_reponse;
    private int id_avis;
    private String reponse;
    private Date date_reponse;


    public Reponse(int id_reponse, int id_avis, String reponse, Date date_reponse) {
        this.id_reponse = id_reponse;
        this.id_avis = id_avis;
        this.reponse = reponse;
        this.date_reponse = date_reponse;
    }


    public Reponse(int id_avis, String reponse, Date date_reponse) {
        this.id_avis = id_avis;
        this.reponse = reponse;
        this.date_reponse = date_reponse;
    }


    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public int getId_avis() {
        return id_avis;
    }

    public void setId_avis(int id_avis) {
        this.id_avis = id_avis;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Date getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(Date date_reponse) {
        this.date_reponse = date_reponse;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", id_avis=" + id_avis +
                ", reponse='" + reponse + '\'' +
                ", date_reponse=" + date_reponse +
                '}';
    }
}