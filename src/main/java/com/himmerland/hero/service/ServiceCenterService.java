package com.himmerland.hero.service;

import com.himmerland.hero.domain.servicecenters.ServiceCenter;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;
import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class ServiceCenterService {

    private final StorageStrategy<ServiceCenter> storage;

    public ServiceCenterService(Path dataDir) {
        this.storage = new JsonStorage<>(dataDir, "servicecenters", ServiceCenter.class);

    }

    public ServiceCenter create(ServiceCenter serviceCenter) {
        storage.write(serviceCenter);
        return serviceCenter;
    }

    public Optional<ServiceCenter> findById(String id) {
        return storage.read(id);
    }
    
    public List<ServiceCenter> findAll() {
        return ReadAllJsonToList.readAll(
            storage instanceof JsonStorage ?
            ((JsonStorage<ServiceCenter>)storage).getBaseDir() :
            Path.of("data/servicecenters"),
            ServiceCenter.class
        );
    }

    public ServiceCenter update(String id, ServiceCenter serviceCenter) {
        serviceCenter.setId(id);
        storage.write(serviceCenter);
        return serviceCenter;
    }

    public void delete(String id) {
        storage.delete(id);
    }

    public List<ServiceCenter> findActive() {
        return findAll().stream()
        .filter(ServiceCenter::isActive)
        .toList();
    }
}
