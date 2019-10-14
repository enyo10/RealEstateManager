package com.openclassrooms.realestatemanager.models;


import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class Address {

    private String country;
    private String zip;
    private String city;
    private String street;
    private String number;


    public Address(){

    }
@Ignore
    public Address(String country, String zip, String city, String street, String number) {
        this.country = country;
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
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
                "Country='" + country + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public String format(){
        String address = (street +" " +number+" " +city + " " + country
                + " " + zip).toLowerCase();
        return address.replace(" ", "+");


    }
}
