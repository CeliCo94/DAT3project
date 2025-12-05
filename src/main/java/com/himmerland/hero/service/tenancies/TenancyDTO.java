package com.himmerland.hero.service.tenancies;

import com.himmerland.hero.domain.tenancies.Tenancy;

public record TenancyDTO(
    String id,
    String meterNumber,
    String departmentId,
    String tennancyNumber,
    String address,
    String city,
    String postalCode,
    Boolean active
) {
    public static TenancyDTO fromDomain(Tenancy tenancy) {
        return new TenancyDTO(
            tenancy.getId(),
            tenancy.getMeterNumber(),
            tenancy.getDepartmentId(),
            tenancy.getTennancyNumber(),
            tenancy.getAddress(),
            tenancy.getCity(),
            tenancy.getPostalCode(),
            tenancy.isActive()
        );
    }
    
    public Tenancy toDomain() {
        Tenancy tenancy = new Tenancy(
            meterNumber,
            departmentId,
            tennancyNumber,
            address,
            city,
            postalCode
        );
        
        if (id != null && !id.isBlank()) {
            tenancy.setId(id);
        }
        
        if (active != null) {
            tenancy.setActive(active);
        }
        
        return tenancy;
    }
}
    

