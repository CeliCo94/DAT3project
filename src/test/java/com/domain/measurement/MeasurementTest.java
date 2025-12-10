package com.domain.measurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.himmerland.hero.domain.measurements.Measurement;

public class MeasurementTest {

    @Test
    void constructorSetsAllFields() {
        Measurement m = new Measurement(
            "Street 132",
            "12345",
            "heat",
            "consumption",
            "2025-01-01T00:00Z",
            7,
            10.5, "m3",
            50.1, "C",
            30.2, "C",
            1.23, "l/s",
            100, "kWh",
            200, "kWh",
            3, "l/h",
            22.5, "C",
            40.0, "%"
        );

        assertEquals("Street 123", m.getAddress());
        assertEquals("12345", m.getMeterNumber());
        assertEquals("heat", m.getMeterType());
        assertEquals("consumption", m.getConsumptionType());
        assertEquals("2025-01-01T00:00Z", m.getTimestamp());
        assertEquals(7, m.getInfoCode());

        assertEquals(10.5, m.getVolume());
        assertEquals("m3", m.getVolumeUnit());

        assertEquals(50.1, m.getForwardTemperature());
        assertEquals("C", m.getForwardTemperatureUnit());

        assertEquals(30.2, m.getReturnTemperature());
        assertEquals("C", m.getReturnTemperatureUnit());

        assertEquals(1.23, m.getFlow());
        assertEquals("l/s", m.getFlowUnit());

        assertEquals(100, m.getRegisterE8());
        assertEquals("kWh", m.getRegisterE8Unit());

        assertEquals(200, m.getRegisterE9());
        assertEquals("kWh", m.getRegisterE9Unit());

        assertEquals(3, m.getaverageFlow());
        assertEquals("l/h", m.getaverageFlowUnit());

        assertEquals(22.5, m.getambientTemperature());
        assertEquals("C", m.getambientTemperatureUnit());

        assertEquals(40.0, m.getHumidity());
        assertEquals("%", m.getHumidityUnit());
    }
    
}
