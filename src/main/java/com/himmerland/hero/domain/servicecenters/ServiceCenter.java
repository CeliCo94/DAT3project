package com.himmerland.hero.domain.servicecenters;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class ServiceCenter extends IdentifiableBase  {
    private String name;
    private String address;
    private String description;
    private boolean isActive;

    public ServiceCenter() {
        this.isActive = true; // Default to active
    }

    public ServiceCenter(String name, String address, String description) {
        this.name =name;
        this.address = address;
        this.description = description;
        this.isActive = true;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
}
}