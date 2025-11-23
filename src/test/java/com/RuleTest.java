package com;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.himmerland.hero.Application;
import com.himmerland.hero.domain.rules.RuleThresholdHeat;
import com.himmerland.hero.domain.rules.MeterRuleState;
import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import java.util.Optional;

@SpringBootTest(
    classes = Application.class
    )
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration")
public class RuleTest {
    private RuleThresholdHeat rule;
    private MeterRuleState meterRuleState;
    private String meterNumber = "0000";

    @BeforeEach
    public void setUp() {
        rule = new RuleThresholdHeat("Test Rule", 60,30,50,2); // duration = 2 hours
        Meter meter = new Meter("0000", "Heat", "Heat", "2024-01-01T00:00:00Z", 0);
        meterRuleState = new MeterRuleState(meter, rule);
    }

    @Test
    public void testOnNewMeasurement_NoBrokenMeasurement_ReturnsEmpty() {
        // Measurement below all thresholds
        Measurement m = new MeasurementHeat(
            meterNumber, "Heat", "Heat", "2024-01-01T00:00:00Z", 0, 40.0, "m3", 55.0, "C", 35.0, "C"
        );

        Optional<Notification> result = meterRuleState.onNewMeasurement(m, null);

        Assertions.assertFalse(result.isPresent());
        Assertions.assertEquals(0, meterRuleState.getConsecutiveBrokenCount());
    }

    @Test
    public void testOnNewMEasurement_OneBrokenMeasurement_ReturnEmpty() {
        // Measurement above thresholds, but durationis 2, so no notification yet
        Measurement m = new MeasurementHeat(
            meterNumber, "Heat", "Heat", "2024-01-01T00:00:00Z", 0, 65.0, "m3", 70.0, "C", 55.0, "C"
        );

        Optional<Notification> result = meterRuleState.onNewMeasurement(m, null);

        Assertions.assertFalse(result.isPresent());
        Assertions.assertEquals(1, meterRuleState.getConsecutiveBrokenCount());
    }

    @Test
    public void testOnNewMeasurement_ConsecutiveBreaksReachDuration_ReturnsNotification() {
        // First broken measurement
        Measurement m1 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T00:00:00Z", 0, 
            60, "m3", 65, "C", 45, "C"
        );
        meterRuleState.onNewMeasurement(m1, null);
        
        // Second broken measurement - should trigger notification
        Measurement m2 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T01:00:00Z", 0, 
            60, "m3", 65, "C", 45, "C"
        );
        Optional<Notification> result = meterRuleState.onNewMeasurement(m2, null);
        
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(0, meterRuleState.getConsecutiveBrokenCount()); // Reset after notification
    }
    
    @Test
    public void testOnNewMeasurement_BrokenThenNotBroken_ResetsCounter() {
        // First broken measurement
        Measurement m1 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T00:00:00Z", 0, 
            60, "m3", 65, "C", 45, "C"
        );
        meterRuleState.onNewMeasurement(m1, null);
        Assertions.assertEquals(1, meterRuleState.getConsecutiveBrokenCount());
        
        // Measurement that doesn't break the rule - should reset counter
        Measurement m2 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T01:00:00Z", 0, 
            40, "m3", 55, "C", 35, "C"
        );
        Optional<Notification> result = meterRuleState.onNewMeasurement(m2, null);
        
        Assertions.assertFalse(result.isPresent());
        Assertions.assertEquals(0, meterRuleState.getConsecutiveBrokenCount());
    }
    
    @Test
    public void testOnNewMeasurement_MultipleBreaksThenReset_RequiresNewConsecutiveSequence() {
        // Two broken measurements
        Measurement m1 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T00:00:00Z", 0, 
            60, "m3", 65, "C", 45, "C"
        );
        meterRuleState.onNewMeasurement(m1, null);
        
        Measurement m2 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T01:00:00Z", 0, 
            60, "m3", 65, "C", 45, "C"
        );
        meterRuleState.onNewMeasurement(m2, null);
        Assertions.assertEquals(0, meterRuleState.getConsecutiveBrokenCount());
        
        // Reset with non-broken measurement
        Measurement m3 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T02:00:00Z", 0, 
            40, "m3", 55, "C", 35, "C"
        );
        meterRuleState.onNewMeasurement(m3, null);
        Assertions.assertEquals(0, meterRuleState.getConsecutiveBrokenCount());
        
        // Need 2 more consecutive breaks to trigger
        Measurement m4 = new MeasurementHeat(
            meterNumber, "Heat", "heat", "2024-01-01T03:00:00Z", 0, 
            60, "m3", 65, "C", 45, "C"
        );
        meterRuleState.onNewMeasurement(m4, null);
        Assertions.assertEquals(1, meterRuleState.getConsecutiveBrokenCount());
    }
}