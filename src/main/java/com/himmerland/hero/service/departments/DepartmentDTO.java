package com.himmerland.hero.service.departments;



public record DepartmentDTO(String name, String email) {
    public DepartmentDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        // email is required
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
    }
}
