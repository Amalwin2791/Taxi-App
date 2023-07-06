package com.taxiapp.library;


public class Admin extends User {

    public Admin(String userName,String mobileNumber, String password) {
        super(userName,mobileNumber,password);
    }

    public String getUserName() {
        return super.getUserName();
    }

    public String getPassword() {
        return super.getPassword();
    }
}
