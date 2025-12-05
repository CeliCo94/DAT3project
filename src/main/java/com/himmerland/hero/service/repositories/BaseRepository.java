package com.himmerland.hero.service.repositories;

import com.himmerland.hero.service.helperclasses.handlejson.ReadAllJsonToList;
import com.himmerland.hero.service.helperclasses.id.Identifiable;
import com.himmerland.hero.service.io.JsonStorage;
import com.himmerland.hero.service.io.StorageStrategy;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends Identifiable> {

    protected final StorageStrategy<T> storage;
    protected final Path entityDir;

    public BaseRepository(Path dataDir, String entityName, Class<T> entityClass) {
        this.entityDir = dataDir.resolve(entityName);
        this.storage = new JsonStorage<>(dataDir, entityName, entityClass);
    }

    //Core operation 1: Find by ID
    public Optional<T> findById(String id) {
        return storage.read(id);
    }

    //Core operation 2: Save (create or update)
    public T save(T entity) {
        storage.write(entity);
        return entity;
    }

    //Core operation 3: Find all
    public List<T> findAll() {
        return ReadAllJsonToList.readAll(entityDir, getEntityClass());
    }

    //Core operation 4: Delete by ID
    public void deleteById(String id) {
        storage.delete(id);
    }

    public boolean existsById(String id) {
        return storage.read(id).isPresent();
    }

    protected abstract Class<T> getEntityClass();
}
