package com.himmerland.hero.service.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.himmerland.hero.service.departments.DepartmentDTO;

import org.springframework.stereotype.Service;

@Service
public class DepCSVImporter {
    private static final String SEMICOLON_DELIMITER = ";";

    public List<DepartmentDTO> readCSVFileToDepartmentDTOs(String filePath) {
        List<DepartmentDTO> Depdtos = new ArrayList<DepartmentDTO>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SEMICOLON_DELIMITER);

                String name = safeString(values,0);
                String email = safeString(values, 1);

                DepartmentDTO department = new DepartmentDTO(
                        name, email
                );

                Depdtos.add(department);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Depdtos;
    }

    private String safeString(String[] values, int index) {
        if (index < values.length) {
            return values[index];
        } else {
            return "";
        }
    }

}
