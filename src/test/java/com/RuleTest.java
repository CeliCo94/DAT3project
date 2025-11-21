package com;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.himmerland.hero.Application;
import com.himmerland.hero.domain.rules.RuleThresholdHeat;
import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.domain.measurements.Measurement;
import java.util.List;
import java.util.ArrayList;

@SpringBootTest(
    classes = Application.class
    )
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration")
public class RuleTest {
    @Test
    public void testRule_NoMeasurements_ReturnsZero() {
        RuleThresholdHeat rule = new RuleThresholdHeat("Test Rule", 60, 30, 50,10);
        List<Measurement> measurements = new ArrayList<>();

        int result = rule.testRule(measurements);
        Assertions.assertEquals(0, result);

    }

    @Test
    public void testRule_MeasurementsBeLowThresholds_ReturnsZero() {
        RuleThresholdHeat rule = new RuleThresholdHeat("Test Rule", 60, 30, 50, 10);

        List<Measurement> measurements = new ArrayList<>();
        measurements.add(new MeasurementHeat("0000", "Heat", "heat", "2024-01-01T00:00:00Z", 0, 40, "m3", 55, "C", 35, "C"));

        int result = rule.testRule(measurements);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testRule_MeasurementsAboveThresholds_ReturnsCount() {
        RuleThresholdHeat rule = new RuleThresholdHeat("Test Rule", 60, 30, 50, 10);

        List<Measurement> measurements = new ArrayList<>();
        measurements.add(new MeasurementHeat("0000", "Heat", "heat", "2024-01-01T00:00:00Z", 0, 40, "m3", 55, "C", 35, "C"));
        measurements.add(new MeasurementHeat("0001", "Heat", "heat", "2024-01-01T01:00:00Z", 0, 60, "m3", 65, "C", 45, "C"));

        int result = rule.testRule(measurements);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testRule_PartialThresholdMatches_ReturnsOnlyMatchingCount() {
        RuleThresholdHeat rule = new RuleThresholdHeat("Test Rule", 60, 30, 50, 10);

        List<Measurement> measurements = new ArrayList<>();
        measurements.add(new MeasurementHeat("0000", "Heat", "heat", "2024-01-01T00:00:00Z", 0, 40, "m3", 55, "C", 35, "C"));
        measurements.add(new MeasurementHeat("0001", "Heat", "heat", "2024-01-01T01:00:00Z", 0, 60, "m3", 65, "C", 45, "C"));

        int result = rule.testRule(measurements);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testRule_ExaxtThresholdValues_ShouldMatch() {
        RuleThresholdHeat rule = new RuleThresholdHeat("Test Rule", 60, 30, 50, 10);

        List<Measurement> measurements = new ArrayList<>();
        measurements.add(new MeasurementHeat("0000", "Heat", "heat", "2024-01-01T00:00:00Z", 0, 40, "m3", 60, "C", 30, "C"));
        measurements.add(new MeasurementHeat("0001", "Heat", "heat", "2024-01-01T01:00:00Z", 0, 60, "m3", 60, "C", 30, "C"));

        int result = rule.testRule(measurements);
        Assertions.assertEquals(2, result);
    }

    @Test
    public void testRule_countExceedsDuration_ShouldTrigger() {
        RuleThresholdHeat rule = new RuleThresholdHeat("Test Rule", 60, 30, 50, 10);

        List<Measurement> measurements = new ArrayList<>();
        measurements.add(new MeasurementHeat("0000", "Heat", "heat", "2024-01-01T00:00:00Z", 0, 40, "m3", 60, "C", 30, "C"));
        measurements.add(new MeasurementHeat("0001", "Heat", "heat", "2024-01-01T01:00:00Z", 0, 60, "m3", 60, "C", 30, "C"));

        int result = rule.testRule(measurements);
        Assertions.assertEquals(2, result);
        Assertions.assertTrue(result >= rule.getDuration());
    }
}