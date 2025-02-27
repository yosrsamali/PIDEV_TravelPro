// StripePaymentService.java
package tn.esprit.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.util.HashMap;
import java.util.Map;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
public class StripePaymentService {

    public StripePaymentService() {
        // Initialize Stripe with your secret key
        Stripe.apiKey = "sk_test_51QwtaHCdbmFmls5V0sfvnIgsKczfEtmYEMeVr4cNKo3l9qnHPEw9BF4LvoYgYTf81qzWTacYP8ocJZkIk82Ro34n00Dp0XjyTn";
    }

    // Create a PaymentIntent
    public String createCheckoutSession(double amount) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://yourwebsite.com/success")
                    .setCancelUrl("https://yourwebsite.com/cancel")
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("usd")
                                    .setUnitAmount((long) (amount * 100))
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Payment for Cart")
                                            .build())
                                    .build())
                            .build())
                    .build();

            Session session = Session.create(params);
            return session.getUrl(); // Return the Stripe checkout URL
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Redirect to Stripe Checkout
    public void redirectToStripeCheckout(String clientSecret) {
        try {
            // Construct the Stripe Checkout URL
            String checkoutUrl = "https://buy.stripe.com/test_" + clientSecret;

            // Open the user's default web browser
            java.awt.Desktop.getDesktop().browse(new java.net.URI(checkoutUrl));
            System.out.println("Redirecting to: " + checkoutUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}