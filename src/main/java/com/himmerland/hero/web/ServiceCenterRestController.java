package com.himmerland.hero.web;

import com.himmerland.hero.domain.servicecenters.ServiceCenter;
import com.himmerland.hero.service.ServiceCenterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicecenters")
@CrossOrigin
public class ServiceCenterRestController {

    private final ServiceCenterService serviceCenterService;

    public ServiceCenterRestController() {
        // Initialize with data directory
        Path dataDir = Path.of("data");
        this.serviceCenterService = new ServiceCenterService(dataDir);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceCenter>> getAll() {
        List<ServiceCenter> serviceCenters = serviceCenterService.findAll();
        return ResponseEntity.ok(serviceCenters);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceCenter>> getActive() {
        List<ServiceCenter> activeServiceCenters = serviceCenterService.findActive();
        return ResponseEntity.ok(activeServiceCenters);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceCenter> getById(@PathVariable String id) {
        Optional<ServiceCenter> serviceCenter = serviceCenterService.findById(id);
        return serviceCenter
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceCenter> create(@RequestBody ServiceCenter serviceCenter) {
        ServiceCenter created = serviceCenterService.create(serviceCenter);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceCenter> update(@PathVariable String id, @RequestBody ServiceCenter serviceCenter) {
        Optional<ServiceCenter> existing = serviceCenterService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ServiceCenter updated = serviceCenterService.update(id, serviceCenter);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Optional<ServiceCenter> existing = serviceCenterService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        serviceCenterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
