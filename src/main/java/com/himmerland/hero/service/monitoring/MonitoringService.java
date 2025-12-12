package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.repositories.NotificationRepository;
import com.himmerland.hero.service.ruleEngine.RuleContext;
import com.himmerland.hero.service.ruleEngine.RuleEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    private final RuleEngine ruleEngine;
    private final NotificationRepository notificationService;
    private final RuleContext ruleContext;

    public MonitoringService(RuleEngine ruleEngine,
            NotificationRepository notificationService,
            RuleContext ruleContext) {
        this.ruleEngine = ruleEngine;
        this.notificationService = notificationService;
        this.ruleContext = ruleContext;
        System.out.println("[MonitoringService] Instantiated");
    }

    public void handleNewMeasurement(Measurement measurement) {
        System.out.println("[MonitoringService] New measurement received for meter "
                + measurement.getMeterNumber()
                + " (timestamp=" + measurement.getTimestamp() + ")");

        List<Notification> notifications = ruleEngine.onNewMeasurement(measurement, ruleContext);

        System.out.println("[MonitoringService] RuleEngine returned "
                + notifications.size() + " notifications");

        for (Notification notification : notifications) {
            notificationService.save(notification);
            System.out.println("[MonitoringService] Saved notification: "
                    + notification);
        }

        System.out.println("[MonitoringService] Finished handling measurement for meter "
                + measurement.getMeterNumber());
    }
}
