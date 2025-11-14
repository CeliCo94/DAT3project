package com.himmerland.hero.service.helperclasses.handlejson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public final class ReadAllJsonToList {

    private static final ObjectMapper mapper = new ObjectMapper();

    private ReadAllJsonToList() {
        // utility class — prevent instantiation
    }

    public static <T> List<T> readAll(Path directory, Class<T> modelClass) {
        if (!Files.exists(directory) || !Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Invalid directory: " + directory);
        }

        try (Stream<Path> files = Files.list(directory)) {
            return files
                .filter(Files::isRegularFile)
                .filter(f -> f.toString().endsWith(".json"))
                .map(f -> {
                    try {
                        return mapper.readValue(f.toFile(), modelClass);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to read file: " + f, e);
                    }
                })
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files in " + directory, e);
        }
    }
}
