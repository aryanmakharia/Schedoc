package com.example.drapp;

public class UserHelperClass {
    String name, email, phno, spec, lon, lat, add;

    public UserHelperClass(String name, String email, String phno, String spec, String lon, String lat, String add) {
        this.name = name;
        this.email = email;
        this.phno = phno;
        this.spec = spec;
        this.lon = lon;
        this.lat = lat;
        this.add = add;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public UserHelperClass( ){

    }

    public UserHelperClass(String name, String email, String phno) {
        this.name = name;
        this.email = email;
        this.phno = phno;
    }

    public UserHelperClass(String name, String email, String phno, String spec) {
        this.name = name;
        this.email = email;
        this.phno = phno;
        this.spec = spec;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
}
