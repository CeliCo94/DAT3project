package com.himmerland.hero.domain.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RuleTest {
    
    @Test
    public void testRuleHeatDefaultConstructor() {
        RuleHeat rule = new RuleHeat();
        Assertions.assertEquals(0, rule.getDuration());
        Assertions.assertEquals("VARME", rule.getConsumptionType());
        Assertions.assertEquals("", rule.getName());
        Assertions.assertEquals("", rule.getDescription());
        Assertions.assertFalse(rule.isActive());
     }

    @Test
    public void testRuleWaterDefaultConstructor() {
        RuleWater rule = new RuleWater();
        Assertions.assertEquals(0, rule.getDuration());
        Assertions.assertEquals("VAND", rule.getConsumptionType());
        Assertions.assertEquals("", rule.getName());
        Assertions.assertEquals("", rule.getDescription());
        Assertions.assertFalse(rule.isActive());
    }

    @Test
    public void testRuleHumidityDefaultConstructor() {
        RuleHumidity rule = new RuleHumidity();
        Assertions.assertEquals(0, rule.getDuration());
        Assertions.assertEquals("LUFTFUGTIGHED", rule.getConsumptionType());
        Assertions.assertEquals("", rule.getName());
        Assertions.assertEquals("", rule.getDescription());
        Assertions.assertFalse(rule.isActive());
    }

    @Test
    public void testRuleSettersAndGetters() {
        RuleHeat rule = new RuleHeat();
        
        rule.setName("Test Rule");
        Assertions.assertEquals("Test Rule", rule.getName());
        
        rule.setDescription("Test Description");
        Assertions.assertEquals("Test Description", rule.getDescription());
        
        rule.setConsumptionType("VARME");
        Assertions.assertEquals("VARME", rule.getConsumptionType());
        
        rule.setDuration(60);
        Assertions.assertEquals(60, rule.getDuration());
        
        rule.setActive(true);
        Assertions.assertTrue(rule.isActive());
        
        rule.setActive(false);
        Assertions.assertFalse(rule.isActive());
    }

    @Test
    public void testRuleHeatSpecificFields() {
        RuleHeat rule = new RuleHeat();
        
        rule.setThresholdTempIn(20);
        Assertions.assertEquals(20, rule.getThresholdTempIn().intValue());
        
        rule.setThresholdTempOut(15);
        Assertions.assertEquals(15, rule.getThresholdTempOut().intValue());
        
        rule.setThresholdHeatWaterFlow(100);
        Assertions.assertEquals(100, rule.getThresholdHeatWaterFlow().intValue());
    }

    @Test
    public void testRuleWaterSpecificFields() {
        RuleWater rule = new RuleWater();
        
        rule.setThresholdWaterFlow(50);
        Assertions.assertEquals(50, rule.getThresholdWaterFlow().intValue());
    }

    @Test
    public void testRuleHumiditySpecificFields() {
        RuleHumidity rule = new RuleHumidity();
        
        rule.setThresholdHumidity(70);
        Assertions.assertEquals(70, rule.getThresholdHumidity().intValue());
    }

    @Test
    public void testRulePolymorphism() {
        Rule heatRule = new RuleHeat();
        Rule waterRule = new RuleWater();
        Rule humidityRule = new RuleHumidity();
        
        heatRule.setName("Heat Rule");
        waterRule.setName("Water Rule");
        humidityRule.setName("Humidity Rule");
        
        Assertions.assertEquals("Heat Rule", heatRule.getName());
        Assertions.assertEquals("Water Rule", waterRule.getName());
        Assertions.assertEquals("Humidity Rule", humidityRule.getName());
        
        Assertions.assertTrue(heatRule instanceof RuleHeat);
        Assertions.assertTrue(waterRule instanceof RuleWater);
        Assertions.assertTrue(humidityRule instanceof RuleHumidity);
    }

    @Test
    public void testRuleJsonSerialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        
        RuleHeat heatRule = new RuleHeat();
        heatRule.setName("Heat Test");
        heatRule.setDescription("Test heat rule");
        heatRule.setDuration(30);
        heatRule.setActive(true);
        heatRule.setThresholdTempIn(22);
        
        String json = mapper.writeValueAsString(heatRule);
        Assertions.assertNotNull(json);
        Assertions.assertTrue(json.contains("\"type\":\"ruleHeat\""));
        Assertions.assertTrue(json.contains("\"name\":\"Heat Test\""));
        
        Rule deserializedRule = mapper.readValue(json, Rule.class);
        Assertions.assertTrue(deserializedRule instanceof RuleHeat);
        Assertions.assertEquals("Heat Test", deserializedRule.getName());
        Assertions.assertEquals(30, deserializedRule.getDuration());
    }

    @Test
    public void testRuleEdgeCases() {
        RuleHeat rule = new RuleHeat();
        
        // Test with null values
        rule.setName(null);
        Assertions.assertNull(rule.getName());
        
        rule.setDescription(null);
        Assertions.assertNull(rule.getDescription());
        
        rule.setConsumptionType(null);
        Assertions.assertNull(rule.getConsumptionType());
        
        // Test with negative duration
        rule.setDuration(-10);
        Assertions.assertEquals(-10, rule.getDuration());
        
        // Test with large duration
        rule.setDuration(Integer.MAX_VALUE);
        Assertions.assertEquals(Integer.MAX_VALUE, rule.getDuration());
    }

    @Test
    public void testRuleAllSubtypesWithCompleteData() {
        RuleHeat heatRule = new RuleHeat();
        heatRule.setName("Complete Heat Rule");
        heatRule.setDescription("Full heat rule test");
        heatRule.setDuration(120);
        heatRule.setActive(true);
        heatRule.setThresholdTempIn(25);
        heatRule.setThresholdTempOut(18);
        heatRule.setThresholdHeatWaterFlow(200);
        
        Assertions.assertEquals("Complete Heat Rule", heatRule.getName());
        Assertions.assertEquals("Full heat rule test", heatRule.getDescription());
        Assertions.assertEquals(120, heatRule.getDuration());
        Assertions.assertTrue(heatRule.isActive());
        Assertions.assertEquals("VARME", heatRule.getConsumptionType());
        
        RuleWater waterRule = new RuleWater();
        waterRule.setName("Complete Water Rule");
        waterRule.setDescription("Full water rule test");
        waterRule.setDuration(90);
        waterRule.setActive(true);
        waterRule.setThresholdWaterFlow(150);
        
        Assertions.assertEquals("Complete Water Rule", waterRule.getName());
        Assertions.assertEquals("Full water rule test", waterRule.getDescription());
        Assertions.assertEquals(90, waterRule.getDuration());
        Assertions.assertTrue(waterRule.isActive());
        Assertions.assertEquals("VAND", waterRule.getConsumptionType());
        
        RuleHumidity humidityRule = new RuleHumidity();
        humidityRule.setName("Complete Humidity Rule");
        humidityRule.setDescription("Full humidity rule test");
        humidityRule.setDuration(45);
        humidityRule.setActive(false);
        humidityRule.setThresholdHumidity(65);
        
        Assertions.assertEquals("Complete Humidity Rule", humidityRule.getName());
        Assertions.assertEquals("Full humidity rule test", humidityRule.getDescription());
        Assertions.assertEquals(45, humidityRule.getDuration());
        Assertions.assertFalse(humidityRule.isActive());
        Assertions.assertEquals("LUFTFUGTIGHED", humidityRule.getConsumptionType());
    }
}   