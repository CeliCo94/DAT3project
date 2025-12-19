package com.himmerland.hero.service.monitoring;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import com.himmerland.hero.service.measurements.MeasurementService;
import com.himmerland.hero.service.tenancies.TenancyService;
import com.himmerland.hero.service.departments.DepartmentService;

@Service
public class DirectoryWatcher {

    private final Path measurementDir = Paths.get("src/main/resources/csv");;
    private final MeasurementService measurementService;

    private final Path tenancyDir = Paths.get("src/main/resources/csvTenancy");
    private final TenancyService tenancyService;

    private final Path departmentDir = Paths.get("src/main/resources/csvDep");
    private final DepartmentService departmentService;

    public DirectoryWatcher(MeasurementService measurementService, TenancyService tenancyService, DepartmentService departmentService) {
        this.measurementService = measurementService;
        this.tenancyService = tenancyService;
        this.departmentService = departmentService;
        System.out.println("[DirectoryWatcher] Watching:");
        System.out.println(" - " + measurementDir);
        System.out.println(" - " + tenancyDir);
        System.out.println(" - " + departmentDir);
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

            Files.createDirectories(measurementDir);
            Files.createDirectories(tenancyDir);
            Files.createDirectories(departmentDir);

            measurementDir.register(
                watchService, StandardWatchEventKinds.ENTRY_CREATE);

            tenancyDir.register(
                watchService, StandardWatchEventKinds.ENTRY_CREATE);

            departmentDir.register(
                watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take();

                Path watchedDir = (Path) key.watchable();

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.OVERFLOW) continue;

                    Path fileName = (Path) event.context();
                    Path filePath = watchedDir.resolve(fileName);

                    if (!filePath.toString().endsWith(".csv")) continue;

                    System.out.println("[DirectoryWatcher] New file: " + filePath);

                    try {
                        Thread.sleep(500);   // 0.5 seconds — adjust if needed
                    } catch (InterruptedException ignored) {}

                    if (watchedDir.equals(measurementDir)) {
                        measurementService.ReadMeasurementData(filePath.toString());
                    } else if (watchedDir.equals(tenancyDir)) {
                        tenancyService.deleteAllObjects();
                        tenancyService.ReadTenancyData(filePath.toString());
                    } else if (watchedDir.equals(departmentDir)) {
                        departmentService.deleteAllObjects();
                        departmentService.ReadDepartmentData(filePath.toString());
                    }
                }

                key.reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
