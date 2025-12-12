package com.himmerland.hero.service.tenancies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.domain.tenancies.Tenancy;

public class TenancyDTO {
    private String departmentName;
    private String address;
    private String city;
    private String postalCode;
    

    public TenancyDTO(String departmentName, String address, String city, String postalCode) {
        this.departmentName = departmentName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public String getdepartmentName() {
        return departmentName;
    }

    public String getcity() {
        return city;
    }

    public String getpostalCode() {
        return postalCode;
    }

    public static TenancyDTO fromDomain(Tenancy tenancy) {
        return new TenancyDTO(
            tenancy.getDepartmentName(),
            tenancy.getAddress(),
            tenancy.getCity(),
            tenancy.getPostalCode()
        );
    }
    /*
    public Tenancy toDomain() {
        Tenancy tenancy = new Tenancy(
            departmentName,
            address,
            city,
            postalCode
        );
        
        return tenancy;
    }
        */
}
    

