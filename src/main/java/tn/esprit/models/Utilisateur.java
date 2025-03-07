package tn.esprit.models;

public class Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String password;
    protected String role;
    protected String codeVerification;
    protected boolean etat;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String mail, String password, String role, String codeVerification, boolean etat) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.codeVerification = codeVerification;
        this.etat = etat;
    }

    public Utilisateur(int id, String nom, String prenom, String mail, String password, String role, String codeVerification, boolean etat) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.codeVerification = codeVerification;
        this.etat = etat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCodeVerification() { return codeVerification; }
    public void setCodeVerification(String codeVerification) { this.codeVerification = codeVerification; }

    public boolean getEtat() { return etat; }
    public void setEtat(boolean etat) { this.etat = etat; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", codeVerification='" + codeVerification + '\'' +
                ", etat=" + etat +
                '}';
    }
}
