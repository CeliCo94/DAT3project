package com.himmerland.hero.domain.tenancies;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenancy extends IdentifiableBase {
    private String DepartmentName;
    private String TenancyNumber;
    private String Address;
    private String City;
    private String PostalCode;

    public Tenancy() {
        super();
    }

    // Convenience constructor for tests (without id - will use auto-generated from IdentifiableBase)
    public Tenancy(String departmentName, String tenancyNumber, String address, String city, String postalCode) {
        super();
        this.DepartmentName = departmentName;
        this.TenancyNumber = tenancyNumber;
        this.Address = address;
        this.City = city;
        this.PostalCode = postalCode;
    }
    
    public String getDepartmentName() {
        return DepartmentName;
    }
    
    public String getTenancyNumber() {
        return TenancyNumber;
    }
    
    public String getAddress() {
        return Address;
    }
    
    public String getCity() {
        return City;
    }
    
    public String getPostalCode() {
        return PostalCode;
    }
    
    public void setDepartmentName(String departmentName) {
        this.DepartmentName = departmentName;
    }
    
    public void setTenancyNumber(String tenancyNumber) {
        this.TenancyNumber = tenancyNumber;
    }
    
    public void setAddress(String address) {
        this.Address = address;
    }
    
    public void setCity(String city) {
        this.City = city;
    }
    
    public void setPostalCode(String postalCode) {
        this.PostalCode = postalCode;
    }
}