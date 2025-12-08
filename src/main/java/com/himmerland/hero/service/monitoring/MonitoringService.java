package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.service.ruleEngine.RuleEngine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.ruleEngine.RuleContext;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.notifications.NotificationNotifier;
import com.himmerland.hero.service.notifications.NotificationService;

@Service
public class MonitoringService {
    private RuleEngine ruleEngine;
    private final NotificationService notificationService;
    private final NotificationNotifier notificationNotifier;

    public MonitoringService(
            RuleEngine ruleEngine,
            NotificationService notificationService,
            NotificationNotifier notificationNotifier
    ) {
        this.ruleEngine = ruleEngine;
        this.notificationService = notificationService;
        this.notificationNotifier = notificationNotifier;
        System.out.println("MonitoringService has been instantiated");
    }

    public void handleNewMeasurement(Measurement measurement) {
        RuleContext ruleContext = new RuleContext();
        List<Notification> notifications = ruleEngine.onNewMeasurement(measurement, ruleContext);
        System.out.println("Number of notification in MonitoringService: " + notifications.size());

        for (Notification notification : notifications) {
            // 1) gem notifikationen
            notificationService.save(notification);
            // 2) autosend hvis hero.email.autosend = true
            notificationNotifier.sendNotificationIfAuto(notification);
        }
    }

}
