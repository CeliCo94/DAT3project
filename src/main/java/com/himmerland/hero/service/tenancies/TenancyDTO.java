package com.himmerland.hero.service.tenancies;

public record TenancyDTO(
    String id,
    String meterNumber,
    String departmentId,
    String tennancyNumber,
    String address,
    String city,
    String postalCode,
    Boolean active
) {}
    

