package tn.esprit.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
    private static final String EMAIL = "";
    private static final String PASSWORD = ""; // Mot de passe d'application











    public static void envoyerMail(String destinataire, String sujet, String codeVerification) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);

            // Contenu de l'email en HTML avec mise en forme du code de vérification
            String contenu = "<html><body>"
                    + "<p>Bonjour,</p>"
                    + "<p>Votre code de vérification est :</p>"
                    + "<p style='color:blue; font-size:30px; font-weight:bold;'>" + codeVerification + "</p>"
                    + "<p>Merci de nous rejoindre !</p>"
                    + "</body></html>";

            message.setContent(contenu, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("✅ Email envoyé avec succès à " + destinataire);
        } catch (MessagingException e) {
            System.out.println("❌ Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}
