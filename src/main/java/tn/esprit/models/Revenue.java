package tn.esprit.models;
import java.time.LocalDate;

public class Revenue {

    private int id_revenue;  // identifiant unique
    private String type_revenue;  // type de revenue (vente_produit, reservation_hotel, ...)
    private LocalDate date_revenue;  // date de la transaction
    private double montant_total;  // montant total de la revenue
    private double commission;  // commission générée

    // Constructeur par défaut
    public Revenue() {}

    // Constructeur avec tous les attributs
    public Revenue(int id_revenue, String type_revenue, LocalDate date_revenue, double montant_total, double commission) {
        this.id_revenue = id_revenue;
        this.type_revenue = type_revenue;
        this.date_revenue = date_revenue;
        this.montant_total = montant_total;
        this.commission = commission;
    }

    // Getters et Setters
    public int getIdRevenue() {
        return id_revenue;
    }

    public void setIdRevenue(int id_revenue) {
        this.id_revenue = id_revenue;
    }

    public String getTypeRevenue() {
        return type_revenue;
    }

    public void setTypeRevenue(String type_revenue) {
        this.type_revenue = type_revenue;
    }

    public LocalDate getDateRevenue() {
        return date_revenue;
    }

    public void setDateRevenue(LocalDate date_revenue) {
        this.date_revenue = date_revenue;
    }

    public double getMontantTotal() {
        return montant_total;
    }

    public void setMontantTotal(double montant_total) {
        this.montant_total = montant_total;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "Revenue{idRevenue=" + id_revenue + ", typeRevenue='" + type_revenue +
                "', dateRevenue=" + date_revenue + ", montantTotal=" + montant_total + ", commission=" + commission + "}";
    }
}
