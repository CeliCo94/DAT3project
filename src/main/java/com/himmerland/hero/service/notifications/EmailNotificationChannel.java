package com.himmerland.hero.service.notifications;
import com.himmerland.hero.config.EmailProperties;
import com.himmerland.hero.domain.notifications.Notification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationChannel implements NotificationChannel {

    private final JavaMailSender mailSender;
    private final EmailProperties props;

    public EmailNotificationChannel(JavaMailSender mailSender, EmailProperties props) {
        this.mailSender = mailSender;
        this.props = props;
    }

    @Override
    public String getName() {
        return "EMAIL";
    }

    @Override
    public void send(Notification notification) {
        if (!props.autosend()) {
            return; 
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(props.from());
        msg.setTo(props.recipient());

        StringBuilder body = new StringBuilder();

        boolean isSummary =
                notification.getAddress() == null &&
                notification.getRule() == null &&
                notification.getCriticality() == null &&
                notification.getTimeStamp() == null;

        if (isSummary) {
            msg.setSubject("HERO - Daglige notifikationer");
            body.append("Der er nye notifikationer fra HERO-systemet.\n\n");
            body.append(safe(notification.getCause())); 
        } else {
            msg.setSubject("HERO notifikation - " + safe(notification.getAddress()));

            body.append("Der er en ny notifikation fra HERO-systemet.\n\n");
            body.append("Adresse: ").append(safe(notification.getAddress())).append("\n");
            body.append("Årsag: ").append(safe(notification.getCause())).append("\n");
            body.append("Regel: ").append(safe(notification.getRule())).append("\n");

            body.append("Kritikalitet: ");
            if (notification.getCriticality() != null) {
                body.append(notification.getCriticality().name());
            }
            body.append("\n");

            body.append("Tidspunkt: ").append(safe(notification.getTimeStamp())).append("\n");
            body.append("Aktiv: ").append(notification.isActive() ? "Ja" : "Nej").append("\n");
        }

        msg.setText(body.toString());
        mailSender.send(msg);
    }

    private String safe(String value) {
        return value != null ? value : "";
    }
}
