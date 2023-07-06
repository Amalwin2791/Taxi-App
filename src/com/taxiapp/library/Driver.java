package com.taxiapp.library;

public class Driver extends User {

    private boolean status= false;
    private final String licenseId;
    private double rating;
    private final Taxi taxi;

    public Driver(String name, String password, String licenseId,  String mobileNumber, Taxi taxi) {
        super(name,mobileNumber,password);
        this.licenseId = licenseId;
        this.taxi=taxi;
    }
    public String getName() {
        return super.getUserName();
    }

    public String getPassword() {
        return super.getPassword();
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getMobileNumber() {
        return super.getMobileNumber();
    }

    public double getRating() {
        return rating;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
