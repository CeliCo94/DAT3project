package com.himmerland.hero.service.ruleEngine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;

@Service
public class SimpleRuleEngine implements RuleEngine {

    @Override
    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {
        List<Notification> notifications = new ArrayList<>();

        if (measurement.getInfoCode() == 0) {
            Notification notification = new Notification();
            notification.setCause("This is a test");
            notifications.add(notification);
        }

        return notifications;
    }
}
