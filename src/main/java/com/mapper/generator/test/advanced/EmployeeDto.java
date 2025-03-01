package com.mapper.generator.test.advanced;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for Employee.
 */
public class EmployeeDto {
    private String employeeName;
    private String employeeIdentifier;
    private int employeeAge;
    private Date dateJoined;
    private List<DepartmentDto> assignedDepartments;
    private ContactInfo contactInformation;
    private String[] responsibilities;
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getEmployeeIdentifier() {
        return employeeIdentifier;
    }
    
    public void setEmployeeIdentifier(String employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
    }
    
    public int getEmployeeAge() {
        return employeeAge;
    }
    
    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }
    
    public Date getDateJoined() {
        return dateJoined;
    }
    
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
    
    public List<DepartmentDto> getAssignedDepartments() {
        return assignedDepartments;
    }
    
    public void setAssignedDepartments(List<DepartmentDto> assignedDepartments) {
        this.assignedDepartments = assignedDepartments;
    }
    
    public ContactInfo getContactInformation() {
        return contactInformation;
    }
    
    public void setContactInformation(ContactInfo contactInformation) {
        this.contactInformation = contactInformation;
    }
    
    public String[] getResponsibilities() {
        return responsibilities;
    }
    
    public void setResponsibilities(String[] responsibilities) {
        this.responsibilities = responsibilities;
    }
    
    /**
     * Contact information DTO.
     */
    public static class ContactInfo {
        private String emailAddress;
        private String phoneNumber;
        private AddressDto homeAddress;
        
        public String getEmailAddress() {
            return emailAddress;
        }
        
        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
        
        public String getPhoneNumber() {
            return phoneNumber;
        }
        
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
        
        public AddressDto getHomeAddress() {
            return homeAddress;
        }
        
        public void setHomeAddress(AddressDto homeAddress) {
            this.homeAddress = homeAddress;
        }
    }
}

/**
 * Department DTO.
 */
class DepartmentDto {
    private String departmentName;
    private String departmentCode;
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getDepartmentCode() {
        return departmentCode;
    }
    
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}

/**
 * Address DTO.
 */
class AddressDto {
    private String streetLine;
    private String cityName;
    private String stateProvince;
    private String postalCode;
    private String countryName;
    
    public String getStreetLine() {
        return streetLine;
    }
    
    public void setStreetLine(String streetLine) {
        this.streetLine = streetLine;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public String getStateProvince() {
        return stateProvince;
    }
    
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}