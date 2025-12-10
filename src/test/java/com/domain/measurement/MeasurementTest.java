package com.domain.measurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.himmerland.hero.domain.measurements.Measurement;

public class MeasurementTest {

    @Test
    void constructorSetsAllFields() {
        Measurement m = new Measurement(
            "12345",
            "heat",
            "consumption",
            "2025-01-01T00:00Z",
            7
        );

        assertEquals("12345", m.getMeterNumber());
        assertEquals("heat", m.getMeterType());
        assertEquals("consumption", m.getConsumptionType());
        assertEquals("2025-01-01T00:00Z", m.getTimestamp());
        assertEquals(7, m.getInfoCode());
    }
    
}
