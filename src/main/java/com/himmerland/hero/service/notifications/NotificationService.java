package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(Path dataDir) {
        this.repository = new NotificationRepository(dataDir);
    }

    public List<Notification> findAll() {
       return repository.findAll();
    }

    public Notification save(Notification notification) {
        return repository.save(notification);
    }
    
    public Notification findCurrentNotification() {
        return repository.findAll().stream()
                .filter(Notification::isActive)
                .filter(n -> !n.isSent())
                .findFirst()
                .orElse(null);
    }

    public List<Notification> findAllActiveAndUnsent() {
    return repository.findAll().stream()
            .filter(Notification::isActive)
            .filter(n -> !n.isSent())
            .collect(Collectors.toList());
    }
    
}
