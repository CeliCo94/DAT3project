package com.himmerland.hero.service.notifications;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(Path dataDir) {
        this.repository = new NotificationRepository(dataDir);
    }

    public List<Notification> findAll() {
       return repository.findAll();
    }
    
}
