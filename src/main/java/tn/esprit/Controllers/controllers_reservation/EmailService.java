package tn.esprit.Controllers.controllers_reservation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private final String username; // Votre adresse e-mail
    private final String password; // Votre mot de passe

    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String to, String subject, String htmlContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html");

            System.out.println("Envoi de l'e-mail à : " + to);
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}