package com.taxiapp.library;

public class Customer extends User {

    private final String email;


    public Customer(String name, String mobileNumber,String password, String email) {
        super(name,mobileNumber,password);
        this.email = email;
    }

    public String getName() {
        return super.getUserName();
    }

    public String getMobileNumber() {
        return super.getMobileNumber();
    }

    public String getPassword() {
        return super.getPassword();
    }
    public String getEmail() {
        return email;
    }
}
