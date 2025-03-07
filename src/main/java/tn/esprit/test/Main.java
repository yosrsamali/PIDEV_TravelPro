package tn.esprit.test;
<<<<<<< HEAD
import tn.esprit.models.Produit;
import tn.esprit.models.Panier;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.ServiceProduit;
import tn.esprit.utils.MyDatabase;


import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceReponse;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceReponse serviceReponse = new ServiceReponse();
        Scanner scanner = new Scanner(System.in);


        System.out.println("\n🔹 Liste des réponses avant l'ajout :");
        List<Reponse> reponseList = serviceReponse.getAll();
        for (Reponse r : reponseList) {
            System.out.println(r);
        }


        Reponse newReponse = new Reponse(1, "Réponse très utile", new Date());
        serviceReponse.add(newReponse);
        System.out.println("\n✅ Réponse ajoutée avec succès !");


        System.out.println("\n🔹 Liste des réponses après l'ajout :");
        reponseList = serviceReponse.getAll();
        for (Reponse r : reponseList) {
            System.out.println(r);
        }


        System.out.print("\n🔹 Entrez l'ID de l'avis pour récupérer ses réponses : ");
        int avisId = scanner.nextInt();
        List<Reponse> reponsesByAvis = serviceReponse.getReponsesByAvisId(avisId);

        System.out.println("\n🔹 Réponses associées à l'avis ID " + avisId + " :");
        if (reponsesByAvis.isEmpty()) {
            System.out.println("Aucune réponse trouvée pour cet avis.");
        } else {
            for (Reponse r : reponsesByAvis) {
                System.out.println(r);
            }
        }

    }
}
=======


import tn.esprit.models.DemandeValidation;
import tn.esprit.services.ServiceDemandeValidation;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceDemandeValidation service = new ServiceDemandeValidation();

        // 🔹 1. Ajouter une nouvelle demande
        System.out.println("Ajout d'une demande...");
        DemandeValidation nouvelleDemande = new DemandeValidation(1); // Remplace 1 par un ID client valide
        service.add(nouvelleDemande);

        // 🔹 2. Récupérer toutes les demandes
        System.out.println("\nListe des demandes:");
        List<DemandeValidation> demandes = service.getAll();
        for (DemandeValidation d : demandes) {
            System.out.println(d);
        }

        // 🔹 3. Mettre à jour le statut d'une demande
        if (!demandes.isEmpty()) {
            int idDemande = demandes.get(0).getId();
            System.out.println("\nMise à jour du statut de la demande ID " + idDemande);
            service.updateStatut(idDemande, "Approuvé");
        }

        // 🔹 4. Récupérer une demande par ID
        if (!demandes.isEmpty()) {
            int idDemande = demandes.get(0).getId();
            System.out.println("\nRécupération de la demande ID " + idDemande);
            DemandeValidation demande = service.getById(idDemande);
            System.out.println(demande);
        }


        // 🔹 6. Vérifier la liste après suppression
        System.out.println("\nListe des demandes après suppression:");
        demandes = service.getAll();
        for (DemandeValidation d : demandes) {
            System.out.println(d);
        }
    }
}
>>>>>>> user
