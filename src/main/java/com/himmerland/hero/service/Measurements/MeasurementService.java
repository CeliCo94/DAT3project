package com.himmerland.hero.service.measurements;

import java.util.List;

import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.repositories.MeasurementRepository;

public class MeasurementService {

    private MeasurementRepository MeasurementRepo;
    
    public MeasurementService(MeasurementRepository MeasurementRepo) {
        this.MeasurementRepo = MeasurementRepo;
    }

    public boolean CreateAndSaveMeasurement(MeasurementDTO measurement) {
        try {
            MeasurementHeat m = CreateNewMeasurement(measurement);

            MeasurementRepo.save(m);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private MeasurementHeat CreateNewMeasurement(MeasurementDTO measurement) {
        return MeasurementFactory.fromDTO(measurement);
    }

    public List<MeasurementHeat> FindMeasurementsHours(int hours, String MeterNumber) {
        List<MeasurementHeat> allMeasurements = MeasurementRepo.FilterMeterLastHours(hours,MeterNumber);

        return allMeasurements;
    }
    
}
