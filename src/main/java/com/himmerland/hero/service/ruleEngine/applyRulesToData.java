package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.rules.*;
import com.himmerland.hero.domain.measurements.*;

import java.util.List;

public class applyRulesToData {

    public static void applyRule(List<Rule> activeRules) {


        for(Rule rule : activeRules) {
            List<Measurement> measurements = getMeasurements(rule.getConsumptionType());

            for (int i = 0; i<rule.getDuration(); i++) {
                
            }
            
            rule.compareMeasurementsToThresholds(measurements);

        }
    }
}