package com.himmerland.hero.service.tenancies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.domain.tenancies.Tenancy;

public record TenancyDTO(
    String departmentName,
    String tenancyNumber,
    String address,
    String city,
    String postalCode
) {
    public static TenancyDTO fromDomain(Tenancy tenancy) {
        return new TenancyDTO(
            tenancy.getDepartmentName(),
            tenancy.getTenancyNumber(),
            tenancy.getAddress(),
            tenancy.getCity(),
            tenancy.getPostalCode()
        );
    }
    
    
    
    public Tenancy toDomain() {
        Tenancy tenancy = new Tenancy(
            departmentName,
            tenancyNumber,
            address,
            city,
            postalCode
        );
        
        return tenancy;
    }
}
    

