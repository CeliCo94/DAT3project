package com.himmerland.hero.domain.personnel;

import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Personnel extends IdentifiableBase {
    private String name;
    private String email;
    private String phone;
    private String role;
    private String serviceCenterId; // Connetion to ServiceCenter
    private boolean isActive;

    public Personnel() {
        this.isActive = true; // Default to Active
    }

    public Personnel(String name, String email, String phone, String role, String serviceCenterId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.serviceCenterId = serviceCenterId;
        this.isActive = true; // Default to Active
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(String serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}