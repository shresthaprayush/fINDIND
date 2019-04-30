package com.example.findingshops.Data;


public class ParkingData {
    private String longitude;
    private String latitude;
    private String nearto;
    private String name;
    private String address;
    private String type;
    private String bike;
    private String car;
    private String jeep;
    private String nearlocations;

    public ParkingData(String longitude, String latitude, String nearto, String name, String address, String type, String bike, String car, String jeep, String nearlocations) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nearto = nearto;
        this.name = name;
        this.address = address;
        this.type = type;
        this.bike = bike;
        this.car = car;
        this.jeep = jeep;
        this.nearlocations = nearlocations;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getNearto() {
        return nearto;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getBike() {
        return bike;
    }

    public String getCar() {
        return car;
    }

    public String getJeep() {
        return jeep;
    }

    public String getNearlocations() {
        return nearlocations;
    }
}
