package com.himmerland.hero.service.helperclasses.handlejson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class WriteObjectToJson {
    public static void writeObjectToJson(String filePath, Object newObject) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            File file = new File(filePath);
            mapper.writeValue(file, newObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
