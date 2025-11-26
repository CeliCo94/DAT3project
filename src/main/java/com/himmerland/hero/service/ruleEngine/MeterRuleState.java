package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.service.helperclasses.enums.Criticality;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeterRuleState {
  
    private final String meterId;
    //private final String ruleId;
    private final int durationInMeasurements;
    private int consecutiveBrokenCount = 0;

    public MeterRuleState(String meterId, int duration) {
        this.meterId = meterId;
        //this.ruleId = ruleId;
        this.durationInMeasurements = duration;
    }

    // called every time a new measurement for this meter arrives
    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        List<Notification> notifications = new ArrayList(); 
        boolean brokenNow = isBroken(measurement, ruleContext); // single-measurement check

        if (brokenNow) {
            consecutiveBrokenCount++;
            System.out.println("Rule is broken. Counter is now:" + consecutiveBrokenCount);
        } else {
            consecutiveBrokenCount = 0;
            System.out.println("Counter is reset");
        }

        if (consecutiveBrokenCount >= durationInMeasurements) {
            Notification notification = buildNotification(measurement, ruleContext);
            notifications.add(notification);
            System.out.println("Notification is added to list");

            consecutiveBrokenCount = 0;
            System.out.println("Counter is reset");
        }

        System.out.println("Notifications will be returned from MeterRuleState to SimpleRuleEngine");
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
