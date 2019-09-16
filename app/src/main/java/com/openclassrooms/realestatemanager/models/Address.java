package com.openclassrooms.realestatemanager.models;


public class Address {

    private String Country;
    private int zip;
    private String city;
    private String street;
    private String number;


    public Address(){

    }

    public Address(String country, int zip, String city, String street, String number) {
        Country = country;
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
