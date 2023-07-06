package com.taxiapp.library;




public class History {
    private final String pickUpPoint;
    private final String dropPoint;
    private final String pickUpTime;
    private final String dropTime;
    private final int earnings;
    private final String customerID;
    private final String driverID;
    private  Feedback feedback;


    public History(String pickUpPoint, String dropPoint, String pickUpTime, String dropTime, int earnings, String customerID, String driverID, Feedback feedback) {
        this.pickUpPoint = pickUpPoint;
        this.dropPoint = dropPoint;
        this.pickUpTime = pickUpTime;
        this.dropTime = dropTime;
        this.earnings = earnings;
        this.customerID = customerID;
        this.driverID = driverID;
        this.feedback=feedback;
    }

    public String getPickUpPoint() {
        return pickUpPoint;
    }

    public String getDropPoint() {
        return dropPoint;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public int getEarnings() {
        return earnings;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
