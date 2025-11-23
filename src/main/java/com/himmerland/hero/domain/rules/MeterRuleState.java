package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import java.util.Optional;

public class MeterRuleState {
    private Meter meter;
    private IRule rule;
    private int consecutiveBrokenCount;

    public MeterRuleState(Meter meter, IRule rule) {
        this.meter = meter;
        this.rule = rule;
        this.consecutiveBrokenCount = 0;
    }

    public Optional<Notification> onNewMeasurement(Measurement m, RuleContext ctx) {
        if (rule.isBrokenBy(m, ctx)) {
            consecutiveBrokenCount++;

            // Check if we've reached the duration threshold
            if (consecutiveBrokenCount >= rule.getDuration()) {
                // Reset counter and return notification
                consecutiveBrokenCount = 0;
                Notification notification = rule.buildNotification(m, ctx);
                return Optional.of(notification);
            }
            
            // Not enough consecutive breaks yet
            return Optional.empty();
        } else {
            // Measurement doesn't break the rule, reset counter
            consecutiveBrokenCount = 0;
            return Optional.empty();
        }
    }

    public int getConsecutiveBrokenCount() {
        return consecutiveBrokenCount;
    }

    public Meter getMeter() {
        return meter;
    }

    public IRule getRule() {
        return rule;
    }
}
