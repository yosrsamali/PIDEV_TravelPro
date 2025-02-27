package tn.esprit.models;

public class Client extends Utilisateur {
    private int idClient;
    private String numTel;
    private String adresse;
    private String imageUrl; // Nouveau champ pour stocker l'URL de l'image

    public Client() {
    }
/*
    public Client(int id, String nom, String prenom, String mail, String password, String numTel, String adresse, String codeVerification, boolean etat) {
        super(id, nom, prenom, mail, password, "Client",codeVerification,etat);
        this.numTel = numTel;
        this.adresse = adresse;
    }

    public Client(int idClient, int id, String nom, String prenom, String mail, String password, String numTel, String adresse, String codeVerification, boolean etat) {
        super(id, nom, prenom, mail, password, "Client",codeVerification,etat);
        this.idClient = idClient;
        this.numTel = numTel;
        this.adresse = adresse;
    }
*/
    public Client(int id, String nom, String prenom, String mail, String password, String numTel, String adresse, String codeVerification, boolean etat, String imageUrl) {
        super(id, nom, prenom, mail, password, "Client", codeVerification, etat);
        this.numTel = numTel;
        this.adresse = adresse;
        this.imageUrl = imageUrl;
    }

    public Client(int idClient, int id, String nom, String prenom, String mail, String password, String numTel, String adresse, String codeVerification, boolean etat, String imageUrl) {
        super(id, nom, prenom, mail, password, "Client", codeVerification, etat);
        this.idClient = idClient;
        this.numTel = numTel;
        this.adresse = adresse;
        this.imageUrl = imageUrl;
    }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public String getNumTel() { return numTel; }
    public void setNumTel(String numTel) { this.numTel = numTel; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public String toString() {
        return super.toString() + " Client{" +
                "idClient=" + idClient +
                ", numTel='" + numTel + '\'' +
                ", adresse='" + adresse + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
