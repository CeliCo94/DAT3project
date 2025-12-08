package com.himmerland.hero.web;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.notifications.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {
 
  private final NotificationService notificationService;

  public NotificationRestController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Notification>> getAllNotifications() {
    return ResponseEntity.ok(notificationService.findAll());
  }
 
}
