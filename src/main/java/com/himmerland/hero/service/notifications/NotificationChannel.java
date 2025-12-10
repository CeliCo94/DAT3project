package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;

/**
 * En kanal der kan sende en notifikation (email, sms, osv.).
 */
public interface NotificationChannel {
    String getName();
    void send(Notification notification);
}
