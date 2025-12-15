package com.himmerland.hero;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.domain.meters.Meter;
import com.himmerland.hero.domain.rules.RuleWater;
import com.himmerland.hero.service.monitoring.MonitoringService;
import com.himmerland.hero.service.repositories.MeterRepository;
import com.himmerland.hero.service.repositories.RuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleEngineTestRunner {

    @Bean
    public CommandLineRunner runRuleEngineTest(MonitoringService monitoringService,
                                               MeterRepository meterRepository,
                                               RuleRepository ruleRepository) {
        return args -> {

            System.out.println("=== RULE ENGINE TEST RUNNER START ===");

            // 1) Create and save a meter so RuleContext can resolve it by meterNumber
            String meterNumber = "WATER-001";
            Meter meter = new Meter(meterNumber, "WATER", "Test Address 1");

            // Save meter (adjust if your repository uses a different method name)
            meterRepository.save(meter);
            System.out.println("[TestRunner] Saved meter: " + meterNumber);

            // 3) Create two measurements that break the rule (Flow > 600)
            Measurement m1 = new Measurement(
                    "Test Address 1",        // address
                    meterNumber,             // meterNumber
                    "WATER_METER",           // meterType
                    "WATER",                 // consumptionType
                    "2025-11-25T10:00:00Z",  // timestamp
                    0,                       // infoCode

                    0.0, "L",                // volume + unit
                    0.0, "C",                // forwardTemp + unit
                    0.0, "C",                // returnTemp + unit
                    700.0, "L/H",            // Flow + FlowUnit  (BREAKS threshold 600)

                    0, "",                   // RegisterE8 + unit
                    0, "",                   // RegisterE9 + unit
                    0, "",                   // averageFlow + unit
                    0.0, "C",                // ambientTemperature + unit
                    0.0, "%"                 // humidity + unit
            );

            Measurement m2 = new Measurement(
                    "Test Address 1",
                    meterNumber,
                    "WATER_METER",
                    "WATER",
                    "2025-11-25T11:00:00Z",
                    0,

                    0.0, "L",
                    0.0, "C",
                    0.0, "C",
                    800.0, "L/H",            // Flow still breaking

                    0, "",
                    0, "",
                    0, "",
                    0.0, "C",
                    0.0, "%"
            );

            // 4) Call MonitoringService twice.
            // Expected behavior:
            // - first call: counter = 1 -> no notification
            // - second call: counter = 2 -> notification generated + saved
            System.out.println("[TestRunner] Sending measurement #1 (flow=700)");
            monitoringService.handleNewMeasurement(m1);

            System.out.println("[TestRunner] Sending measurement #2 (flow=800)");
            monitoringService.handleNewMeasurement(m2);

            System.out.println("=== RULE ENGINE TEST RUNNER END ===");
        };
    }
}
