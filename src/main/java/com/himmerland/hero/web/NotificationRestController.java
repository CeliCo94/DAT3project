package com.himmerland.hero.web;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.nio.file.Path;


@RestController
@RequestMapping("/notifications")
public class NotificationRestController {
 
    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notification>> fetch() {
        List<Notification> notifications = ReadAllJsonToList.readAll(Path.of("data", "notifications"), Notification.class);
        return ResponseEntity.ok(notifications);
    }
 
}
