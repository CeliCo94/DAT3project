package com.himmerland.hero.service.ruleEngine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.domain.rules.RuleHeat;
import com.himmerland.hero.service.repositories.RuleRepository;

@Service
public class SimpleRuleEngine implements RuleEngine {

    private final Map<String, List<MeterRuleState>> meterRuleStates = new HashMap<>();

    private final RuleRepository ruleRepository;


    public SimpleRuleEngine(RuleRepository ruleRepository){
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<Notification> onNewMeasurement(Measurement measurement, RuleContext ruleContext) {

        List<Notification> notifications = new ArrayList<>();

        String meterNumber = measurement.getMeterNumber();

        List<MeterRuleState> statesForMeterRule = meterRuleStates.computeIfAbsent(
            meterNumber,
            id -> createInitialStatesForMeter(id, ruleContext)
        );

        for (MeterRuleState meterRuleState : statesForMeterRule) {
            notifications.addAll(meterRuleState.onNewMeasurement(measurement, ruleContext));
        }

        return notifications;
    }

    private List<MeterRuleState> createInitialStatesForMeter(String meterNumber, RuleContext ruleContext) {
        String consumptionType = ruleContext.getConsumptionType(meterNumber);
        
        List<MeterRuleState> instances = new ArrayList<>();
        
        List<Rule> rulesForType = ruleRepository.findRuleFromType("ruleHeat");
        
        for (Rule rule : rulesForType) {
            instances.add(new MeterRuleState(meterNumber, rule));
        }

        return instances;
    }
}
