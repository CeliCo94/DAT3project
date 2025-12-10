package com.himmerland.hero;

import java.nio.file.Path;
import java.util.List;

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
  }
}