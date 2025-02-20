package tn.esprit.models;

public class Client {

    private int idClient;
    private int idUtilisateur;
    private String numTel;
    private String adresse;

    public Client() {
    }

    public Client( int idUtilisateur, String numTel, String adresse) {

        this.idUtilisateur = idUtilisateur;
        this.numTel = numTel;
        this.adresse = adresse;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", idUtilisateur=" + idUtilisateur +
                ", numTel='" + numTel + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
