package com.himmerland.hero.service.email;

import com.himmerland.hero.config.EmailProperties;
import com.himmerland.hero.domain.notifications.Notification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties props;

    public EmailService(JavaMailSender mailSender, EmailProperties props) {
        this.mailSender = mailSender;
        this.props = props;
    }

    /**
     * Sender en simpel tekst-mail baseret på en Notification.
     */
    public void sendNotificationEmail(Notification notification) {
        SimpleMailMessage msg = new SimpleMailMessage();

        // Fra / til adresser
        msg.setFrom(props.from());
        msg.setTo(props.recipient());

        // Emne
        msg.setSubject("HERO notifikation - " + safe(notification.getAddress()));

        // Body
        StringBuilder body = new StringBuilder();
        body.append("Der er en ny notifikation fra HERO-systemet.\n\n");
        body.append("Adresse: ").append(safe(notification.getAddress())).append("\n");
        body.append("Årsag: ").append(safe(notification.getCause())).append("\n");
        body.append("Regel: ").append(safe(notification.getRule())).append("\n");
        body.append("Kritikalitet: ").append(
                notification.getCriticality() != null
                        ? notification.getCriticality().name()
                        : ""
        ).append("\n");
        body.append("Tidspunkt: ").append(safe(notification.getTimeStamp())).append("\n");
        body.append("Aktiv: ").append(notification.isActive() ? "Ja" : "Nej").append("\n");

        msg.setText(body.toString());

        // Send mailen
        mailSender.send(msg);
    }

    private String safe(String value) {
        return value != null ? value : "";
    }
}
