package com.himmerland.hero.service.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.himmerland.hero.domain.measurements.MeasurementHeat;

class JsonStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteAndRead() {
        JsonStorage<MeasurementHeat> storage =
                new JsonStorage<>(tempDir, "measurements", MeasurementHeat.class);

        MeasurementHeat m =
                new MeasurementHeat("0000", "Heat", "Heat",
                        "2024-01-01T00:00:00Z", 0,
                        100.0, "m3",
                        60.0, "C",
                        40.0, "C",
                        5.0, "m3/h",
                        0, "kWh",
                        0, "kWh",
                        3, "m3/h",
                        20.0, "C",
                        50.0, "%");

        storage.write(m);

        Path expectedFile = tempDir
                .resolve("measurements")
                .resolve(m.getId() + ".json");

        assertTrue(Files.exists(expectedFile), "JSON file should be created");

        Optional<MeasurementHeat> read = storage.read(m.getId());
        assertTrue(read.isPresent(), "Object should be readable");

        assertEquals(m.getVolume(), read.get().getVolume());
        assertEquals(m.getForwardTemperature(), read.get().getForwardTemperature());
        assertEquals(m.getReturnTemperature(), read.get().getReturnTemperature());
    }

    @Test
    void testDelete() {
        JsonStorage<MeasurementHeat> storage =
                new JsonStorage<>(tempDir, "measurements", MeasurementHeat.class);

        MeasurementHeat m =
                new MeasurementHeat("0001", "Heat", "Heat",
                        "2024-01-01T01:00:00Z", 0,
                        120.0, "m3",
                        65.0, "C",
                        42.0, "C",
                        6.0, "m3/h",
                        0, "kWh",
                        0, "kWh",
                        4, "m3/h",
                        21.0, "C",
                        55.0, "%");

        storage.write(m);

        Path filePath = tempDir
                .resolve("measurements")
                .resolve(m.getId() + ".json");

        assertTrue(Files.exists(filePath), "File must exist before delete");

        storage.delete(m.getId());

        assertFalse(Files.exists(filePath), "File must be deleted");
    }

    @Test
    void testReadMissingReturnsEmptyOptional() {
        JsonStorage<MeasurementHeat> storage =
                new JsonStorage<>(tempDir, "measurements", MeasurementHeat.class);

        Optional<MeasurementHeat> result = storage.read("does-not-exist");

        assertTrue(result.isEmpty(), "Unknown file should return Optional.empty()");
    }
}
