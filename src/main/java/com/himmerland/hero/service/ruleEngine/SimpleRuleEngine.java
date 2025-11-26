package com.himmerland.hero.service.ruleEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;

@Service
public class SimpleRuleEngine implements RuleEngine {

    private final Map<String, List<MeterRuleState>> meterRuleStates = new HashMap<>();

    private static final int DEFAULT_DURATION_MEASUREMENTS = 3;

    @Override

    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        System.out.println("SimpleRuleEngine is called from MonitoringService");

        List<Notification> notifications = new ArrayList<>();
        String meterNumber = measurement.getMeterNumber();
        System.out.println("MeterNumber is collected");

        List<MeterRuleState> statesForMeterRule = meterRuleStates.computeIfAbsent(
            meterNumber,
            id -> createInitialStatesForMeter(id)
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

    private List<MeterRuleState> createInitialStatesForMeter(String meterNumber) {
        List<MeterRuleState> list = new ArrayList<>();
        list.add(new MeterRuleState(meterNumber, DEFAULT_DURATION_MEASUREMENTS));
        return list;
    }
}
