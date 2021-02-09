package com.qgsstrive.sciencemap;

/**
 * @date 2021/2/3 8:59
 */
public class GPSUser {
    private int id;
    private String lat;
    private String lon;

    public GPSUser() {
        super();
    }

    public GPSUser(int id, String lat, String lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
