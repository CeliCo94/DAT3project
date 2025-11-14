package com.himmerland.hero.service.helperclasses.id;

import java.util.Objects;
import java.util.UUID;

public abstract class IdentifiableBase implements Identifiable {

    private String id;

    public IdentifiableBase() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }
}
