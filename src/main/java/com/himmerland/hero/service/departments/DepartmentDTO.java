package com.himmerland.hero.service.departments;



public record DepartmentDTO(String id, String email, Boolean active) {
    public DepartmentDTO {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
    }
}
