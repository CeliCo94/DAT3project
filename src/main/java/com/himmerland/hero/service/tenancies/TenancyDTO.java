package com.himmerland.hero.service.tenancies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.domain.tenancies.Tenancy;

public record TenancyDTO(
    String id,
    String meterNumber,
    @JsonProperty("departmentId") String departmentId,
    @JsonProperty("departmentName") String departmentName, // Accept departmentName as alias
    @JsonProperty("tenancyNumber") String tenancyNumber, // Changed to tenancyNumber (single 'n') to match frontend
    String address,
    String city,
    String postalCode
) {
    public static TenancyDTO fromDomain(Tenancy tenancy) {
        return new TenancyDTO(
            tenancy.getId(),
            tenancy.getMeterNumber(),
            tenancy.getDepartmentId(),
            null, // departmentName is not stored in domain
            tenancy.getTenancyNumber(),
            tenancy.getAddress(),
            tenancy.getCity(),
            tenancy.getPostalCode()
        );
    }
    
    // Helper method to get departmentId, using departmentName if departmentId is null
    public String getDepartmentId() {
        return departmentId != null && !departmentId.isBlank() ? departmentId : departmentName;
    }
    
    public Tenancy toDomain() {
        Tenancy tenancy = new Tenancy(
            meterNumber,
            getDepartmentId(), // Use helper method
            tenancyNumber,
            address,
            city,
            postalCode
        );
        
        if (id != null && !id.isBlank()) {
            tenancy.setId(id);
        }
        
        return tenancy;
    }
}
    

