package com.himmerland.hero.web;

import com.himmerland.hero.service.helperclasses.handlejson.ReadNotificationObjectFromJson;
import com.himmerland.hero.service.helperclasses.handlejson.WriteObjectToJson;
import com.himmerland.hero.service.notifications.Notification;
import com.himmerland.hero.service.email.EmailService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationRestController {

    private final EmailService emailService;

    public NotificationRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> fetch() {
        Notification notification = ReadNotificationObjectFromJson.readNoticationObjectFromJson();
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail() {
        Notification notification = ReadNotificationObjectFromJson.readNoticationObjectFromJson();
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        emailService.sendNotificationEmail(notification);
        notification.setSent(true);

        // Save updated notification (with sent = true)
        WriteObjectToJson.writeObjectToJson("src/main/resources/json/notifications.json", notification);

        return ResponseEntity.ok().build();
    }

}
