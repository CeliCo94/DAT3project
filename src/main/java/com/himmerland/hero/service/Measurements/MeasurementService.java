package com.himmerland.hero.service.measurements;

import java.util.List;

import javax.management.monitor.Monitor;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.repositories.MeasurementRepository;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.MeasurementCSVImporter;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.monitoring.MeterService;
import com.himmerland.hero.service.monitoring.MonitoringService;

public class MeasurementService {

    private MeasurementRepository MeasurementRepo;
    private MeasurementCSVImporter importer;
    private MeterService meterService;
    private MonitoringService monitoringService;
    
    public MeasurementService(MeasurementRepository MeasurementRepo, MeasurementCSVImporter importer, MeterService meterService, MonitoringService monitoringService) {
        this.MeasurementRepo = MeasurementRepo;
        this.importer = importer;
        this.meterService = meterService;
        this.monitoringService = monitoringService;
    }

    public boolean CreateAndSaveMeasurement(MeasurementDTO measurement) {
        try {
            Measurement m = CreateNewMeasurement(measurement);

            System.out.println(m.getMeterNumber());

            MeasurementRepo.save(m);
            monitoringService.handleNewMeasurement(m);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Measurement CreateNewMeasurement(MeasurementDTO measurement) {
        Measurement m = MeasurementFactory.fromDTO(measurement);
        System.out.println(m.getMeterNumber());
        return m;
    }

    public List<Measurement> FindMeasurementsHours(int hours, String MeterNumber) {
        List<Measurement> allMeasurements = MeasurementRepo.FilterMeterLastHours(hours,MeterNumber);

        return allMeasurements;
    }

    public void ReadMeasurementData(String filepath) {
        List<MeasurementDTO> csvData = importer.readCSVFileToMeasurementDTOs(filepath);

        for (MeasurementDTO dto : csvData) {
            meterService.ensureMeterExists(dto);
            CreateAndSaveMeasurement(dto);
        }
    }
    
}
