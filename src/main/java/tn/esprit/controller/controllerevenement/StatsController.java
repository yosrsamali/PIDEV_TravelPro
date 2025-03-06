package tn.esprit.controller.controllerevenement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import tn.esprit.models.Activite;
import tn.esprit.services.ServiceActivite;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class StatsController implements Initializable {

    @FXML private BarChart<String, Number> activiteBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private PieChart activitePieChart;
    @FXML private Label mostActiveMonthLabel;
    @FXML private Label averageDurationLabel;

    private ServiceActivite serviceActivite = new ServiceActivite();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateStatistics();
    }

    private void generateStatistics() {
        activiteBarChart.getData().clear();
        activitePieChart.getData().clear();

        List<Activite> activites = serviceActivite.getAll();

        if (activites.isEmpty()) {
            mostActiveMonthLabel.setText("Aucune activité enregistrée.");
            averageDurationLabel.setText("");
            return;
        }

        // 📊 Nombre d'activités par mois (BarChart)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre d'activités par mois");

        Map<String, Long> activitesParMois = activites.stream()
                .collect(Collectors.groupingBy(
                        activite -> activite.getDateDebutA().toLocalDate().getMonth().toString(),
                        Collectors.counting()
                ));

        // Trouver le mois le plus actif
        Optional<Map.Entry<String, Long>> moisMax = activitesParMois.entrySet()
                .stream().max(Map.Entry.comparingByValue());

        moisMax.ifPresent(entry -> mostActiveMonthLabel.setText("Mois le plus actif : " + entry.getKey() + " (" + entry.getValue() + " activités)"));

        activitesParMois.forEach((month, count) -> series.getData().add(new XYChart.Data<>(month, count)));
        activiteBarChart.getData().add(series);

        // 📌 Calcul de la durée moyenne des activités
        double averageDuration = activites.stream()
                .mapToLong(activite -> activite.getDateFinA().getTime() - activite.getDateDebutA().getTime())
                .average()
                .orElse(0) / (1000 * 60 * 60 * 24); // Convertir en jours

        averageDurationLabel.setText("Durée moyenne : " + String.format("%.2f", averageDuration) + " jours");
    }

}
