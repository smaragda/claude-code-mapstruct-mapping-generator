package com.mapper.generator.test;

import java.util.List;

/**
 * Example source class for testing mapper generation.
 */
public class SourceExample {
    private String name;
    private int age;
    private List<SourceNested> children;
    private SourceAddress address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<SourceNested> getChildren() {
        return children;
    }

    public void setChildren(List<SourceNested> children) {
        this.children = children;
    }

    public SourceAddress getAddress() {
        return address;
    }

    public void setAddress(SourceAddress address) {
        this.address = address;
    }
}

class SourceNested {
    private String firstName;
    private int childAge;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getChildAge() {
        return childAge;
    }

    public void setChildAge(int childAge) {
        this.childAge = childAge;
    }
}

class SourceAddress {
    private String streetName;
    private String city;
    private String countryName;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}