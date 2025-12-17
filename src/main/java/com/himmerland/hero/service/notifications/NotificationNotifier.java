package com.himmerland.hero.service.notifications;

import com.himmerland.hero.config.EmailProperties;
import com.himmerland.hero.domain.notifications.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationNotifier {

    private final NotificationService notificationService;
    private final List<NotificationChannel> channels;

    public NotificationNotifier(
            NotificationService notificationService,
            EmailProperties emailProperties,
            List<NotificationChannel> channels
    ) {
        this.notificationService = notificationService;
        this.channels = channels;
    }

    /**
     * Bruges af frontend til at hente den aktuelle notifikation.
     */
    public Notification getCurrentNotification() {
        return notificationService.findCurrentNotification();
    }

    /**
     * SEND NOTIFICATION:
     *  - loop over alle kanaler (email, sms, ...)
     *  - markér som sendt og gem
     */
    public boolean sendNotification(Notification notification) {
        if (notification == null) {
            return false;
        }

        for (NotificationChannel channel : channels) {
            channel.send(notification);
        }

        notification.setSent(true);
        notificationService.save(notification);

        return true;
    }

    /**
     * Bruges af REST-endpointet, der skal sende "den aktuelle" notifikation.
     */
    public boolean sendCurrentNotification() {
        Notification n = getCurrentNotification();
        return sendNotification(n);
    }

    public boolean sendDailySummary(Notification notification) {
        if (notification == null) {
            return false;
        }
        for (NotificationChannel channel : channels) {
            channel.send(notification);
        }
        return true;
    }
}
