package com.himmerland.hero.service.measurements.MeasurementCSVImporter;

import com.himmerland.hero.service.measurements.MeasurementService;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MeasurementCSVImporter {
    private final String SEMICOLON_DELIMITER = ";";

    private final MeasurementService MeasurementService;

    public MeasurementCSVImporter(MeasurementService MeasurementService) {
        this.MeasurementService = MeasurementService;
    }

    public void readCSVFileToMeasurementDTOs(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEMICOLON_DELIMITER);

                String meterNumber = safeString(values, 0);
                String meterType = safeString(values, 1);
                String consumptionType = safeString(values, 2);
                String timestamp = safeString(values, 3);
                int infoCode = safeInt(values, 4);
                double volume = safeDouble(values, 5);
                String volumeUnit = safeString(values, 6);
                double forwardTemperature = safeDouble(values, 7);
                String forwardTemperatureUnit = safeString(values, 8);
                double returnTemperature = safeDouble(values, 9);
                String returnTemperatureUnit = safeString(values, 10);
                double Flow = safeDouble(values, 11);
                String FlowUnit = safeString(values, 12);
                int RegisterE8 = safeInt(values, 13);
                String RegisterE8Unit = safeString(values, 14);
                int RegisterE9 = safeInt(values, 15);
                String RegisterE9Unit = safeString(values, 16);
                int averageFlow = safeInt(values, 17);
                String averageFlowUnit = safeString(values, 18);
                double ambientTemperature = safeDouble(values, 19);
                String ambientTemperatureUnit = safeString(values, 20);
                double Humidity = safeDouble(values, 21);
                String HumidityUnit = safeString(values, 22);

                MeasurementDTO measurement = new MeasurementDTO(
                        meterNumber, meterType, consumptionType, timestamp, infoCode,
                        volume, volumeUnit, forwardTemperature, forwardTemperatureUnit,
                        returnTemperature, returnTemperatureUnit, Flow, FlowUnit,
                        RegisterE8, RegisterE8Unit, RegisterE9, RegisterE9Unit,
                        averageFlow, averageFlowUnit, ambientTemperature, ambientTemperatureUnit,
                        Humidity, HumidityUnit
                );

                boolean success = MeasurementService.CreateAndSaveMeasurement(measurement);
                
                if (success) {
                    System.out.println("Measurement Saves Successfully");
                } else {
                    System.out.println("Failed to save measurement");
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double parseDouble(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }

        return Double.parseDouble(value.replace(",", "."));
    }

    private static int parseInt(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        
        return Integer.parseInt(value);
    }

    private String safeString(String[] values, int index) {
        if (index < values.length) {
            return values[index];
        } else {
            return ""; // default for strings
        }
    }

    private int safeInt(String[] values, int index) {
        if (index < values.length && !values[index].isEmpty()) {
            return parseInt(values[index]);
        } else {
            return 0; // default for missing numbers
        }
    }

    private double safeDouble(String[] values, int index) {
        if (index < values.length && !values[index].isEmpty()) {
            // Handle comma decimal separator
            return parseDouble(values[index]);
        } else {
            return 0.0;
        }
    }

}
