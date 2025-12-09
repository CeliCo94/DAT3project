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

    public MonitoringService(RuleEngine ruleEngine, MeterRepository meterRepository) {
        this.ruleEngine = ruleEngine;
        this.meterRepository = meterRepository;
    }

    public void handleNewMeasurement(Measurement measurement) {
        RuleContext ruleContext = new RuleContext(meterRepository);

        List<Notification> notifications =
                ruleEngine.onNewMeasurement(measurement, ruleContext);

        System.out.println("Notifications: " + notifications.size());
    }
}