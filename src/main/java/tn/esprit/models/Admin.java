package tn.esprit.models;

public class Admin {

    private int idAdmin;
    private int idUtilisateur;

    public Admin() {
    }

    public Admin(int idUtilisateur) {

        this.idUtilisateur = idUtilisateur;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "idAdmin=" + idAdmin +
                ", idUtilisateur=" + idUtilisateur +
                '}';
    }
}
