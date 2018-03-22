package com.example.junhosung.aquagroupwalkingapp.model;

/**
 * Created by Junho Sung on 3/21/2018.
 */
public class GpsLocation {
    private double lat;
    private double lng;
    private double timestamp;

    public GpsLocation(){

    }

    public GpsLocation(double lat, double lng, double timestamp) {
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

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}