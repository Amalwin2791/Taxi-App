package com.taxiapp.library;

public class Feedback {
    private String feedback;
    private double rating;


    public Feedback(String feedback, double rating) {
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getCustomerFeedback() {
        return feedback;
    }
    public double getRating() {
        return rating;
    }

}
