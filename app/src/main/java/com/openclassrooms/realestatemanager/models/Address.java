package com.openclassrooms.realestatemanager.models;

public class Address {
    private String Country;
    private String zip;
    private String city;
    private String street;
    private int number;


    public Address(){

    }

    public Address(String country, String zip, String city, String street, int number) {
        Country = country;
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
