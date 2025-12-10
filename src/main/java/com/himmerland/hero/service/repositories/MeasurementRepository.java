package com.himmerland.hero.service.repositories;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.himmerland.hero.domain.measurements.Measurement;

import org.springframework.stereotype.Repository;

@Repository
public class MeasurementRepository extends BaseRepository<Measurement>{

    public MeasurementRepository(Path dataDir) {
        super(dataDir, "measurements", Measurement.class);
    }

    public List<Measurement> FilterForMeterNumber(String MeterNumber) {
        List<Measurement> MeasurementList = findAll().stream()
                .filter(m -> m.getMeterNumber().equals(MeterNumber))
                .toList();

        return MeasurementList;
    }

    public List<Measurement> FilterMeterLastHours(int hours, String MeterNumber) {
        List<Measurement> MeasurementList = FilterForMeterNumber(MeterNumber);

        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime cutoff = LocalDateTime.now().minusHours(hours);

        return MeasurementList.stream()
                // convert string timestamp -> LocalDateTime
                .filter(m -> {
                    try {
                        LocalDateTime ts = LocalDateTime.parse(m.getTimestamp(), formatter);
                        return ts.isAfter(cutoff);
                    } catch (Exception e) {
                        // skip malformed date entries
                        return false;
                    }
                })
                .toList();
    }

    @Override
    protected Class<Measurement> getEntityClass() {
        return Measurement.class;
    }

}
