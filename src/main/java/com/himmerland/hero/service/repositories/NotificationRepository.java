package com.himmerland.hero.service.repositories;

import com.himmerland.hero.domain.notifications.Notification;

import java.nio.file.Path;

import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepository extends BaseRepository<Notification> {
    
    public NotificationRepository(Path dataDir) {
        super(dataDir, "notifications", Notification.class);
    }

    @Override
    protected Class<Notification> getEntityClass() {
        return Notification.class;
    }
}
