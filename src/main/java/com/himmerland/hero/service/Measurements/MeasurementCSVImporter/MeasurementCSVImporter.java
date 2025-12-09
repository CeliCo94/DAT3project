package com.himmerland.hero.service.Measurements.MeasurementCSVImporter;

import com.himmerland.hero.service.Measurements.MeasurementCSVImporter.dto.MeasurementDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeasurementCSVImporter {
    private final String SEMICOLON_DELIMITER = ";";

    public MeasurementCSVImporter() {}

    public List<MeasurementDTO> readCSVFileToMeasurementDTOs(String filePath) {
        List<MeasurementDTO> dtos = new ArrayList<MeasurementDTO>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEMICOLON_DELIMITER);

                String address = safeString(values,0);
                String meterNumber = safeString(values, 1);
                String meterType = safeString(values, 2);
                String consumptionType = safeString(values, 3);
                String timestamp = safeString(values, 4);
                int infoCode = safeInt(values, 5);
                double volume = safeDouble(values, 6);
                String volumeUnit = safeString(values, 7);
                double forwardTemperature = safeDouble(values, 8);
                String forwardTemperatureUnit = safeString(values, 9);
                double returnTemperature = safeDouble(values, 10);
                String returnTemperatureUnit = safeString(values, 11);
                double Flow = safeDouble(values, 12);
                String FlowUnit = safeString(values, 13);
                int RegisterE8 = safeInt(values, 14);
                String RegisterE8Unit = safeString(values, 15);
                int RegisterE9 = safeInt(values, 16);
                String RegisterE9Unit = safeString(values, 17);
                int averageFlow = safeInt(values, 18);
                String averageFlowUnit = safeString(values, 19);
                double ambientTemperature = safeDouble(values, 20);
                String ambientTemperatureUnit = safeString(values, 21);
                double Humidity = safeDouble(values, 22);
                String HumidityUnit = safeString(values, 23);

                MeasurementDTO measurement = new MeasurementDTO(
                        address, meterNumber, meterType, consumptionType, timestamp, infoCode,
                        volume, volumeUnit, forwardTemperature, forwardTemperatureUnit,
                        returnTemperature, returnTemperatureUnit, Flow, FlowUnit,
                        RegisterE8, RegisterE8Unit, RegisterE9, RegisterE9Unit,
                        averageFlow, averageFlowUnit, ambientTemperature, ambientTemperatureUnit,
                        Humidity, HumidityUnit
                );

                System.out.println(measurement.getmeterNumber());

                //boolean success = MeasurementService.CreateAndSaveMeasurement(measurement);
                dtos.add(measurement);
                /*
                if (success) {
                    System.out.println("Measurement Saves Successfully");
                } else {
                    System.out.println("Failed to save measurement");
                    continue;
                }
                    */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dtos;
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
