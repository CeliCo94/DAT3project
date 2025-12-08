package com.himmerland.hero.service.Measurements.MeasurementCSVImporter.dto;

public class MeasurementDTO {
    private String meterNumber;
    private String meterType;
    private String consumptionType;
    private String timestamp;
    private int infoCode;
    private double Volume;
    private String VolumeUnit;
    private double ForwardTemperature;
    private String ForwardTemperatureUnit;
    private double ReturnTemperature;
    private String ReturnTemperatureUnit; 
    private double Flow;
    private String FlowUnit;
    private int RegisterE8;
    private String RegisterE8Unit;
    private int RegisterE9;
    private String RegisterE9Unit;
    private int averageFlow;
    private String averageFlowUnit;
    private double ambientTemperature;
    private String ambientTemperatureUnit;
    private double Humidity;
    private String HumidityUnit;

    public MeasurementDTO(String meterNumber, String meterType, String consumptionType, String timestamp, int infoCode,
                          double volume, String volumeUnit,
                          double forwardTemperature, String forwardTemperatureUnit,
                          double returnTemperature, String returnTemperatureUnit, double Flow, String FlowUnit,
                            int RegisterE8, String RegisterE8Unit, int RegisterE9, String RegisterE9Unit,
                            int averageFlow, String averageFlowUnit, double ambientTemperature, String ambientTemperatureUnit,
                            double Humidity, String HumidityUnit) {

        this.meterNumber = meterNumber;
        this.meterType = meterType;
        this.consumptionType = consumptionType;
        this.timestamp = timestamp;
        this.infoCode = infoCode;
        this.Volume = volume;
        this.VolumeUnit = volumeUnit;
        this.ForwardTemperature = forwardTemperature;
        this.ForwardTemperatureUnit = forwardTemperatureUnit;
        this.ReturnTemperature = returnTemperature;
        this.ReturnTemperatureUnit = returnTemperatureUnit;
        this.Flow = Flow;
        this.FlowUnit = FlowUnit;
        this.RegisterE8 = RegisterE8;
        this.RegisterE8Unit = RegisterE8Unit;
        this.RegisterE9 = RegisterE9;
        this.RegisterE9Unit = RegisterE9Unit;
        this.averageFlow = averageFlow;
        this.averageFlowUnit = averageFlowUnit;
        this.ambientTemperature = ambientTemperature;
        this.ambientTemperatureUnit = ambientTemperatureUnit;
        this.Humidity = Humidity;
        this.HumidityUnit = HumidityUnit;
    }

    public String getmeterNumber() {
        return meterNumber;
    }

    public String getmeterType() {
        return meterType;
    }

    public String getconsumptionType() {
        return consumptionType;
    }

    public String gettimeStamp() {
        return timestamp;
    }

    public int getinfoCode() {
        return infoCode;
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

    public double getFlow() {
        return Flow;
    }
    public void setFlow(double Flow) {
        this.Flow = Flow;
    }

    public String getFlowUnit() {
        return FlowUnit;
    }
    public void setFlowUnit(String FlowUnit) {
        this.FlowUnit = FlowUnit;
    }

    public int getRegisterE8() {
        return RegisterE8;
    }
    public void setRegisterE8(int RegisterE8) {
        this.RegisterE8 = RegisterE8;
    }

    public String getRegisterE8Unit() {
        return RegisterE8Unit;
    }
    public void setRegisterE8Unit(String RegisterE8Unit) {
        this.RegisterE8Unit = RegisterE8Unit;
    }

    public int getRegisterE9() {
        return RegisterE9;
    }
    public void setRegisterE9(int RegisterE9) {
        this.RegisterE9 = RegisterE9;
    }

    public String getRegisterE9Unit() {
        return RegisterE9Unit;
    }
    public void setRegisterE9Unit(String RegisterE9Unit) {
        this.RegisterE9Unit = RegisterE9Unit;
    }

    public int getaverageFlow() {
        return averageFlow;
    }
    public void setaverageFlow(int averageFlow) {
        this.averageFlow = averageFlow;
    }

    public String getaverageFlowUnit() {
        return averageFlowUnit;
    }
    public void setaverageFlowUnit(String averageFlowUnit) {
        this.averageFlowUnit = averageFlowUnit;
    }

    public double getambientTemperature() {
        return ambientTemperature;
    }
    public void setambientTemperature(double ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    public String getambientTemperatureUnit() {
        return ambientTemperatureUnit;
    }
    public void setambientTemperatureUnit(String ambientTemperatureUnit) {
        this.ambientTemperatureUnit = ambientTemperatureUnit;
    }

    public double getHumidity() {
        return Humidity;
    }
    public void setHumidity(double Humidity) {
        this.Humidity = Humidity;
    }
    public String getHumidityUnit() {
        return HumidityUnit;
    }
    public void setHumidityUnit(String HumidityUnit) {
        this.HumidityUnit = HumidityUnit;
    }
}
