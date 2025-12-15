package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class MeterRuleState {

    private final String meterNumber;
    private final Rule rule;

    private int consecutiveBrokenCount = 0;

    public MeterRuleState(String meterNumber, Rule rule) {
        this.meterNumber = meterNumber;
        this.rule = rule;
    }

    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        List<Notification> notifications = new ArrayList<>();

        boolean brokenNow = isBroken(measurement, ruleContext); // single-measurement check

        if (brokenNow) {
            consecutiveBrokenCount++;
            System.out.println("[MeterRuleState] meter=" + meterNumber
                    + " rule=" + rule.getName()
                    + " broken, count=" + consecutiveBrokenCount);
        } else {
            consecutiveBrokenCount = 0;
            System.out.println("[MeterRuleState] meter=" + meterNumber
                    + " rule=" + rule.getName()
                    + " reset");
        }

        int duration = rule.getDuration();

        if (duration > 0 && consecutiveBrokenCount >= duration) {
            Notification notification = buildNotification(measurement, ruleContext);
            notifications.add(notification);

            System.out.println("[MeterRuleState] meter=" + meterNumber
                    + " rule=" + rule.getName()
                    + " TRIGGERED notification");

            consecutiveBrokenCount = 0;
        }

        return notifications;
    }

    private boolean isBroken(Measurement measurement, RuleContext ruleContext) {
        return rule.isBroken(measurement);
    }

    private Notification buildNotification(Measurement measurement, RuleContext ruleContext) {

        Notification notification = new Notification(ruleContext.getDepartment(measurement.getAddress()), measurement.getAddress(), rule.getDescription(), rule.getName(), rule.getCriticality());

        return notification;
    }

}
