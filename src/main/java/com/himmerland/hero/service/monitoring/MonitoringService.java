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
        System.out.println("MonitoringService has been instantiated");
    }

    public void handleNewMeasurement(Measurement measurement) {
        RuleContext ruleContext = new RuleContext();
        List<?> notifications = ruleEngine.onNewMeasurement(measurement, ruleContext);
        System.out.println("Number of notification in MonitoringService: " + notifications.size());
    }

}