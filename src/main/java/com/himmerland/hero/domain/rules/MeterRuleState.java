package com.himmerland.hero.domain.rules;

import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import java.util.Optional;

public class MeterRuleState {
    private Meter meter;
    private Rule rule;
    private int consecutiveBrokenCount;

    public MeterRuleState(Meter meter, Rule rule) {
        this.meter = meter;
        this.rule = rule;
        this.consecutiveBrokenCount = 0;
    }
    public <T extends Rule> Optional<Notification> onNewMeasurement(Measurement m, RuleContext ctx) {
        if ((rule).isBroken(m)) {
            consecutiveBrokenCount++;

            // Check if we've reached the duration threshold
            if (consecutiveBrokenCount >= (rule).getDuration()) {
                // Reset counter and return notification
                consecutiveBrokenCount = 0;
                Notification notification = rule.buildNotification(m);
                return Optional.of(notification);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public int getConsecutiveBrokenCount() {
        return consecutiveBrokenCount;
    }

    public Meter getMeter() {
        return meter;
    }

    public Rule getRule() {
        return rule;
    }
}
