package tn.esprit.models;

public class Admin extends Utilisateur {
    private int idAdmin;

    public Admin() {
    }
    public Admin(int id_Utilisateur) {
        this.id= id_Utilisateur;
    }

    public Admin(int id, String nom, String prenom, String mail, String password, String codeVerification, boolean etat) {
        super(id, nom, prenom, mail, password, "Admin",codeVerification,etat);
    }

    public Admin(int idAdmin, int id, String nom, String prenom, String mail, String password, String codeVerification, boolean etat) {
        super(id, nom, prenom, mail, password, "Admin",codeVerification,etat);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public String toString() {
        return super.toString() + " Admin{" +
                "idAdmin=" + idAdmin +
                '}';
    }
}
