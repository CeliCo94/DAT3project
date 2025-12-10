package com.himmerland.hero.domain.tenancies;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenancy extends IdentifiableBase {
    private String Id;
    private String DepartmentId;
    private String MeterNumber;
    private String TenancyNumber;
    private String Address; 
    private String City; 
    private String PostalCode; 

    // Convenience constructor for tests (without id - will use auto-generated from IdentifiableBase)
    public Tenancy(String meterNumber, String departmentId, String tenancyNumber, String address, String city, String postalCode) {
        super();
        this.MeterNumber = meterNumber;
        this.DepartmentId = departmentId;
        this.TenancyNumber = tenancyNumber;
        this.Address = address; 
        this.City = city; 
        this.PostalCode = postalCode;
    }

    @JsonCreator
    public Tenancy(@JsonProperty("id") String id, 
                   @JsonProperty("meterNumber") String meterNumber, 
                   @JsonProperty("departmentId") String departmentId, 
                   @JsonProperty("tenancyNumber") @JsonAlias("tennancyNumber") String tenancyNumber, 
                   @JsonProperty("address") String address, 
                   @JsonProperty("city") String city, 
                   @JsonProperty("postalCode") String postalCode) {
        super();
        this.Id = id;
        this.MeterNumber = meterNumber;
        this.DepartmentId = departmentId;
        this.TenancyNumber = tenancyNumber;
        this.Address = address; 
        this.City = city; 
        this.PostalCode = postalCode; 
        if (id != null) {
            super.setId(id);
        }
    }
    
    public String getId() {
        return Id != null ? Id : super.getId();
    }
    
    public String getDepartmentId() {
        return DepartmentId;
    }
    
    public String getMeterNumber() {
        return MeterNumber;
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
    
    
    
    public void setId(String id) {
        this.Id = id;
        if (id != null) {
            super.setId(id);
        }
    }
    
    public void setMeterNumber(String meterNumber) {
        this.MeterNumber = meterNumber;
    }
    
    public void setDepartmentId(String departmentId) {
        this.DepartmentId = departmentId;
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