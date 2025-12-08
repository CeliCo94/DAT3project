package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.email.EmailService;
import org.springframework.stereotype.Service;

/**
 * NotificationChannel-implementering der sender via e-mail.
 */
@Service
public class EmailNotificationChannel implements NotificationChannel {

    private final EmailService emailService;

    public EmailNotificationChannel(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public String getName() {
        return "EMAIL";
    }

    @Override
    public void send(Notification notification) {
        emailService.sendNotificationEmail(notification);
    }
}
