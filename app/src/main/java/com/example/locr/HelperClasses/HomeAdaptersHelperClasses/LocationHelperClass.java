package com.example.locr.HelperClasses.HomeAdaptersHelperClasses;

public class LocationHelperClass {
    String name,address,distance;
    int image;
    Double lat,lon;

    public LocationHelperClass(String name, String address, String distance, int image, Double lat, Double lon) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public int getImage() {
        return image;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
