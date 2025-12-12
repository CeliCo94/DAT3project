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

import java.nio.file.Path;
import java.nio.file.Paths;

@EnableConfigurationProperties(EmailProperties.class)
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
/*
    Path path = Paths.get("src\\main\\resources\\csv");

    TenancyRepository tenancyRepo = new TenancyRepository(Paths.get("data"));

    TenancyCSVImporter importer = new TenancyCSVImporter();
    TenancyService tenancyService = new TenancyService(tenancyRepo, importer);
    tenancyService.ReadTenancyData("src\\main\\resources\\csvTenancy\\tenancies(Ark1).csv");

    DepartmentRepository DepRepo = new DepartmentRepository(Paths.get("data"));

    DepCSVImporter depImporter = new DepCSVImporter();
    DepartmentService departmentService = new DepartmentService(DepRepo, depImporter);
    departmentService.ReadDepartmentData("src\\main\\resources\\csvDep\\departments(Ark1).csv");

    MeterRepository meterRepo = new MeterRepository(Paths.get("data"));
    MeasurementRepository measurementRepo = new MeasurementRepository(Paths.get("data"));

    MeterService meterService = new MeterService(meterRepo);
    MeasurementCSVImporter mesImporter = new MeasurementCSVImporter();
    MeasurementService measurementService = new MeasurementService(measurementRepo, mesImporter, meterService);
    DirectoryWatcher watcher = new DirectoryWatcher(path, measurementService);
    watcher.startWatching();
    */
  }
}