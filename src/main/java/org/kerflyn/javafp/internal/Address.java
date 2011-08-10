package org.kerflyn.javafp.internal;

public class Address {
    public String city;
    public String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address(" + city + ", " + country + ")";
    }
}
