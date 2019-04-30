package com.example.findingshops.Data;

public class LocalData {

    private String name;
    private String contact;
    private String address;
    private String latitude;
    private String longitude;
    private String color;
    private String nearlocations;


    public LocalData(String name, String contact, String address, String latitude, String longitude, String color, String nearlocations) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.color = color;
        this.nearlocations = nearlocations;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getColor() {
        return color;
    }

    public String getNearlocations() {
        return nearlocations;
    }
}

