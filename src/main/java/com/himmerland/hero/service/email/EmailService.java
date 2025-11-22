package com.himmerland.hero.service.email;

import com.himmerland.hero.service.notifications.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${hero.notification.recipient}")
    private String defaultRecipient;

    @Value("${hero.notification.from}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotificationEmail(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(defaultRecipient);
        message.setFrom(fromAddress);
        message.setSubject("HERO notifikation: " + notification.getRule());
        message.setText(buildBody(notification));
        mailSender.send(message);
    }

    private String buildBody(Notification n) {
        StringBuilder sb = new StringBuilder();
        sb.append("Der er opstået en notifikation i HERO.\n\n");
        sb.append("Adresse: ").append(n.getAddress()).append("\n");
        sb.append("Årsag: ").append(n.getCause()).append("\n");
        sb.append("Regel: ").append(n.getRule()).append("\n");
        sb.append("Kritikalitet: ").append(n.getCriticality()).append("\n");
        sb.append("Tidspunkt: ").append(n.getTimeStamp()).append("\n");
        return sb.toString();
    }
}
