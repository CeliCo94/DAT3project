package com.himmerland.hero.service.ruleEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.domain.rules.RuleHeat;

@Service
public class SimpleRuleEngine implements RuleEngine {

    private final Map<String, List<MeterRuleState>> meterRuleStates = new HashMap<>();

    @Override
    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        List<Notification> notifications = new ArrayList<>();

        String meterNumber = measurement.getMeterNumber();

        List<MeterRuleState> statesForMeterRule = meterRuleStates.computeIfAbsent(
            meterNumber,
            id -> createInitialStatesForMeter(id, ruleContext)
        );

        for (MeterRuleState meterRuleState : statesForMeterRule) {
            System.out.println("Following MeterRuleState exists in list:" + meterRuleState);
        }

        for (MeterRuleState meterRuleState : statesForMeterRule) {
            notifications.addAll(meterRuleState.onNewMeasurement(measurement, ruleContext));
            System.out.println("notifications are added to list");
        }

        System.out.println("Notifications will be returned from SimpleRuleEngine to MonitoringService");
        return notifications;
    }

    private List<MeterRuleState> createInitialStatesForMeter(String meterId, RuleContext ruleContext) {
        List<MeterRuleState> instances = new ArrayList<>();
        
        Rule mockRule = createMockRule();
        
        instances.add(new MeterRuleState(meterId, mockRule));

        return instances;
    }

    private Rule createMockRule(){
        RuleHeat rule = new RuleHeat();
        return rule;

    }
}
