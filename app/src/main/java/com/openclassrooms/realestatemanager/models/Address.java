package com.openclassrooms.realestatemanager.models;


import androidx.annotation.NonNull;

public class Address {

    private String Country;
    private String zip;
    private String city;
    private String street;
    private String number;


    public Address(){

    }

    public Address(String country, String zip, String city, String street, String number) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Override
    @NonNull
    public String toString() {
        return "Address{" +
                "Country='" + Country + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
