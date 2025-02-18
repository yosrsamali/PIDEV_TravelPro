package tn.esprit.models;

import java.util.Date;

public class Avis {
    private int id_avis;
    private int note ;
    private String commentaire;
    private Date date_publication;
    public Avis (int id_avis, int note, String commentaire, Date date_publication) {
        this.id_avis = id_avis;
        this.note = note;
        this.commentaire = commentaire;
        this.date_publication = date_publication;

    }
    public int getId_avis() {
        return id_avis;
    }
    public void setId_avis(int id_avis) {
        this.id_avis = id_avis;
    }
    public int getNote() {
        return note;
    }
    public void setNote(int note) {
        this.note = note;
    }
    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public Date getDate_publication() {
        return date_publication;
    }
    public void setDate_publication(Date date_publication) {
        this.date_publication = date_publication;
    }
    @Override
    public String toString() {
        return "Avis{" +
                "id_avis=" + id_avis +
                ", note=" + note +
                ", commentaire='" + commentaire + '\'' +
                ", date_publication=" + date_publication +
                '}';
    }
}
