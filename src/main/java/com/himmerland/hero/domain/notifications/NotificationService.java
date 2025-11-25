package com.himmerland.hero.domain.notifications;

import com.himmerland.hero.service.email.EmailService;
import com.himmerland.hero.service.helperclasses.handlejson.ReadNotificationObjectFromJson;
import com.himmerland.hero.service.helperclasses.handlejson.WriteObjectToJson;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final EmailService emailService;

    private static final String NOTIFICATION_FILE = "src/main/resources/json/notifications.json";

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Hent den seneste notification fra JSON-filen.
     */
    public Notification getLatestNotification() {
        return ReadNotificationObjectFromJson.readNoticationObjectFromJson();
    }

    /**
     * Gem en notification i JSON-filen.
     */
    public void saveNotification(Notification notification) {
        WriteObjectToJson.writeObjectToJson(NOTIFICATION_FILE, notification);
    }

    /**
     * Markér en notification som sendt og gem den.
     */
    public void markAsSent(Notification notification) {
        notification.setSent(true);
        saveNotification(notification);
    }

    /**
     * Send e-mail baseret på den seneste notification i filen.
     */
    public void sendLatestNotificationEmail() {
        Notification notification = getLatestNotification();
        if (notification == null) {
            // Der er ikke noget at sende
            return;
        }
        sendEmail(notification);
    }

    /**
     * Send e-mail for en given notification og markér den som sendt.
     */
    public void sendEmail(Notification notification) {
        emailService.sendNotificationEmail(notification);
        markAsSent(notification);
    }
}