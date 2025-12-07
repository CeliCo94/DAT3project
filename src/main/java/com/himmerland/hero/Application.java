package com.himmerland.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;
import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.domain.notifications.Notification;
import com.himmerland.hero.domain.rules.RuleHeat;
import com.himmerland.hero.service.helperclasses.enums.Criticality;

import static com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList.readAll;
import static com.himmerland.hero.service.helperclasses.handlecsv.ReadCSVFileToMeasurementHeat.readCSVFileToMeasurementsHeat;

import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    //runStorageStrategyExample();
  }


  static void runStorageStrategyExample() {
    // Write to a real data directory, not resources
    Path dataDir = Path.of("data");

    StorageStrategy<MeasurementHeat> storageMeasurement =
        new JsonStorage<>(dataDir, "measurements", MeasurementHeat.class);

    MeasurementHeat m1 = new MeasurementHeat("0000", "Heat", "Heat", "2024-01-01T00:00:00Z", 0, 
    100.0, "m3", 60.0, "C", 40.0, "C", 
    0.0, "m3/h", 0, "", 0, "", 0, "m3/h", 20.0, "C", 50.0, "%");
    MeasurementHeat m2 = new MeasurementHeat("0001", "Heat", "Heat", "2024-01-01T01:00:00Z", 0, 
    120.0, "m3", 65.0, "C", 42.0, "C", 
    0.0, "m3/h", 0, "", 0, "", 0, "m3/h", 20.0, "C", 50.0, "%");
    
    storageMeasurement.write(m1);
    storageMeasurement.write(m2);

    MeasurementHeat readM1 = storageMeasurement.read(m1.getId()).orElseThrow();
    System.out.println("Read MeasurementHeat id=" + readM1.getId() + ", volume=" + readM1.getVolume());

    StorageStrategy<Notification> storageNotification = new JsonStorage<>(dataDir, "notifications", Notification.class);
    
    Notification n1 = new Notification("address1", "Subject1", "Content1", Criticality.Low, "2024-01-01T02:00:00Z", false, false);
    storageNotification.write(n1);
    
    Notification readN1 = storageNotification.read(n1.getId()).orElseThrow();

    System.out.println("Read Notification id=" + readN1.getId() + ", address=" + readN1.getAddress());

    List<MeasurementHeat> allMeasurements = readAll(dataDir.resolve("measurements"), MeasurementHeat.class);
    System.out.println("Total measurements stored: " + allMeasurements.size());

  }


  public static void evaluateRuleThresholdHeat(RuleHeat rule) {
    String filePath = "src/main/resources/csv/twentyfourHoursTestData.csv";
    List<MeasurementHeat> measurements = readCSVFileToMeasurementsHeat(filePath);

    int counter = 0;

    System.out.println("Measurements Heat:");
    for (MeasurementHeat measurement : measurements) {
      if (measurement.getForwardTemperature() >= rule.getThresholdTempIn()
          && measurement.getReturnTemperature() >= rule.getThresholdTempOut()
          && measurement.getVolume() >= rule.getThresholdHeatWaterFlow()) {
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
          "Broken water heater",
          rule.getName(),
          Criticality.Low,
          "2024-01-01T12:00:00Z",
          true,
          false
      );
      //writeObjectToJson(filePathNotification, notification);
    } else {
      System.out.println("No notification triggered. Rule was only triggered for " + counter + " measurements.");
      //writeObjectToJson(filePathNotification, "");
    }
  }
}

  
