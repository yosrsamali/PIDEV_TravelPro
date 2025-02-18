package tn.esprit.models;

import java.util.Date;

public class revenue {
    private int id_revenue;
    private String source_revenue;
    private Date date_revenue;
    private double montant_revenue;

    public revenue() {
    }

    public revenue(int id_revenue, String source_revenue, Date date_revenue, double montant_revenue) {
        this.id_revenue = id_revenue;
        this.source_revenue = source_revenue;
        this.date_revenue = date_revenue;
        this.montant_revenue = montant_revenue;
    }

    public int getId_revenue() {
        return id_revenue;
    }

    public void setId_revenue(int id_revenue) {
        this.id_revenue = id_revenue;
    }

    public String getSource_revenue() {
        return source_revenue;
    }

    public void setSource_revenue(String source_revenue) {
        this.source_revenue = source_revenue;
    }

    public Date getDate_revenue() {
        return date_revenue;
    }

    public void setDate_revenue(Date date_revenue) {
        this.date_revenue = date_revenue;
    }

    public double getMontant_revenue() {
        return montant_revenue;
    }

    public void setMontant_revenue(double montant_revenue) {
        this.montant_revenue = montant_revenue;
    }

    @Override
    public String toString() {
        return "revenue{" +
                "id_revenue=" + id_revenue +
                ", source_revenue='" + source_revenue + '\'' +
                ", date_revenue=" + date_revenue +
                ", montant_revenue=" + montant_revenue +
                '}';
    }
}
