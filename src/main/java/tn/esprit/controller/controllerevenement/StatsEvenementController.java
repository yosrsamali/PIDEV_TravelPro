package tn.esprit.controller.controllerevenement;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import tn.esprit.models.evenement;
import tn.esprit.services.ServiceEvenement;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class StatsEvenementController implements Initializable {

    @FXML private BarChart<String, Number> evenementBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private PieChart evenementPieChart;
    @FXML private Label mostPopularEventLabel;
    @FXML private Label averageDurationLabel;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateStatistics();
    }

    private void generateStatistics() {
        evenementBarChart.getData().clear();
        evenementPieChart.getData().clear();

        List<evenement> evenements = serviceEvenement.getAll();

        if (evenements.isEmpty()) {
            mostPopularEventLabel.setText("Aucun événement enregistré.");
            averageDurationLabel.setText("");
            return;
        }

        // 📊 Nombre d'événements par mois (BarChart)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre d'événements par mois");

        Map<String, Long> eventsPerMonth = evenements.stream()
                .collect(Collectors.groupingBy(
                        evt -> evt.getDateDebutE().toLocalDate().getMonth().toString(),
                        Collectors.counting()
                ));

        // Trouver le mois le plus actif
        Optional<Map.Entry<String, Long>> mostActiveMonth = eventsPerMonth.entrySet()
                .stream().max(Map.Entry.comparingByValue());

        mostActiveMonth.ifPresent(entry ->
                mostPopularEventLabel.setText("Mois le plus actif : " + entry.getKey() + " (" + entry.getValue() + " événements)")
        );

        eventsPerMonth.forEach((month, count) -> series.getData().add(new XYChart.Data<>(month, count)));
        evenementBarChart.getData().add(series);

        // ⏳ Durée moyenne des événements
        double averageDuration = evenements.stream()
                .mapToLong(evt -> evt.getDateFinE().getTime() - evt.getDateDebutE().getTime())
                .average()
                .orElse(0) / (1000 * 60 * 60 * 24); // Convertir en jours

        averageDurationLabel.setText("Durée moyenne : " + String.format("%.2f", averageDuration) + " jours");

        // 🎭 Répartition des types d'événements (PieChart)
        Map<String, Long> eventsPerType = evenements.stream()
                .collect(Collectors.groupingBy(evenement::getType, Collectors.counting()));

        evenementPieChart.setData(FXCollections.observableArrayList(
                eventsPerType.entrySet().stream()
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        ));
    }
}
