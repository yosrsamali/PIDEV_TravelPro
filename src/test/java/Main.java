import tn.esprit.models.Commande;
import tn.esprit.models.CommandeProduit;
import tn.esprit.models.Produit;
import tn.esprit.models.Panier;
import tn.esprit.services.ServiceCommande;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.ServiceProduit;
import tn.esprit.utils.MyDatabase;

import javax.naming.CompositeName;
import javax.naming.InvalidNameException;
import java.util.Arrays;

public class Main {

    private static Arrays Arrays;

    public static void main(String[] args) throws InvalidNameException {
        ServiceProduit sp = new ServiceProduit();

        ServiceCommande serviceCommande = new ServiceCommande();

// Create test order
        CommandeProduit cp1 = new CommandeProduit(0, 1, 2 ); // idCommande = 0 (temporary)
        CommandeProduit cp2 = new CommandeProduit(0, 2, 1, 50.00);
        CommandeProduit cp3 = new CommandeProduit(0, 3, 7, 5.00);
        CommandeProduit cp4 = new CommandeProduit(0, 4,  5, 22.00);

        Commande newOrder = new Commande(1, 110.00, Arrays.asList(cp1, cp2,cp3,cp4)); // id_client = 1

        // Add order

        serviceCommande.add(newOrder);
        System.out.println("Order added successfully!");

        // Get all orders
        System.out.println("\nAll Orders:");
        System.out.println(serviceCommande.getAll());





/*//Testing add,delete,update methods for produit
       // Testing add method
        System.out.println("Testing add method");
        sp.add(new Produit( "flouka", 10000, 100));
        sp.add(new Produit("jaraya", 20, 52));
        System.out.println(sp.getAll());

        /*
        // Testing delete method
        System.out.println("\nTesting delete method");
        Produit produitToDelete = new Produit( "jaraya", 9, 100);
        sp.delete(produitToDelete);
        System.out.println("Product deleted successfully!");
        System.out.println(sp.getAll());
/*
        // Testing update method
        System.out.println("\nTesting update method");
        Produit updatedProduit = new Produit(2, "updatedThermos", 25.5, 150);
        sp.update(updatedProduit);
        System.out.println("Product updated successfully!");
        System.out.println(sp.getAll());
    }
*/
/*      Testing add,delete,update methods for panier
        ServicePanier sp = new ServicePanier();

        // Testing add method
        System.out.println("Testing add method");
        sp.add(new Panier(1, 200.50));
        sp.add(new Panier(2, 350.75));
        System.out.println(sp.getAll());

        // Testing delete method
        System.out.println("\nTesting delete method");
        Panier panierToDelete = new Panier(1, 1, 200.50);
        sp.delete(panierToDelete);
        System.out.println("Panier deleted successfully!");
        System.out.println(sp.getAll());

        // Testing update method
        System.out.println("\nTesting update method");
        Panier updatedPanier = new Panier(2, 2, 500.00); // Assuming id_panier 2 exists
        sp.update(updatedPanier);
        System.out.println("Panier updated successfully!");
        System.out.println(sp.getAll());
*/
    }
}