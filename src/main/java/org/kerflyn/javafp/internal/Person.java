package org.kerflyn.javafp.internal;

public class Person {
    public int someId;
    public String name;
    public Address address;

    public Person(int someId, String name, Address address) {
        this.someId = someId;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person(" + name + ", " + address + ")";
    }
}
