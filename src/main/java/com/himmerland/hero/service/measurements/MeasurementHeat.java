package com.himmerland.hero.service.measurements;

public class MeasurementHeat extends Measurement {
    private double Volume;
    private String VolumeUnit;
    private double ForwardTemperature;
    private String ForwardTemperatureUnit;
    private double ReturnTemperature;
    private String ReturnTemperatureUnit; 


    public MeasurementHeat(String meterNumber, String meterType, String consumptionType, String timestamp, int infoCode,
                          double volume, String volumeUnit,
                          double forwardTemperature, String forwardTemperatureUnit,
                          double returnTemperature, String returnTemperatureUnit) {
        super(meterNumber, meterType, consumptionType, timestamp, infoCode);
        this.Volume = volume;
        this.VolumeUnit = volumeUnit;
        this.ForwardTemperature = forwardTemperature;
        this.ForwardTemperatureUnit = forwardTemperatureUnit;
        this.ReturnTemperature = returnTemperature;
        this.ReturnTemperatureUnit = returnTemperatureUnit;
    }

    public double getVolume() {
        return Volume;
    }
    public String getVolumeUnit() {
        return VolumeUnit;
    }
    public double getForwardTemperature() {
        return ForwardTemperature;
    }
    public String getForwardTemperatureUnit() {
        return ForwardTemperatureUnit;
    }
    public double getReturnTemperature() {
        return ReturnTemperature;
    }
    public String getReturnTemperatureUnit() {
        return ReturnTemperatureUnit;
    }    
}