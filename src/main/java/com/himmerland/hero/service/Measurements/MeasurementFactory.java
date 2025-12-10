package com.himmerland.hero.service.Measurements;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.Measurements.MeasurementCSVImporter.dto.MeasurementDTO;

public class MeasurementFactory {

    public static Measurement fromDTO(MeasurementDTO dto) {

        if (dto.getmeterNumber() == null || dto.getmeterNumber().isBlank()) {
            throw new IllegalArgumentException("MeterNumber cannot be null");
        }

        if (dto.getmeterType() == null || dto.getmeterType().isBlank()) {
            throw new IllegalArgumentException("MeterType cannot be null");
        }

        if (dto.getconsumptionType() == null || dto.getconsumptionType().isBlank()) {
            throw new IllegalArgumentException("ConsumptionType cannot be null");
        }

        if (dto.gettimeStamp() == null || dto.gettimeStamp().isBlank()) {
            throw new IllegalArgumentException("TimeStamp cannot be null");
        }

        System.out.println(dto.getmeterNumber());

        return new Measurement(dto.getAddress(), dto.getmeterNumber(), dto.getmeterType(), dto.getconsumptionType(), 
                        dto.gettimeStamp(), dto.getinfoCode(), dto.getVolume(), dto.getVolumeUnit(),
                        dto.getForwardTemperature(), dto.getForwardTemperatureUnit(),
                        dto.getReturnTemperature(), dto.getReturnTemperatureUnit(), dto.getFlow(),
                        dto.getFlowUnit(), dto.getRegisterE8(), dto.getRegisterE8Unit(),
                        dto.getRegisterE9(), dto.getRegisterE9Unit(), dto.getaverageFlow(),
                        dto.getaverageFlowUnit(), dto.getambientTemperature(), dto.getambientTemperatureUnit(),
                        dto.getHumidity(), dto.getHumidityUnit());
    }
    
}
