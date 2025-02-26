package tn.esprit.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendGridMailer {

    private static final String SMTP_SERVER = "smtp.sendgrid.net";
    private static final String SMTP_PORT = "465";  // Port SSL
    private static final String USERNAME = "apikey";  // Nom d'utilisateur fixe pour SendGrid
    private static final String PASSWORD = "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"; // Clé API SendGrid

    public static void sendEmail(String toEmail, String subject, String body) {
        // Configuration des propriétés SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");  // Activation de SSL

        // Création de la session avec authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("travelproagency123@gmail.com"));  // Expéditeur vérifié
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Envoi du message
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
            e.printStackTrace();
        }
    }
}