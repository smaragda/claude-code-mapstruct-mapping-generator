package com.mapper.generator.test;

import java.util.List;

/**
 * Example target class for testing mapper generation.
 */
public class TargetExample {
    private String fullName;
    private int personAge;
    private List<TargetChild> kids;
    private TargetLocation location;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    public List<TargetChild> getKids() {
        return kids;
    }

    public void setKids(List<TargetChild> kids) {
        this.kids = kids;
    }

    public TargetLocation getLocation() {
        return location;
    }

    public void setLocation(TargetLocation location) {
        this.location = location;
    }
}

class TargetChild {
    private String childName;
    private int age;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class TargetLocation {
    private String street;
    private String cityName;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}