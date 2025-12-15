package com.himmerland.hero.service.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.himmerland.hero.service.helperclasses.id.Identifiable;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public final class JsonStorage<T extends Identifiable> implements StorageStrategy<T> {

    private final Path baseDir;
    private final Class<T> modelClass;
    private final ObjectMapper mapper;

    public JsonStorage(Path rootPath, String type, Class<T> modelClass) {
        this.baseDir    = rootPath.resolve(type);
        this.modelClass = modelClass;
        this.mapper     = new ObjectMapper();
        ensureDir();
        System.out.println("[JsonStorage] Initialized for type: " + type + " at " + baseDir.toString());
    }

    private void ensureDir() {
        try {
            Files.createDirectories(baseDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory " + baseDir, e);
        }
    }

    private Path file(String id) {
        return baseDir.resolve(id + ".json");
    }

    public Path getBaseDir() {
        return baseDir;
    }

    @Override
    public Optional<T> read(String id) {
        Path f = file(id);
        if (!Files.exists(f)) return Optional.empty();
        try {
            return Optional.of(mapper.readValue(f.toFile(), modelClass));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file for id=" + id, e);
        }
    }

    @Override
    public void write(T value) {
        String id = value.getId();
        Path target = file(id);
        try {
            Path tmp = Files.createTempFile(baseDir, id + "-", ".tmp");
            mapper.writeValue(tmp.toFile(), value);
            Files.move(tmp, target, ATOMIC_MOVE, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file for id=" + id, e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            Files.deleteIfExists(file(id));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file for id=" + id, e);
        }
    }

    @Override
    public List<T> findAll() {
        if (!Files.exists(baseDir) || !Files.isDirectory(baseDir)) {
            throw new IllegalStateException("Invalid base directory: " + baseDir);
        }

        try (Stream<Path> files = Files.list(baseDir)) {
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
            throw new RuntimeException("Failed to list files in " + baseDir, e);
        }
    }
}
