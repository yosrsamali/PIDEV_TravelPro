package tn.esprit.test;

import tn.esprit.models.revenue;
import tn.esprit.services.ServiceRevenue;
import tn.esprit.utils.MyDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MainRevenue {
    public static void main(String[] args) {
        try {
            // Connexion à la base de données
            Connection connection = MyDatabase.getInstance().getCnx();
            ServiceRevenue serviceRevenue = new ServiceRevenue(connection);

            // 1. Ajouter une revenue avec des valeurs valides
            revenue nouvelleRevenue = new revenue(0, "vente_billet", new java.util.Date(), 1500.00);
            serviceRevenue.ajouterRevenue(nouvelleRevenue);
            System.out.println("✅ Revenue ajoutée avec succès !");

            // 2. Afficher toutes les revenues
            System.out.println("📌 Liste des revenues :");
            List<revenue> revenues = serviceRevenue.getAllRevenues();
            if (revenues.isEmpty()) {
                System.out.println("⚠️ Aucune revenue trouvée.");
            } else {
                for (revenue r : revenues) {
                    System.out.println(r);
                }
            }

            // 3. Modifier une revenue (exemple : changer le montant)
            if (!revenues.isEmpty()) {
                revenue revenueAModifier = revenues.get(0); // Prendre la première revenue
                revenueAModifier.setMontant_revenue(2000.00); // Nouveau montant
                serviceRevenue.updateRevenue(revenueAModifier);
                System.out.println("✅ Revenue mise à jour avec succès !");
            } else {
                System.out.println("⚠️ Impossible de modifier : aucune revenue trouvée.");
            }

            // 4. Supprimer une revenue (exemple : supprimer la première revenue)
            revenues = serviceRevenue.getAllRevenues(); // Recharger les revenues après modification
            if (!revenues.isEmpty()) {
                int idASupprimer = revenues.get(0).getId_revenue();
                serviceRevenue.deleteRevenue(idASupprimer);
                System.out.println("✅ Revenue supprimée avec succès !");
            } else {
                System.out.println("⚠️ Impossible de supprimer : aucune revenue trouvée.");
            }

            // 5. Afficher la liste après suppression
            System.out.println("📌 Liste après suppression :");
            revenues = serviceRevenue.getAllRevenues();
            if (revenues.isEmpty()) {
                System.out.println("✅ Plus aucune revenue enregistrée.");
            } else {
                for (revenue r : revenues) {
                    System.out.println(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Erreur de connexion à la base de données !");
        }
    }
}
