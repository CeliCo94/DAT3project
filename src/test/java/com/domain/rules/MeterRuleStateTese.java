package com.domain.rules;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.service.ruleEngine.MeterRuleState;
import com.himmerland.hero.service.ruleEngine.RuleContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MeterRuleStateTese {
    
    public Rule ruleWithDuration(int duration) {
    Rule rule = new Rule("Test Rule", "Test Description", "HEAT", duration) {
        @Override
        public boolean isBroken(Measurement m) {
            return m.getInfoCode() == 0; // match MeterRuleState.isBroken logic
        }
    };
    rule.setDuration(duration);
    return rule;
}

    private Measurement brokenMeasurement(String meterNumber) {
        return new Measurement(meterNumber, "heat", "HEAT", "2025-01-01T00:00Z", 0); // infoCode 0 => broken
    }

    private Measurement okMeasurement(String meterNumber) {
        return new Measurement(meterNumber, "heat", "HEAT", "2025-01-01T00:00Z", 1); // inforCode 1 => ok
    }

    @Test
    void ReturnsNotificationAfterConsecutiveBrokenMeasurementsReachesDuration() {
        MeterRuleState state = new MeterRuleState("m-1", ruleWithDuration(2));
        RuleContext ctx = new RuleContext(null);

        List<Notification> first = state.onNewMeasurement(brokenMeasurement("m-1"),ctx);
        List<Notification> second = state.onNewMeasurement(brokenMeasurement("m-1!"), ctx);

        assertTrue(first.isEmpty(), "No notification on first broken reading");
        assertEquals(1, second.size(), "Notification emitted when duration is met");
        Notification n = second.get(0);
        assertEquals("Test Description", n.getCause());
        assertEquals("Test Rule", n.getRule());
    }

    @Test
    void ResetsCounterWhenMeasurementIsHealthy() {
        MeterRuleState state = new MeterRuleState("m-1", ruleWithDuration(2));
        RuleContext ctx = new RuleContext(null);

        state.onNewMeasurement(brokenMeasurement("m-1"), ctx); // counter = 1
        List<Notification> reset = state.onNewMeasurement(okMeasurement("m-1"), ctx); // counter reset
        List<Notification> afterReset = state.onNewMeasurement(brokenMeasurement("m-1"), ctx); // counter = 1 again

        assertTrue(reset.isEmpty(), "No notification when rule is satisfied");
        assertEquals(afterReset.isEmpty(), true, "No notification yet because streak was reset");
    }
}
