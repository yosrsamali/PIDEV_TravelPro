package tn.esprit.models;

import javafx.beans.property.*;

public class Produit {

    private IntegerProperty idProduit; // IntegerProperty instead of int
    private StringProperty nomProduit; // StringProperty instead of String
    private DoubleProperty prixAchat; // DoubleProperty instead of double
    private IntegerProperty quantiteProduit; // IntegerProperty instead of int
    // Default constructor
    private DoubleProperty prixVente; // DoubleProperty instead of double

    private StringProperty imagePath; // DoubleProperty instead of double

    public Produit() {
        this.idProduit = new SimpleIntegerProperty();
        this.nomProduit = new SimpleStringProperty();
        this.prixAchat = new SimpleDoubleProperty();
        this.quantiteProduit = new SimpleIntegerProperty();
        this.prixVente = new SimpleDoubleProperty();
        this.imagePath = new SimpleStringProperty();
    }

    public Produit(int productId) {
        this.idProduit = new SimpleIntegerProperty(productId);
        this.nomProduit = new SimpleStringProperty();
        this.prixAchat = new SimpleDoubleProperty();
        this.quantiteProduit = new SimpleIntegerProperty();
        this.prixVente = new SimpleDoubleProperty();
        this.imagePath = new SimpleStringProperty();
    }

    public Produit(String nomProduit, double prixAchat, int quantiteProduit, String selectedImagePath) {
        this.nomProduit = new SimpleStringProperty(nomProduit);
        this.prixAchat = new SimpleDoubleProperty(prixAchat);
        this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
        this.imagePath = new SimpleStringProperty(selectedImagePath);
    }

    // Getter and Setter for idProduit
    public int getIdProduit() {
        return idProduit.get();
    }

    public void setIdProduit(int idProduit) {
        this.idProduit.set(idProduit);
    }

    public IntegerProperty idProduitProperty() {
        return idProduit;
    }

    // Getter and Setter for nomProduit
    public String getNomProduit() {
        return nomProduit.get();
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit.set(nomProduit);
    }

    public StringProperty nomProduitProperty() {
        return nomProduit;
    }

    // Getter and Setter for prixAchat
    public double getPrixAchat() {
        return prixAchat.get();
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat.set(prixAchat);
    }

    public DoubleProperty prixAchatProperty() {
        return prixAchat;
    }

    // Getter and Setter for quantiteProduit
    public int getQuantiteProduit() {
        return quantiteProduit.get();
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit.set(quantiteProduit);
    }

    public IntegerProperty quantiteProduitProperty() {
        return quantiteProduit;
    }

    // Getter and Setter for prixVente
    public double getPrixVente() {
        return prixVente.get();
    }

    public void setPrixVente(double prixVente) {
        this.prixVente.set(prixVente);
    }
    // Getter and Setter for imagePath

    public String getImagePath() {
        return imagePath.get();
    }
    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public DoubleProperty prixVenteProperty() {
        return prixVente;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit.get() +
                ", nomProduit='" + nomProduit.get() + '\'' +
                ", prixAchat=" + prixAchat.get() +
                ", quantiteProduit=" + quantiteProduit.get() +
                ", prixVente=" + prixVente.get() +
                ", imagePath=" + imagePath.get() +
                '}';
    }
}
