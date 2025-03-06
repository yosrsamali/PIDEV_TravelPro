package tn.esprit.test;

import tn.esprit.models.deponse;
import tn.esprit.services.Servicedeponse;
import tn.esprit.utils.MyDatabase;
import java.time.LocalDate;

public class MainDeponse {

    public static void main(String[] args) {
        Servicedeponse sp = new Servicedeponse();
        sp.add(new deponse(1, 3, 20, 15.5,  LocalDate.parse("2025-02-12") ));

        System.out.println(sp.getAll());




    }
}