package com.himmerland.hero.service.Measurements;

import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.service.Measurements.MeasurementCSVImporter.dto.MeasurementDTO;

public class MeasurementFactory {

    public static MeasurementHeat fromDTO(MeasurementDTO dto) {

        if (dto.getmeterNumber() == null) {
            throw new IllegalArgumentException("MeterNumber cannot be null");
        }

        if (dto.getmeterType() == null) {
            throw new IllegalArgumentException("MeterType cannot be null");
        }

        if (dto.getconsumptionType() == null) {
            throw new IllegalArgumentException("ConsumptionType cannot be null");
        }

        if (dto.gettimeStamp() == null) {
            throw new IllegalArgumentException("TimeStamp cannot be null");
        }

        return new MeasurementHeat(dto.getmeterNumber(), dto.getmeterType(), dto.getconsumptionType(), 
                        dto.gettimeStamp(), dto.getinfoCode(), dto.getVolume(), dto.getVolumeUnit(),
                        dto.getForwardTemperature(), dto.getForwardTemperatureUnit(),
                        dto.getReturnTemperature(), dto.getReturnTemperatureUnit(), dto.getFlow(),
                        dto.getFlowUnit(), dto.getRegisterE8(), dto.getRegisterE8Unit(),
                        dto.getRegisterE9(), dto.getRegisterE9Unit(), dto.getaverageFlow(),
                        dto.getaverageFlowUnit(), dto.getambientTemperature(), dto.getambientTemperatureUnit(),
                        dto.getHumidity(), dto.getHumidityUnit());
    }
    
}
