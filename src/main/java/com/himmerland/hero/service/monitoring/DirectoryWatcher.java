package com.himmerland.hero.service.monitoring;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import com.himmerland.hero.service.measurements.MeasurementService;

@Service
public class DirectoryWatcher {

    private final Path directory = Paths.get("src/main/resources/csv");;
    private final MeasurementService measurementService;

    public DirectoryWatcher(MeasurementService measurementService) {
        this.measurementService = measurementService;
        System.out.println("[DirectoryWatcher] Watching directory: " + directory.toString());
    }

    @PostConstruct
    public void start() {
        startWatching();
    }

    public void startWatching() {
        Thread watcherThread = new Thread(this::runWatcher);
        watcherThread.setDaemon(true); 
        watcherThread.start();
    }

    private void runWatcher() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take(); // blocks here until file arrives

                for (WatchEvent<?> event : key.pollEvents()) {
                    String fileName = event.context().toString();
                    Path filePath = directory.resolve(fileName);

                    try {
                        Thread.sleep(500);   // 0.5 seconds — adjust if needed
                    } catch (InterruptedException ignored) {}

                    // run the CSV import
                    measurementService.ReadMeasurementData(filePath.toString());
                }

                key.reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
