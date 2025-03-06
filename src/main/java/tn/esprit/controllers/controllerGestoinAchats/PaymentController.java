package tn.esprit.controllers.controllerGestoinAchats;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.esprit.services.StripePaymentService;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PaymentController {

    @FXML
    private Label labelAmount;
    @FXML
    private Button btnPay;

    private final StripePaymentService paymentService = new StripePaymentService();
    private double totalAmount;

    // Predefined exchange rate (1 TND = 0.32 USD, for example)
    private static final double EXCHANGE_RATE_TND_TO_USD = 0.32;

    public void initialize() {
        btnPay.setOnAction(event -> processPayment());
    }

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
        labelAmount.setText("Total Amount: " + amount + " TND");
    }

    private void processPayment() {
        // Convert TND to USD
        double amountInUSD = convertTndToUsd(totalAmount);

        // Send the converted amount to Stripe for payment
        String checkoutUrl = paymentService.createCheckoutSession(amountInUSD);
        if (checkoutUrl != null) {
            showAlert("Payment Processing", "Processing your payment...");
            try {
                Desktop.getDesktop().browse(new URI(checkoutUrl));
                // Continue with emptying cart and updating orders after redirect
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to redirect to Stripe.");
            }
        } else {
            showAlert("Error", "Payment initiation failed.");
        }
    }

    // Method to convert TND to USD
    private double convertTndToUsd(double tndAmount) {
        return tndAmount * EXCHANGE_RATE_TND_TO_USD;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
