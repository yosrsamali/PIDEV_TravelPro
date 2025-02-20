package tn.esprit.test;

import tn.esprit.models.Revenue;

import tn.esprit.services.RevenueService;

import tn.esprit.utils.MyDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainRevenue {
    public static void main(String[] args) {
        RevenueService rs = new RevenueService();
        rs.add(new Revenue(1, "billet_avion", "1a",  LocalDate.parse("2025-02-12"), 200, 14 ));

        System.out.println(rs.getAll());




    }
}
