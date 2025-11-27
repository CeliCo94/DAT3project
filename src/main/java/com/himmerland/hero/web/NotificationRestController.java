package com.himmerland.hero.web;

import com.himmerland.hero.domain.notifications.Notification;
//import com.himmerland.hero.service.helperclasses.handlejson.ReadNotificationObjectFromJson;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationRestController {
/* 
    @GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> fetch() {
        Notification notification = ReadNotificationObjectFromJson.readNoticationObjectFromJson();
        return ResponseEntity.ok(notification);
    }
 */
}
