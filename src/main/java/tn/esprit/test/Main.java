package tn.esprit.test;
import java.util.*;

import tn.esprit.models.Client;
import tn.esprit.models.Admin;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceClient;
import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceUtilisateur;

public class Main {
    public static void main(String[] args) {

        {/*


        ServiceClient serviceClient = new ServiceClient();

        // Test récupération par ID
        int idClient = 3; // Remplacez par un ID valide dans votre base
        Client retrievedClient = serviceClient.getById(idClient);

        if (retrievedClient != null) {
            System.out.println(" Client trouvé : " + retrievedClient);
        } else {
            System.out.println(" Aucun client trouvé avec l'ID " + idClient);
        }

        ServiceAdmin serviceAdmin = new ServiceAdmin();

        // Test récupération par ID
        int idAdmin = 2; // Remplacez par un ID valide dans votre base
        Admin retrievedAdmin = serviceAdmin.getById(idAdmin);

        if (retrievedAdmin != null) {
            System.out.println(" Admin trouvé : " + retrievedAdmin);
        } else {
            System.out.println(" Aucun admin trouvé avec l'ID " + idAdmin);
        }








         // Test Service Utilisateur ***********************************************
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

        //  Ajout d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Dupont", "Jean", "jean.2358yuyguydddk@mail.com", "password123", "Client");
        serviceUtilisateur.add(utilisateur);

        //  Vérification de l'ID auto-généré
        System.out.println("ID de l'utilisateur ajouté : " + utilisateur.getId());

        //  Affichage de tous les utilisateurs après l'ajout
        System.out.println(" Liste des utilisateurs après ajout :");
        System.out.println(serviceUtilisateur.getAll());

        //  Mise à jour de l'utilisateur
        utilisateur.setNom("Martin");
        utilisateur.setPrenom("Paul");
        utilisateur.setMail("paul.martin@mail.com");
        utilisateur.setPassword("newpassword456");
        utilisateur.setRole("Admin");
        serviceUtilisateur.update(utilisateur);

        //  Vérification après mise à jour
        System.out.println(" Liste des utilisateurs après mise à jour :");
        System.out.println(serviceUtilisateur.getAll());

        //  Suppression de l'utilisateur
        serviceUtilisateur.delete(utilisateur);

        //  Vérification après suppression
        System.out.println("Liste des utilisateurs après suppression :");
        System.out.println(serviceUtilisateur.getAll());


        // Test Service Client **************************************************

        ServiceClient sc = new ServiceClient();

// Ajout d'un nouveau client
        Client client = new Client(4, "123456789", "123 Main Street");  // id_utilisateur = 4
        sc.add(client);

// Affichage de tous les clients
        System.out.println(sc.getAll());

// Mise à jour du client
        client.setIdUtilisateur(5);
        client.setNumTel("000000000");
        client.setAdresse("456 Another Street");
        sc.update(client);

// Vérification après mise à jour
        System.out.println(sc.getAll());

// Suppression du client
        sc.delete(client);

// Vérification après suppression
        System.out.println(sc.getAll());


        // Test Service  Admin **************************************************

        ServiceAdmin sa = new ServiceAdmin();

        // Ajout d'un nouvel admin
        Admin admin = new Admin(4);  // id_utilisateur = 1
        sa.add(admin);

        // Affichage de tous les admins
        System.out.println(sa.getAll());

        // Mise à jour de l'admin
        admin.setIdUtilisateur(5);
        sa.update(admin);

        // Vérification après mise à jour
        System.out.println(sa.getAll());

        // Suppression de l'admin
        sa.delete(admin);

        // Vérification après suppression
        System.out.println(sa.getAll());
    }


        ServiceClient serviceClient = new ServiceClient();

        // Ajout d'un client pour tester
        Client client = new Client(1, "555-1234", "10 Rue de Paris"); // id_utilisateur = 1
        serviceClient.add(client);

        //  Test récupération par ID
        int idClient = client.getIdClient();
        Client retrievedClient = serviceClient.getById(idClient);

        if (retrievedClient != null) {
            System.out.println(" Client trouvé : " + retrievedClient);
        } else {
            System.out.println(" Aucun client trouvé avec l'ID " + idClient);
        }
        */
        }

/*


ServiceClient serviceClient = new ServiceClient();

        //  Test récupération des détails d'un client par son ID
        int idClientTest = 2;  // Remplacez par un ID existant dans votre base de données
        List<Map<String, Object>> clientDetails = serviceClient.getDetailsClient(idClientTest);

        if (!clientDetails.isEmpty()) {
            for (Map<String, Object> details : clientDetails) {
                System.out.println(details);
            }
        } else {
            System.out.println(" Aucune donnée trouvée pour ce client.");
        }
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

        //  Test récupération des utilisateurs avec rôle "client"
        List<Utilisateur> clients = serviceUtilisateur.getClients();

        if (!clients.isEmpty()) {
            System.out.println(" Liste des clients récupérée avec succès !");
            for (Utilisateur u : clients) {
                System.out.println("ID: " + u.getId() + ", Nom: " + u.getNom() +
                        ", Prénom: " + u.getPrenom() + ", Email: " + u.getMail());
            }
        } else {
            System.out.println(" Aucun utilisateur trouvé avec le rôle 'client'.");
        }









        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

        //  Récupération de tous les admins
        List<Utilisateur> admins = serviceUtilisateur.getAllAdmin();

        if (!admins.isEmpty()) {
            System.out.println(" Liste des admins trouvés :");
            for (Utilisateur admin : admins) {
                System.out.println(admin);
            }
        } else {
            System.out.println(" Aucun admin trouvé !");
        }


*/








        ServiceAdmin serviceAdmin = new ServiceAdmin();

        // 🔍 Test récupération des détails d'un administrateur par son ID
        int idAdminTest = 2;  // Remplacez par un ID existant dans votre base de données
        List<Map<String, Object>> adminDetails = serviceAdmin.getDetailsAdmin(idAdminTest);

        if (!adminDetails.isEmpty()) {
            for (Map<String, Object> details : adminDetails) {
                System.out.println(details);
            }
        } else {
            System.out.println(" Aucune donnée trouvée pour cet administrateur.");
        }





    }
}