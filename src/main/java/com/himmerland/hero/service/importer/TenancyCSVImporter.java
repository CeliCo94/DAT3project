package com.himmerland.hero.service.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.himmerland.hero.service.departments.DepartmentDTO;
import com.himmerland.hero.service.tenancies.TenancyDTO;

import org.springframework.stereotype.Service;

@Service
public class TenancyCSVImporter {
    private final String SEMICOLON_DELIMITER = ";";

    public TenancyCSVImporter() {}

    public List<TenancyDTO> readCSVFileToTenancyDTOs(String filePath) {
        List<TenancyDTO> Tenancydtos = new ArrayList<TenancyDTO>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEMICOLON_DELIMITER);

                String name = safeString(values,0);
                String address = safeString(values, 1);
                String city = safeString(values,2);
                String postalcode = safeString(values, 3);

                TenancyDTO department = new TenancyDTO(
                        name, address, city, postalcode
                );

                Tenancydtos.add(department);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Tenancydtos;
    }

    private String safeString(String[] values, int index) {
        if (index < values.length) {
            return values[index];
        } else {
            return ""; // default for strings
        }
    }

}
