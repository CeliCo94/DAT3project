package com.himmerland.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.himmerland.hero.service.rules.RuleThresholdHeat;
import com.himmerland.hero.service.measurements.MeasurementHeat;
import com.himmerland.hero.service.notifications.Notification;
import com.himmerland.hero.service.helperclasses.enums.Criticality;

import static com.himmerland.hero.service.helperclasses.handlecsv.ReadCSVFileToMeasurementHeat.readCSVFileToMeasurementsHeat;
import static com.himmerland.hero.service.helperclasses.handlejson.WriteObjectToJson.writeObjectToJson;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  public static void evaluateRuleThresholdHeat(RuleThresholdHeat rule) {
    String filePath = "src/main/resources/csv/twentyfourHoursTestData.csv";
    List<MeasurementHeat> measurements = readCSVFileToMeasurementsHeat(filePath);

    int counter = 0;

    System.out.println("Measurements Heat:");
    for (MeasurementHeat measurement : measurements) {
      if (measurement.getForwardTemperature() >= rule.getThresholdTempIn()
          && measurement.getReturnTemperature() >= rule.getThresholdTempOut()
          && measurement.getVolume() >= rule.getThresholdWaterFlow()) {
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
      Notification notification = new Notification(
          "address for the notification",
          rule.getDescription(), 
          rule.getName(),
          rule.getCriticality(), 
          OffsetDateTime.now(ZoneOffset.UTC).toString(),
          true,
          false
      );
      writeObjectToJson(filePathNotification, notification);
    } else {
      System.out.println("No notification triggered. Rule was only triggered for " + counter + " measurements.");
      writeObjectToJson(filePathNotification, "");
    }
  }
}
