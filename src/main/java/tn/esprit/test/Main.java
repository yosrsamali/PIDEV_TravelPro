package tn.esprit.test;


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
