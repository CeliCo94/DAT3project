package com.himmerland.hero.web;

import com.himmerland.hero.service.notifications.Notification;
import com.himmerland.hero.service.notifications.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationRestController {

    private final NotificationService notificationService;

    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /notifications/fetch
    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> fetchLatestNotification() {
        Notification notification = notificationService.getLatestNotification();

        if (notification == null) {
            // Ingen notification endnu
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notification);
    }

    // POST /notifications/send-email
    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmailForLatestNotification() {

        Notification notification = notificationService.getLatestNotification();
        if (notification == null) {
            // Der er intet at sende mail om
            return ResponseEntity.notFound().build();
        }

        // Brug vores modulære service
        notificationService.sendEmail(notification);

        return ResponseEntity.ok().build();
    }
}
