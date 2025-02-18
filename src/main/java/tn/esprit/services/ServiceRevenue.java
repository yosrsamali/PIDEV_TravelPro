package tn.esprit.services;

import tn.esprit.models.revenue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRevenue {
    private Connection connection;

    public ServiceRevenue(Connection connection) {
        this.connection = connection;
    }

    // Ajouter une revenue
    public void ajouterRevenue(revenue revenue) throws SQLException {
        String query = "INSERT INTO revenue (source_revenue, date_revenue, montant_revenue) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, revenue.getSource_revenue());
        preparedStatement.setDate(2, new java.sql.Date(revenue.getDate_revenue().getTime()));
        preparedStatement.setDouble(3, revenue.getMontant_revenue());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    // Récupérer toutes les revenues
    public List<revenue> getAllRevenues() throws SQLException {
        List<revenue> revenues = new ArrayList<>();
        String query = "SELECT * FROM revenue";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            revenue revenue = new revenue(
                    resultSet.getInt("id_revenue"),
                    resultSet.getString("source_revenue"),
                    resultSet.getDate("date_revenue"),
                    resultSet.getDouble("montant_revenue")
            );
            revenues.add(revenue);
        }

        resultSet.close();
        statement.close();
        return revenues;
    }

    // Mettre à jour une revenue
    public void updateRevenue(revenue revenue) throws SQLException {
        String query = "UPDATE revenue SET source_revenue = ?, date_revenue = ?, montant_revenue = ? WHERE id_revenue = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, revenue.getSource_revenue());
        preparedStatement.setDate(2, new java.sql.Date(revenue.getDate_revenue().getTime()));
        preparedStatement.setDouble(3, revenue.getMontant_revenue());
        preparedStatement.setInt(4, revenue.getId_revenue());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    // Supprimer une revenue
    public void deleteRevenue(int id) throws SQLException {
        String query = "DELETE FROM revenue WHERE id_revenue = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
