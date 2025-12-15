package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification findCurrentNotification() {
        return notificationRepository.findAll().stream()
                .filter(Notification::isActive)
                .filter(n -> !n.isSent())
                .findFirst()
                .orElse(null);
    }

    public List<Notification> findAllActiveAndUnsent() {
        return notificationRepository.findAll().stream()
                .filter(Notification::isActive)
                .filter(n -> !n.isSent())
                .collect(Collectors.toList());
    }

}
