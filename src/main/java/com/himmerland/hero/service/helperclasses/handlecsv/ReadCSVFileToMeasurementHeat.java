package com.himmerland.hero.service.helperclasses.handlecsv;

import com.himmerland.hero.domain.measurements.MeasurementHeat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ReadCSVFileToMeasurementHeat {
    private static final String SEMICOLON_DELIMITER = ";";

    public static List<MeasurementHeat> readCSVFileToMeasurementsHeat(String filePath) {
        List<MeasurementHeat> measurements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEMICOLON_DELIMITER);

                String meterNumber = values[0];
                String meterType = values[1];
                String consumptionType = values[2];
                String timestamp = values[3];
                int infoCode = Integer.parseInt(values[4]);
                double volume = parseDouble(values[5]);
                String volumeUnit = values[6];
                double forwardTemperature = parseDouble(values[7]);
                String forwardTemperatureUnit = values[8];
                double returnTemperature = parseDouble(values[9]);
                String returnTemperatureUnit = values[10];
                double Flow = parseDouble(values[11]);
                String FlowUnit = values[12];
                int RegisterE8 = Integer.parseInt(values[13]);
                String RegisterE8Unit = values[14];
                int RegisterE9 = Integer.parseInt(values[15]);
                String RegisterE9Unit = values[16];
                int averageFlow = Integer.parseInt(values[17]);
                String averageFlowUnit = values[18];
                double ambientTemperature = parseDouble(values[19]);
                String ambientTemperatureUnit = values[20];
                double Humidity = parseDouble(values[21]);
                String HumidityUnit = values[22];

                MeasurementHeat measurement = new MeasurementHeat(
                        meterNumber, meterType, consumptionType, timestamp, infoCode,
                        volume, volumeUnit, forwardTemperature, forwardTemperatureUnit,
                        returnTemperature, returnTemperatureUnit, Flow, FlowUnit,
                        RegisterE8, RegisterE8Unit, RegisterE9, RegisterE9Unit,
                        averageFlow, averageFlowUnit, ambientTemperature, ambientTemperatureUnit,
                        Humidity, HumidityUnit
                );

                measurements.add(measurement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return measurements;
    }

    private static double parseDouble(String value) {
        return Double.parseDouble(value.replace(",", "."));
    }
}
