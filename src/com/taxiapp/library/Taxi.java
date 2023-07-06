package com.taxiapp.library;

import com.taxiapp.application.enums.TaxiTypes;

public class Taxi {

    boolean booked ;
    private int currentSpot= 0;
    private String freeTime= "00.00";
    private int totalEarnings=0;
    private final String taxiID;
    private final TaxiTypes taxiType;

    public Taxi(String taxiID, TaxiTypes taxiType) {
        this.taxiID=taxiID;
        this.taxiType=taxiType;
    }

    public String getTaxiID() {
        return taxiID;
    }

    public int getCurrentSpot() {
        return currentSpot;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public TaxiTypes getTaxiType() {
        return taxiType;
    }

    public void setCurrentSpot(int currentSpot) {
        this.currentSpot = currentSpot;
    }

    public void setFreeTime(String freeTime) {
        this.freeTime = freeTime;
    }
    public String getFreeTime() {
        return freeTime;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public boolean isBooked() {
        return booked;
    }

}
