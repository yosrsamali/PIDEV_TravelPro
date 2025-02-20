package tn.esprit.models;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String password;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur( String nom, String prenom, String mail, String password, String role) {

        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }
    public Utilisateur( int id ,String nom, String prenom, String mail, String password, String role) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}