package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.measurements.Measurement;

import java.util.List;

public interface RuleEngine {
    List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext);
}
