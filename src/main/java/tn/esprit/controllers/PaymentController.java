package tn.esprit.controllers;

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

    public void initialize() {
        btnPay.setOnAction(event -> processPayment());
    }

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
        labelAmount.setText("Total Amount: " + amount + " USD");
    }

    private void processPayment() {
        String checkoutUrl = paymentService.createCheckoutSession(totalAmount);
        if (checkoutUrl != null) {
            showAlert("Redirecting to Payment", "Click OK to proceed.");
            try {
                Desktop.getDesktop().browse(new URI(checkoutUrl));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Payment initiation failed.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
