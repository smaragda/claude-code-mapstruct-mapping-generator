package com.mapper.generator.test.advanced;

import java.util.Date;
import java.util.List;

/**
 * Entity class for Worker.
 */
public class WorkerEntity {
    private String name;
    private String id;
    private int age;
    private Date hireDate;
    private List<DepartmentEntity> departments;
    private WorkerContact contact;
    private String[] tasks;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public Date getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
    
    public List<DepartmentEntity> getDepartments() {
        return departments;
    }
    
    public void setDepartments(List<DepartmentEntity> departments) {
        this.departments = departments;
    }
    
    public WorkerContact getContact() {
        return contact;
    }
    
    public void setContact(WorkerContact contact) {
        this.contact = contact;
    }
    
    public String[] getTasks() {
        return tasks;
    }
    
    public void setTasks(String[] tasks) {
        this.tasks = tasks;
    }
    
    /**
     * Worker contact information entity.
     */
    public static class WorkerContact {
        private String email;
        private String phone;
        private AddressEntity address;
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
        
        public AddressEntity getAddress() {
            return address;
        }
        
        public void setAddress(AddressEntity address) {
            this.address = address;
        }
    }
}

/**
 * Department entity.
 */
class DepartmentEntity {
    private String name;
    private String code;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
}

/**
 * Address entity.
 */
class AddressEntity {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}