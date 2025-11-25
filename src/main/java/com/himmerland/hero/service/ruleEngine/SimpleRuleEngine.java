package com.himmerland.hero.service.ruleEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.helperclasses.enums.Criticality;

@Service
public class SimpleRuleEngine implements RuleEngine {

    private final Map<String, Integer> violationCounters = new HashMap<>();

    private static final int DURATION_MEASUREMENTS = 3;

    @Override

    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        List<Notification> notifications = new ArrayList<>();

        String meterNumber = measurement.getMeterNumber();
        boolean brokenNow = isBroken(measurement, ruleContext);

        int currentCount = violationCounters.getOrDefault(meterNumber, 0);

        if (brokenNow) {
            currentCount++;
        } else {
            currentCount = 0;
        }

        violationCounters.put(meterNumber, currentCount);

        if (currentCount >= DURATION_MEASUREMENTS && brokenNow) {
            Notification notification = buildNotification(measurement, ruleContext)
            notifications.add(notification);
        }

        return notifications;
    }

    private boolean isBroken(Measurement measurement, RuleContext ruleContext) {
        return measurement.getInfoCode() == 0;
    }

    private Notification buildNotification(Measurement measurement, RuleContext ruleContext) {

        Notification notification = new Notification("Test address",
                "Test for SimpelRuleEngine", "Content1",
                Criticality.Low, "2024-01-01T02:00:00Z", false, false);

        return notification;
    }
}
