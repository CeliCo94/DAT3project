package com.himmerland.hero.service.helperclasses.handlejson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class JsonFileWriter {
    public static void writeFileToJson(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("name", "Abul Hasan");
            jsonNode.put("age", 23);
            jsonNode.put("city", "Lucknow");
            jsonNode.put("state", "Uttar Pradesh");
            jsonNode.put("country", "India");
            objectMapper.writeValue(new File(filePath), jsonNode);
        } catch (IOException e) {
            throw new RuntimeException("Failed writing JSON file", e);
        }
    }
}