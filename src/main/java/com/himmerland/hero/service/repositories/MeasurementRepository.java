package com.himmerland.hero.service.repositories;

import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.ReadAll;
import com.himmerland.hero.service.io.StorageStrategy;

public class MeasurementRepository {
    
    private final StorageStrategy<MeasurementHeat> storage;

    public MeasurementRepository() {
        Path dataDir = Path.of("data");
        this.storage = new JsonStorage<MeasurementHeat>(dataDir, "measurements", MeasurementHeat.class);
    }

    public boolean save(MeasurementHeat measurement) {
        try {
            storage.write(measurement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    public List<MeasurementHeat> findAll() {
        String id = "1";
        return storage.read(id);
    }
        */

    public List<MeasurementHeat> FilterForMeterNumber(String MeterNumber) {
        List<MeasurementHeat> MeasurementList = new ArrayList<>();

        

        return MeasurementList;
    }

    public List<MeasurementHeat> findAllMeasurements() {
        if (storage instanceof ReadAll) {
            return ((ReadAll<MeasurementHeat>) storage).readAll();
        } else {
            throw new UnsupportedOperationException("readAll() not supported by this storage");
        }
    }

}
