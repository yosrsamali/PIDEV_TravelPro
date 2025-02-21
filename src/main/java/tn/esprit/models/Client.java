package tn.esprit.models;

public class Client {
    private int idClient;
    private String adresseClient;
    private String numTelClient;

    // Constructors
    public Client() {}

    public Client(int idClient, String adresseClient, String numTelClient) {
        this.idClient = idClient;
        this.adresseClient = adresseClient;
        this.numTelClient = numTelClient;
    }

    public Client(String adresseClient, String numTelClient) {
        this.adresseClient = adresseClient;
        this.numTelClient = numTelClient;
    }

    // Getters & Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getAdresseClient() {
        return adresseClient;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }

    public String getNumTelClient() {
        return numTelClient;
    }

    public void setNumTelClient(String numTelClient) {
        this.numTelClient = numTelClient;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", adresseClient='" + adresseClient + '\'' +
                ", numTelClient='" + numTelClient + '\'' +
                '}';
    }
}
