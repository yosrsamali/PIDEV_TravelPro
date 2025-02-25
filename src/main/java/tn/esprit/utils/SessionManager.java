package tn.esprit.utils;
import tn.esprit.models.Client;
import tn.esprit.models.Utilisateur;

public class SessionManager {


    private static SessionManager instance;
    private Utilisateur utilisateurConnecte ;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }

    public Utilisateur getUtilisateurConnecte() {

        return utilisateurConnecte;
    }


    public boolean isUserLoggedIn() {
        return utilisateurConnecte != null;
    }

    public void logout() {
        utilisateurConnecte = null;
        System.out.println("✅ Déconnexion réussie !");
    }
}
