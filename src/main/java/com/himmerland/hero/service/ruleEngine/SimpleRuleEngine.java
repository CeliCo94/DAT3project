package com.himmerland.hero.service.ruleEngine;

import java.util.ArrayList;
import java.util.List;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;

public class SimpleRuleEngine implements RuleEngine {

    
    
    @Override
    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {
        List<Notification> notifications = new ArrayList<Notification>();
        return notifications;
    }
}
