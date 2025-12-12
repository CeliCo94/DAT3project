package com.himmerland.hero.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.himmerland.hero.service.helperclasses.enums.Criticality;
import com.himmerland.hero.web.RuleRequest;

class RuleFactoryTest {

    private RuleFactory factory;

    @BeforeEach
    void setUp() {
        factory = new RuleFactory();
    }

    @Test
    void testCreateRuleHeat_CreatesCorrectSubclass() {
        // Arrange
        RuleRequest request = new RuleRequest();
        request.setType("ruleHeat");
        request.setName("Heat Rule Test");
        request.setDescription("Test description for heat rule");
        request.setCriticality(Criticality.High);
        request.setDuration(300);
        request.setThresholdTempIn(70);
        request.setThresholdTempOut(50);
        request.setThresholdHeatWaterFlow(100);

        // Act
        Rule result = factory.create(request);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RuleHeat.class, result);
        
        RuleHeat heatRule = (RuleHeat) result;
        
        // Verify shared fields
        assertEquals("Heat Rule Test", heatRule.getName());
        assertEquals("Test description for heat rule", heatRule.getDescription());
        assertEquals(Criticality.High, heatRule.getCriticality());
        assertEquals(300, heatRule.getDuration());
        
        // Verify type-specific fields
        assertEquals(70, heatRule.getThresholdTempIn());
        assertEquals(50, heatRule.getThresholdTempOut());
        assertEquals(100, heatRule.getThresholdHeatWaterFlow());
    }

    @Test
    void testCreateRuleWater_CreatesCorrectSubclass() {
        // Arrange
        RuleRequest request = new RuleRequest();
        request.setType("ruleWater");
        request.setName("Water Rule Test");
        request.setDescription("Test description for water rule");
        request.setCriticality(Criticality.Medium);
        request.setDuration(600);
        request.setThresholdWaterFlow(150);

        // Act
        Rule result = factory.create(request);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RuleWater.class, result);
        
        RuleWater waterRule = (RuleWater) result;
        
        // Verify shared fields
        assertEquals("Water Rule Test", waterRule.getName());
        assertEquals("Test description for water rule", waterRule.getDescription());
        assertEquals(Criticality.Medium, waterRule.getCriticality());
        assertEquals(600, waterRule.getDuration());
        
        // Verify type-specific fields
        assertEquals(150, waterRule.getThresholdWaterFlow());
    }

    @Test
    void testCreateRuleHumidity_CreatesCorrectSubclass() {
        // Arrange
        RuleRequest request = new RuleRequest();
        request.setType("ruleHumidity");
        request.setName("Humidity Rule Test");
        request.setDescription("Test description for humidity rule");
        request.setCriticality(Criticality.Low);
        request.setDuration(900);
        request.setThresholdHumidity(80);

        // Act
        Rule result = factory.create(request);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RuleHumidity.class, result);
        
        RuleHumidity humidityRule = (RuleHumidity) result;
        
        // Verify shared fields
        assertEquals("Humidity Rule Test", humidityRule.getName());
        assertEquals("Test description for humidity rule", humidityRule.getDescription());
        assertEquals(Criticality.Low, humidityRule.getCriticality());
        assertEquals(900, humidityRule.getDuration());
        
        // Verify type-specific fields
        assertEquals(80, humidityRule.getThresholdHumidity());
    }

    @Test
    void testCreateWithUnknownType_ThrowsIllegalArgumentException() {
        // Arrange
        RuleRequest request = new RuleRequest();
        request.setType("unknownType");
        request.setName("Invalid Rule");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> factory.create(request)
        );
        
        assertEquals("Unknown rule type: unknownType", exception.getMessage());
    }

    @Test
    void testCreateWithNullType_ThrowsException() {
        // Arrange
        RuleRequest request = new RuleRequest();
        request.setType(null);
        request.setName("Null Type Rule");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> factory.create(request));
    }

    @Test
    void testCreateRuleHeat_WithNullValues() {
        // Arrange
        RuleRequest request = new RuleRequest();
        request.setType("ruleHeat");
        request.setDuration(0); // duration is primitive int, cannot be null
        // Leave other fields as null

        // Act
        Rule result = factory.create(request);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RuleHeat.class, result);
        
        RuleHeat heatRule = (RuleHeat) result;
        assertNull(heatRule.getName());
        assertNull(heatRule.getDescription());
        assertNull(heatRule.getCriticality());
        assertEquals(0, heatRule.getDuration());
        assertNull(heatRule.getThresholdTempIn());
        assertNull(heatRule.getThresholdTempOut());
        assertNull(heatRule.getThresholdHeatWaterFlow());
    }
}
