package com.himmerland.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.himmerland.hero.service.rules.*;
import com.himmerland.hero.service.measurements.MeasurementHeat;
import com.himmerland.hero.service.notifications.*;

import com.himmerland.hero.service.helperclasses.enums.Criticality;

import static com.himmerland.hero.service.helperclasses.handlecsv.ReadCSVFileToMeasurementHeat.readCSVFileToMeasurementsHeat;
import static com.himmerland.hero.service.helperclasses.handlejson.WriteObjectToJson.writeObjectToJson;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		String filePath = "src/main/resources/csv/twentyfourHoursTestData.csv";
		List<MeasurementHeat> measurements = readCSVFileToMeasurementsHeat(filePath);
		RuleThresholdHeat rule = new RuleThresholdHeat("Heat Rule Example", 60, 40, 20, 24);
		int counter = 0;

		System.out.println("Measurements Heat:");
		for (MeasurementHeat measurement : measurements) {
			if(
				measurement.getForwardTemperature() >= rule.getThresholdTempIn() &&
				measurement.getReturnTemperature() >= rule.getThresholdTempOut() &&
				measurement.getVolume() >= rule.getThresholdWaterFlow() )	
			{
				System.out.println("Rule triggered for measurement at timestamp: " + measurement.getTimestamp());
				counter++;
			} else {
				System.out.println("No rule triggered for measurement at timestamp: " + measurement.getTimestamp());
			}
		}

		System.out.println("******************************************");
		System.out.println("\nRule Evaluation Summary:");

		String filePathNotification = "src/main/resources/json/notifications.json";

		if (counter >= rule.getDuration()) {
			System.out.println("A notification has been sent due to rule being triggered for " + counter + " measurements.");
			Notification notification = new Notification("address123", "Cause XYZ", rule.getName(), Criticality.High,
					"2024-01-01T12:00:00Z", true, false);
			writeObjectToJson(filePathNotification, notification);
		} else {
			System.out.println("No notification triggered. Rule was only triggered for " + counter + " measurements.");
			writeObjectToJson(filePathNotification, "");
		}
	}
}