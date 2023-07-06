package com.taxiapp.library;

public abstract class User {
    private final String userName;
    private final String mobileNumber;
    private final String password;

    public User(String userName, String mobileNumber, String password) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }
}
