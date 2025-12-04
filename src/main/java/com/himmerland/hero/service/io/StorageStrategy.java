package com.himmerland.hero.service.io;

import com.himmerland.hero.service.helperclasses.id.Identifiable;

import java.util.Optional;

public interface StorageStrategy<T extends Identifiable> {
    Optional<T> read(String id);
    void write(T value);
    void delete(String id);
}
