package com.taxiapp;


import com.taxiapp.application.MainMenu;
import com.taxiapp.application.Menu;
import com.taxiapp.database.ManagerFactory;

public class Main {
    public static void main(String[] args) {
        Menu mainMenu = new MainMenu(ManagerFactory.getDriverService(),ManagerFactory.getCustomerService());
        mainMenu.start();
    }
}


