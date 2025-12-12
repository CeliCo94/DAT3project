package com.himmerland.hero.service.ruleEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.service.repositories.RuleRepository;

@Service
public class SimpleRuleEngine implements RuleEngine {

    private final Map<String, List<MeterRuleState>> meterRuleStates = new HashMap<>();
    private final RuleRepository ruleRepository;

    public SimpleRuleEngine(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        String meterNumber = measurement.getMeterNumber();
        System.out.println("[SimpleRuleEngine] New measurement for meter " + meterNumber);

        List<MeterRuleState> statesForMeter = meterRuleStates.computeIfAbsent(
            meterNumber,
            id -> createInitialStatesForMeter(id, ruleContext)
        );

        System.out.println("[SimpleRuleEngine] States for meter: " + statesForMeter.size());

        List<Notification> notifications = new ArrayList<>();
        for (MeterRuleState state : statesForMeter) {
            notifications.addAll(state.onNewMeasurement(measurement, ruleContext));
        }

        System.out.println("[SimpleRuleEngine] Notifications produced: " + notifications.size());
        return notifications;
    }

    private List<MeterRuleState> createInitialStatesForMeter(String meterNumber, RuleContext ruleContext) {

        String consumptionType = ruleContext.getConsumptionType(meterNumber);
        System.out.println("[SimpleRuleEngine] Creating states for meter " + meterNumber
                + " consumptionType=" + consumptionType);

        List<Rule> rulesForType = ruleRepository.findByConsumptionType(consumptionType);

        List<MeterRuleState> instances = new ArrayList<>();
        for (Rule rule : rulesForType) {
            instances.add(new MeterRuleState(meterNumber, rule));
        }

        System.out.println("[SimpleRuleEngine] Created " + instances.size() + " MeterRuleState objects");
        return instances;
    }
}
