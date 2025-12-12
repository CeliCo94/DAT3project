package com.himmerland.hero.service.ruleEngine;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.Rule;
import com.himmerland.hero.service.helperclasses.enums.Criticality;
import com.himmerland.hero.service.repositories.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class SimpleRuleEngineTest {

    private RuleRepository ruleRepository;
    private RuleContext ruleContext;
    private SimpleRuleEngine engine;

    private Measurement measurement(String meterNumber, String address) {
        Measurement m = mock(Measurement.class);
        when(m.getMeterNumber()).thenReturn(meterNumber);
        when(m.getAddress()).thenReturn("addr-" + address);
        return m;
    }

    private Rule rule(String name) {
        Rule r = mock(Rule.class);
        when(r.getDuration()).thenReturn(1);
        when(r.getName()).thenReturn(name);
        when(r.getDescription()).thenReturn("desc-" + name);
        when(r.getCriticality()).thenReturn(Criticality.High);
        when(r.isBroken(any())).thenReturn(true);
        return r;
    }
    
    @BeforeEach
    void setUp() {
        ruleRepository = mock(RuleRepository.class);
        ruleContext = mock(RuleContext.class);
        engine = new SimpleRuleEngine(ruleRepository);

        when(ruleContext.getConsumptionType(anyString())).thenReturn("ruleHeat");
        when(ruleContext.getDepartment(anyString())).thenReturn("Dept-1");
    }

    @Test
    void seedsStatesinceAndDelegatesPerMeter() {
        Rule r1 = rule("r1");
        Rule r2 = rule("r2");
        when(ruleRepository.findRuleFromType("ruleHeat")).thenReturn(List.of(r1, r2));

        Measurement m1 = measurement("m-123", "addr-1");

        List<Notification> notifications = engine.onNewMeasurement(m1, ruleContext);

        assertThat(notifications).hasSize(2);

        verify(ruleRepository, times(1)).findRuleFromType("ruleHeat");

        verify(ruleContext, times(1)).getConsumptionType("m-123");

        verify(r1, times(1)).isBroken(m1);
        verify(r2, times(1)).isBroken(m1);
    }

    @Test
    void reusesStateForSameMeter() {
        Rule r1 = rule("r1");
        Rule r2 = rule("r2");
        when(ruleRepository.findRuleFromType("ruleHeat")).thenReturn(List.of(r1, r2));

        Measurement m1 = measurement("m-123", "addr-1");
        Measurement m2 = measurement("m-123", "addr-2");

        engine.onNewMeasurement(m1, ruleContext);
        engine.onNewMeasurement(m2, ruleContext);

        verify(ruleRepository, times(1)).findRuleFromType("ruleHeat");

        verify(r1, times(2)).isBroken(any());

        verify(ruleContext, times(1)).getConsumptionType("m-123");
    }

    @Test
    void fetchesFreshStatesForNewMeter() {
        Rule r1 = rule("r1");
        Rule r2 = rule("r2");
        when(ruleRepository.findRuleFromType("ruleHeat")).thenReturn(List.of(r1, r2));

        Measurement m1 = measurement("m-123", "addr-1");
        Measurement m2 = measurement("m-456", "addr-2");

        engine.onNewMeasurement(m1, ruleContext);
        engine.onNewMeasurement(m2, ruleContext);

        InOrder order = inOrder(ruleRepository);

        order.verify(ruleRepository, times(2)).findRuleFromType("ruleHeat");
        verify(r1, times(1)).isBroken(m1);
        verify(r2, times(1)).isBroken(m2);
        verify(ruleContext, times(1)).getConsumptionType("m-123");
        verify(ruleContext, times(1)).getConsumptionType("m-456");
    }
}
