package com.himmerland.hero.service.monitoring;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.himmerland.hero.service.Measurements.MeasurementCSVImporter.MeasurementCSVImporter;

public class DirectoryWatcher {

    private final Path directory;
    private final MeasurementCSVImporter importer;

    public DirectoryWatcher(Path directory, MeasurementCSVImporter importer) {
        this.directory = directory;
        this.importer = importer;
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

                    // run the CSV import
                    importer.readCSVFileToMeasurementDTOs(filePath.toString());
                }

                key.reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
