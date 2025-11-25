package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.domain.meters.Meter;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

import java.util.Optional;

public class MeterRuleState {
/*     
    private final Meter meter;
    private final Rule rule;
    private int consecutiveBrokenCount;

    public MeterRuleState(String meterId, String ruleId) {
        this.meter = getMeterFromId(meterId);
        this.rule = getRuleFromId(ruleId);
        this.consecutiveBrokenCount = 0;
    }

    // called every time a new measurement for this meter arrives
    public Optional<Notification> onNewMeasurement(Measurement m, RuleContext ctx) {

        boolean brokenNow = rule.isBrokenBy(m, ctx); // single-measurement check

        if (brokenNow) {
            consecutiveBrokenCount++;
        } else {
            consecutiveBrokenCount = 0;
        }

        if (consecutiveBrokenCount >= rule.getDurationInHours()) {
            consecutiveBrokenCount = 0;       // or keep going, depending on semantics
            return Optional.of(rule.buildNotification(m, ctx));
        }

        return Optional.empty();
    }
     */
}
