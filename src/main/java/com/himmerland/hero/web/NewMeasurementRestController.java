package com.himmerland.hero.web;

import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.service.monitoring.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewMeasurementRestController {

    private final MonitoringService monitoringService;

    // Constructor injection – Spring will inject the bean
    public NewMeasurementRestController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    // Temporary test endpoint
    @GetMapping("/measurements/test")
    public ResponseEntity<Void> onNewMeasurement() {
        MeasurementHeat measurement = new MeasurementHeat();
        measurement.setVolume(700); // just a dummy value for now

        monitoringService.handleNewMeasurement(measurement);

        return ResponseEntity.ok().build();
    }
}
