package com.bgs.homeshare.Models;

import com.bgs.homeshare.Models.*;

public class Property {
    private int propertyID = -1;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private int rent;
    private int maximumCapacity;
    private int squareFeet;
    private final double distanceToCampus;
    public PropertyUtilities utilities;

    public Property(int propertyID, String streetAddress1, String streetAddress2, String city, String state, String country, int rent, int maximumCapacity, int squareFeet, PropertyUtilities utilities, double distanceToCampus){
        this.propertyID = propertyID;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state =  state;
        this.country = country;
        this.rent = rent;
        this.maximumCapacity = maximumCapacity;
        this.squareFeet = squareFeet;
        this.utilities = utilities;
        this.distanceToCampus = distanceToCampus;
    }

    public void setStreetAddress1(String val){
        this.streetAddress1 = val;
    }

    public void setStreetAddress2(String val){
        this.streetAddress2 = val;
    }

    public void setCity(String val){
        this.city = val;
    }

    public void setState(String val){
        this.state = val;
    }

    public void setCountry(String val){
        this.country = val;
    }

    public void setRent(int val){
        this.rent = val;
    }

    public void setMaximumCapacity(int val){
        this.maximumCapacity = val;
    }

    public void setSquareFeet(int val){
        this.squareFeet = val;
    }

    public int getSquareFeet(){
        return this.squareFeet;
    }

    public String getStreetAddress1(){
        return this.streetAddress1;
    }

    public String getStreetAddress2(){
        return this.streetAddress2;
    }

    public String getCity(){
        return this.city;
    }

    public String getState(){
        return this.state;
    }

    public String getCountry(){
        return this.country;
    }

    public int getRent(){
        return this.rent;
    }

    public int getMaximumCapacity(){
        return this.maximumCapacity;
    }

    public double getDistanceToCampus(){
        return this.distanceToCampus;
    }

    public int getPropertyID(){
        return this.propertyID;
    }

    public PropertyUtilities getUtilities(){
        return this.utilities;
    }

    public String getStringUtilities(){
        return this.utilities.getUtilities();
    }

//    public void setAddress(String addressline1, String addressline2, String City, String State, String Country){
//        this.streetAddress1 = addressline1;
//        this.streetAddress2 = addressline2;
//        this.city = City;
//        this.state = State;
//        this.country = country;
//    }

    public String getAddress(){
        String address = streetAddress1;
        if(!streetAddress2.contentEquals("")){
            address += ("," + streetAddress2);
        }
        address += ("," + city);
        address += ("," + state);
        address += ("," + country);
        return address;
    }



}
