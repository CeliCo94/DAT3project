package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.service.ruleEngine.RuleEngine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.ruleEngine.RuleContext;

@Service
public class MonitoringService {
    private RuleEngine ruleEngine;

    public MonitoringService(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    public void handleNewMeasurement(Measurement measurement) {
        RuleContext ctx = new RuleContext();
        List<?> notifications = ruleEngine.onNewMeasurement(measurement, ctx);
        System.out.println("Notifications: " + notifications);
    }

}
