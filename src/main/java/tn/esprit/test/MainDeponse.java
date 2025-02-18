package tn.esprit.test;

import tn.esprit.models.deponse;
import tn.esprit.services.Servicedeponse;
import tn.esprit.utils.MyDatabase;

public class MainDeponse {

    public static void main(String[] args) {
        Servicedeponse sp = new Servicedeponse();
        sp.add(new deponse(18, 50, 20));

        System.out.println(sp.getAll());




    }
}