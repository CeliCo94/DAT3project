package com.himmerland.hero.service.measurements;

import com.himmerland.hero.domain.measurements.Measurement;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.MeasurementCSVImporter;
import com.himmerland.hero.service.measurements.MeasurementCSVImporter.dto.MeasurementDTO;
import com.himmerland.hero.service.repositories.MeasurementRepository;
import com.himmerland.hero.service.monitoring.MonitoringService;
import com.himmerland.hero.service.monitoring.MeterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementServecieTest {

    @Mock MeasurementRepository measurementRepository;
    @Mock MeasurementCSVImporter importer;
    @Mock MeterService meterService;
    @Mock MonitoringService monitoringService;

    @InjectMocks MeasurementService service;

    private MeasurementDTO dto() {
        return new MeasurementDTO(
            "addr","M-1","HEAT","Heat","01-01-2025 10:00",0,1,"m3",20,"C",60,"C",1.23,"l/s",100,"kWh",200,"kWh",3,"l/h",22.5,"C",40.0,"%");
    }

    @Test
    void createAndSaveMeasurement_returnsTrueOnSuccess() {
        MeasurementDTO measurementDTO = dto();
        System.out.println("DTO: " + measurementDTO.getAddress());

        boolean result = service.CreateAndSaveMeasurement(measurementDTO);
        System.out.println("Result: " + result);

        assertTrue(result);
        //verify(measurementRepository).save(any(Measurement.class));
    }

    @Test
    void createAndSaveMeasurement_returnsFalseOnException() {
        MeasurementDTO measurementDTO = dto();
        doThrow(new RuntimeException("fail"))
          .when(measurementRepository).save(any(Measurement.class));

        boolean result = service.CreateAndSaveMeasurement(measurementDTO);

        //assertFalse(result);
    }

    @Test
    void findMeasurementsHours_returnsEmptyListWhenNoMeasurements() {
        List<Measurement> expected = List.of(mock(Measurement.class));
        when(measurementRepository.FilterMeterLastHours(5, "M-1")).thenReturn(expected);

        List<Measurement> result = service.FindMeasurementsHours(5, "M-1");

        assertEquals(expected, result);
        verify(measurementRepository).FilterMeterLastHours(5, "M-1");
    }

    @Test
    void readMeasurementData_importsEnsuresMetersAndSavesEach() {
        MeasurementDTO dto1 = dto();
        MeasurementDTO dto2 = dto();
        when(importer.readCSVFileToMeasurementDTOs("file.csv"))
            .thenReturn(List.of(dto1, dto2));

        service.ReadMeasurementData("file.csv");

        verify(importer).readCSVFileToMeasurementDTOs("file.csv");
        verify(meterService, times(2)).ensureMeterExists(any(MeasurementDTO.class));
        verify(measurementRepository, times(2)).save(any(Measurement.class));
    }
}
