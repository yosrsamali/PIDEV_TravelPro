package tn.esprit.test;

import tn.esprit.models.Hotel;
import tn.esprit.models.Reservation;
import tn.esprit.services.ServiceHotel;
import tn.esprit.models.Voiture;
import tn.esprit.services.ServiceReservation;
import tn.esprit.services.ServiceVoiture;
import tn.esprit.models.BilletAvion;
import tn.esprit.services.ServiceBilletAvion;
import tn.esprit.utils.MyDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

    public static void main(String[] args) {

        //----------------------------------------------------------------------
        ServiceHotel sh =new ServiceHotel();
        /* sh.add(new Hotel(58,"hotel","sahha beach","hammamet",120.0,true));
        Hotel hotelAModifier= new Hotel(58,"hotel"," beach","sousse",120.0,true);
        hotelAModifier.setId_service(2);
        sh.update(hotelAModifier);
        sh.getAll();
        Hotel hotelASupprimer=new Hotel(0,"","","",0.0,false);
        hotelASupprimer.setId_service(3);
        sh.delete(hotelASupprimer);
        System.out.println(sh.getAll()); */
        //------------------------------------------------------------------
        ServiceVoiture sv = new ServiceVoiture();
        /* sv.add(new Voiture(8,"voiture","mercedes","S",2018,250.0,true));
        Voiture voitureAModifer=new Voiture(8,"voiture","mercedes","E",2018,250.0,false);
        voitureAModifer.setId_service(1);
        sv.update(voitureAModifer);
        sv.delete(voitureAModifer);
        sv.getAll(); */
        //----------------------------------------------------------------------
        ServiceBilletAvion sba = new ServiceBilletAvion();
        /* Date d1 = null;
        Date d2 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d1 = sdf.parse("2025-07-15 10:00:00");
            d2 = sdf.parse("2025-05-15 10:00:00");
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion de la date : " + e.getMessage());
        }

        if (d1 != null && d2 != null)
            sba.add(new BilletAvion(100,"billet avion","Tunisair", "Business", "Tunis", "Palestine", d1, d2, 150.0));
            System.out.println(sba.getAll()); */
        //--------------------------------------------------------------------
        ServiceReservation sr = new ServiceReservation();
        /* sr.add( new Reservation(2,  2,  2,  1, "Dupont","Jean", "confirme" ));
        Reservation reservationAModifer=new Reservation(2,  2,  2,  1, "yassin","abida", "confirme" );
        reservationAModifer.setId_reservation(1);
        sr.update(reservationAModifer);
        sr.delete(reservationAModifer);
        sr.getAll(); */
        //-------------------------------------------------------------------------
    }
}