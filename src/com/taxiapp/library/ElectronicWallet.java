package com.taxiapp.library;

public class ElectronicWallet {
    private final String userId;
    private final String password;
    private int balance;

    public ElectronicWallet(String userId, String password, int balance) {
        this.userId = userId;
        this.password = password;
        this.balance = balance;
    }


    public int getBalance() {
        return balance;
    }

    public void addAmount(int amount){
        this.balance += amount;
    }
    public void reduceAmount(int amount){
        this.balance -= amount;
    }

    public String getPassword() {
        return password;
    }


}
