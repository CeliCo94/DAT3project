package com.himmerland.hero.web;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.notifications.NotificationService;
import com.himmerland.hero.service.notifications.NotificationNotifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {
 
  private final NotificationService notificationService;
  private final NotificationNotifier notificationNotifier;

  public NotificationRestController(
            NotificationService notificationService,
            NotificationNotifier notificationNotifier
    ) {
        this.notificationService = notificationService;
        this.notificationNotifier = notificationNotifier;
    }

    // ---- GET /api/notifications/fetch ----
    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> fetchCurrentNotification() {
        Notification n = notificationNotifier.getCurrentNotification();
        return ResponseEntity.ok(n);
    }


    // ---- POST /api/notifications/send-email ----
    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmailForNotification() {
        boolean sent = notificationNotifier.sendCurrentNotification();

        if (!sent) {
            // Ingen aktiv notifikation at sende
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok().build(); // 200
    }

    // ---- EXISTING /api/notifications (list all) ----
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.findAll());
    }
}