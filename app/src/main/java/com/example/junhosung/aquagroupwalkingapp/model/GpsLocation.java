package com.example.junhosung.aquagroupwalkingapp.model;

/**
 * Created by karti on 2018-03-21.
 */

public class GpsLocation {
    private double lat;
    private double lng;
    private String timestamp;

    public GpsLocation(){

    }

    public GpsLocation(double lat, double lng, String timestamp) {
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}