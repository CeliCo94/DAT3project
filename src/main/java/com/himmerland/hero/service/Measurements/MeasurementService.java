package com.himmerland.hero.service.Measurements;

import java.util.List;

import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.service.Measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.repositories.MeasurementRepository;

public class MeasurementService {

    private MeasurementRepository MeasurementRepo;
    
    public MeasurementService(MeasurementRepository MeasurementRepo) {
        this.MeasurementRepo = MeasurementRepo;
    }

    public boolean CreateAndSaveMeasurement(MeasurementDTO measurement) {
        try {
            //MeasurementHeat m = CreateNewMeasurement(measurement);

            //MeasurementRepo.save(m);

            List<MeasurementHeat> allMeasurements = MeasurementRepo.findAllMeasurements();

            for (MeasurementHeat m : allMeasurements) {
            System.out.println("Volume: " + m.getVolume());
            System.out.println("Meter Type: " + m.getConsumptionType());
            System.out.println("Flow: " + m.getFlow());
            System.out.println("RegisterE8: " + m.getRegisterE8());
            System.out.println("---------------------------");
            }

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
    
}
