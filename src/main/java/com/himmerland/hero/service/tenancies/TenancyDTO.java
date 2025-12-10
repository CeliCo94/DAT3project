package com.himmerland.hero.service.tenancies;

import com.himmerland.hero.domain.tenancies.Tenancy;

public record TenancyDTO(
    String departmentName,
    String tennancyNumber,
    String address,
    String city,
    String postalCode
) {
    public static TenancyDTO fromDomain(Tenancy tenancy) {
        return new TenancyDTO(
            tenancy.getDepartmentName(),
            tenancy.getTennancyNumber(),
            tenancy.getAddress(),
            tenancy.getCity(),
            tenancy.getPostalCode()
        );
    }
    
    public Tenancy toDomain() {
        Tenancy tenancy = new Tenancy(
            departmentName,
            tennancyNumber,
            address,
            city,
            postalCode
        );
        
        return tenancy;
    }
}
    

