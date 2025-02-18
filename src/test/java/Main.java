import tn.esprit.models.Produit;
import tn.esprit.models.Panier;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.ServiceProduit;
import tn.esprit.utils.MyDatabase;

public class Main {

    public static void main(String[] args) {
        ServiceProduit sp = new ServiceProduit();
/*
      //Testing add,delete,update methods for produit


        // Testing add method
        System.out.println("Testing add method");
        sp.add(new Produit(1, "thermos", 20, 100));
        sp.add(new Produit("thermos", 20, 100));
        System.out.println(sp.getAll());
*/
        // Testing delete method
        System.out.println("\nTesting delete method");
        Produit produitToDelete = new Produit(4, "thermos", 20, 100);
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