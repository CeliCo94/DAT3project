package com.himmerland.hero.service.helperclasses.handlecsv;

import com.himmerland.hero.domain.measurements.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ReadCSVFileToMeasurementHeat {
    private static final String SEMICOLON_DELIMITER = ";";

    public static List<Measurement> readCSVFileToMeasurementsHeat(String filePath) {
        List<Measurement> measurements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEMICOLON_DELIMITER);

                String address = values[0];
                String meterNumber = values[1];
                String meterType = values[2];
                String consumptionType = values[3];
                String timestamp = values[4];
                int infoCode = Integer.parseInt(values[5]);
                double volume = parseDouble(values[6]);
                String volumeUnit = values[7];
                double forwardTemperature = parseDouble(values[8]);
                String forwardTemperatureUnit = values[9];
                double returnTemperature = parseDouble(values[10]);
                String returnTemperatureUnit = values[11];
                double Flow = parseDouble(values[12]);
                String FlowUnit = values[13];
                int RegisterE8 = Integer.parseInt(values[14]);
                String RegisterE8Unit = values[15];
                int RegisterE9 = Integer.parseInt(values[16]);
                String RegisterE9Unit = values[17];
                int averageFlow = Integer.parseInt(values[18]);
                String averageFlowUnit = values[19];
                double ambientTemperature = parseDouble(values[20]);
                String ambientTemperatureUnit = values[21];
                double Humidity = parseDouble(values[22]);
                String HumidityUnit = values[23];

                Measurement measurement = new Measurement(
                        address, meterNumber, meterType, consumptionType, timestamp, infoCode,
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
