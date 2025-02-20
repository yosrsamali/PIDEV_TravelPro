package tn.esprit.test;

import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceReponse;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceReponse serviceReponse = new ServiceReponse();
        Scanner scanner = new Scanner(System.in);

        // 1️⃣ Afficher les réponses avant l'ajout
        System.out.println("\n🔹 Liste des réponses avant l'ajout :");
        List<Reponse> reponseList = serviceReponse.getAll();
        for (Reponse r : reponseList) {
            System.out.println(r);
        }

        // 2️⃣ Ajouter une nouvelle réponse
        Reponse newReponse = new Reponse(1, "Réponse très utile", new Date());
        serviceReponse.add(newReponse);
        System.out.println("\n✅ Réponse ajoutée avec succès !");

        // 3️⃣ Afficher les réponses après l'ajout
        System.out.println("\n🔹 Liste des réponses après l'ajout :");
        reponseList = serviceReponse.getAll();
        for (Reponse r : reponseList) {
            System.out.println(r);
        }

        // 4️⃣ Tester la récupération des réponses par avis_id
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