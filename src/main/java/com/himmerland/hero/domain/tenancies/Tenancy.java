package com.himmerland.hero.domain.tenancies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenancy extends IdentifiableBase {
    private String DepartmentName;
    private String Address;
    private String City;
    private String PostalCode;

    public Tenancy() {
        super();
    }
    
    @JsonCreator
    public Tenancy(@JsonProperty("departmentName") String departmentname, @JsonProperty("address") String address, @JsonProperty("city") String city, @JsonProperty("postalCode") String postalCode) {
        super();
        this.DepartmentName = departmentname;
        this.Address = address;
        this.City = city;
        this.PostalCode = postalCode;
    }
    
    public String getDepartmentName() {
        return DepartmentName;
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