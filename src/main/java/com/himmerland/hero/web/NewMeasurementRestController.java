package com.himmerland.hero.web;

import com.himmerland.hero.domain.measurements.MeasurementHeat;
import com.himmerland.hero.service.monitoring.MonitoringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/measurements")
public class NewMeasurementRestController {

    private final MonitoringService monitoringService;

    @Value("${app.test-endpoints.enabled:true}")
    private boolean testEndPointsEnabled;

    public NewMeasurementRestController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/test")
    public ResponseEntity<Void> onNewMeasurement() {
        if (!testEndPointsEnabled) {
            return ResponseEntity.notFound().build();
        }

        for (int i = 0; i < 9; i++) {
            MeasurementHeat measurement = new MeasurementHeat();
            measurement.setVolume(ThreadLocalRandom.current().nextInt(0, 1000));
            monitoringService.handleNewMeasurement(measurement);
        }

        return ResponseEntity.ok().build();
    }
}
