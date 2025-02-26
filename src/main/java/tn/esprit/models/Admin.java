package tn.esprit.models;

public class Admin extends Utilisateur {
    private int idAdmin;

    public Admin() {
    }
    public Admin(int id_Utilisateur) {
        this.id= id_Utilisateur;
    }

    public Admin(int id, String nom, String prenom, String mail, String password) {
        super(id, nom, prenom, mail, password, "Admin");
    }

    public Admin(int idAdmin, int id, String nom, String prenom, String mail, String password) {
        super(id, nom, prenom, mail, password, "Admin");
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
