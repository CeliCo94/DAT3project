package com.himmerland.hero.domain.rules;

import com.himmerland.hero.web.RuleRequest;
import org.springframework.stereotype.Component;

@Component
public class RuleFactory {

    public Rule create(RuleRequest dto) {
        String type = dto.getType();

        switch (type) {
            case "ruleHeat": {
                RuleHeat rule = new RuleHeat();
                rule.setName(dto.getName());
                rule.setDescription(dto.getDescription());
                rule.setDuration(dto.getDuration());
                rule.setThresholdTempIn(dto.getThresholdTempIn());
                rule.setThresholdTempOut(dto.getThresholdTempOut());
                rule.setThresholdHeatWaterFlow(dto.getThresholdHeatWaterFlow());
                return rule;
            }

            case "ruleWater": {
                RuleWater rule = new RuleWater();
                rule.setName(dto.getName());
                rule.setDescription(dto.getDescription());
                rule.setDuration(dto.getDuration());
                rule.setThresholdWaterFlow(dto.getThresholdWaterFlow());
                return rule;
            }

            case "ruleHumidity": {
                RuleHumidity rule = new RuleHumidity();
                rule.setName(dto.getName());
                rule.setDescription(dto.getDescription());
                rule.setDuration(dto.getDuration());
                rule.setThresholdHumidity(dto.getThresholdHumidity());
                return rule;
            }

            default:
                throw new IllegalArgumentException("Unknown rule type: " + type);
        }
    }
}
