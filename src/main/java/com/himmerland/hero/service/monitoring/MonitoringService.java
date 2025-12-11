package com.himmerland.hero.service.monitoring;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.service.repositories.MeterRepository;
import com.himmerland.hero.service.ruleEngine.RuleContext;
import com.himmerland.hero.service.ruleEngine.RuleEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    private final RuleEngine ruleEngine;
    private final MeterRepository meterRepository;
    private final RuleContext ruleContext;

    public MonitoringService(RuleEngine ruleEngine, MeterRepository meterRepository, RuleContext ruleContext) {
        this.ruleEngine = ruleEngine;
        this.meterRepository = meterRepository;
        this.ruleContext = ruleContext;
    }

    public void handleNewMeasurement(Measurement measurement) {
        List<Notification> notifications =
                ruleEngine.onNewMeasurement(measurement, ruleContext);

        System.out.println("Notifications: " + notifications.size());
    }
}
