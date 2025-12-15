package com.himmerland.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.himmerland.hero.config.EmailProperties;
import com.himmerland.hero.service.measurements.MeasurementService;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.MeasurementCSVImporter;
import com.himmerland.hero.service.monitoring.DirectoryWatcher;
import com.himmerland.hero.service.monitoring.MeterService;
import com.himmerland.hero.service.repositories.MeasurementRepository;
import com.himmerland.hero.service.repositories.MeterRepository;
import com.himmerland.hero.service.repositories.TenancyRepository;
import com.himmerland.hero.service.tenancies.TenancyService;
import com.himmerland.hero.service.importer.DepCSVImporter;
import com.himmerland.hero.service.importer.TenancyCSVImporter;
import com.himmerland.hero.service.departments.DepartmentService;
import com.himmerland.hero.service.repositories.DepartmentRepository;
import com.himmerland.hero.service.importer.DepCSVImporter;
import com.himmerland.hero.service.monitoring.DirectoryWatcher;

import java.nio.file.Path;
import java.nio.file.Paths;

@EnableConfigurationProperties(EmailProperties.class)
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}