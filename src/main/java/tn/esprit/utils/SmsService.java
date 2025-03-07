package tn.esprit.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {
    public static final String ACCOUNT_SID ="";
    public static final String AUTH_TOKEN = "";
    public static final String TWILIO_PHONE_NUMBER ="+18575209256";

    public static void sendSms(String numeroDestinataire, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new PhoneNumber(numeroDestinataire), // Numéro du destinataire
                new PhoneNumber(TWILIO_PHONE_NUMBER), // Ton numéro Twilio
                message
        ).create();
        System.out.println("✅ SMS envoyé à " + numeroDestinataire);
    }
}
