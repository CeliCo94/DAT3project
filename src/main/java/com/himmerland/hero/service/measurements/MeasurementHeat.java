package com.himmerland.hero.service.measurements;

public class MeasurementHeat extends Measurement {
    private double Volume;
    private String VolumeUnit;
    private double ForwardTemperature;
    private String ForwardTemperatureUnit;
    private double ReturnTemperature;
    private String ReturnTemperatureUnit; 

    public MeasurementHeat() {
        super("", "", "", "", 0);
    }

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
    public void setVolume(double volume) {
        this.Volume = volume;
    }

    public String getVolumeUnit() {
        return VolumeUnit;
    }
    public void setVolumeUnit(String volumeUnit) {
        this.VolumeUnit = volumeUnit;
    }

    public double getForwardTemperature() {
        return ForwardTemperature;
    }
    public void setForwardTemperature(double forwardTemperature) {
        this.ForwardTemperature = forwardTemperature;
    }

    public String getForwardTemperatureUnit() {
        return ForwardTemperatureUnit;
    }
    public void setForwardTemperatureUnit(String forwardTemperatureUnit) {
        this.ForwardTemperatureUnit = forwardTemperatureUnit;
    }

    public double getReturnTemperature() {
        return ReturnTemperature;
    }
    public void setReturnTemperature(double returnTemperature) {
        this.ReturnTemperature = returnTemperature;
    }

    public String getReturnTemperatureUnit() {
        return ReturnTemperatureUnit;
    }
    public void setReturnTemperatureUnit(String returnTemperatureUnit) {
        this.ReturnTemperatureUnit = returnTemperatureUnit;
    }
        
}