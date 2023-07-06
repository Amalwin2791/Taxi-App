package com.taxiapp.coordinators;


import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.database.interfaces.AdminDBManager;
import com.taxiapp.route.Point;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AdminService {
    private final AdminDBManager databaseManager;

    public AdminService(AdminDBManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    public String addPoints(String point,int index, int distance) {
        return databaseManager.addRoute(point,index,distance);
    }
    public String addPath(int start,int end, Integer distance) {
        return databaseManager.addPath(start, end ,distance);
    }
    public List<History> viewTripDetails(String driverID) {
        return databaseManager.getTravelLogs(driverID);
    }
    public String approveDrivers(String id) {
         return databaseManager.approveDriver(id);
    }

    public HashMap<String, Driver> viewDrivers() {
        return databaseManager.getDrivers();
    }

    public String removeDrivers(String driverID) {
        return databaseManager.removeDriver(driverID);
    }
    public HashMap<String, Driver> viewWaitingList() {
        return databaseManager.viewWaitingList();
    }
    public ArrayList<Point> viewRoute(){
        return databaseManager.viewRoute();
    }

    public String addNewAdmin(String userName, String mobileNumber, String password) {
        return databaseManager.addNewAdmin(userName,mobileNumber,password);
    }

    public HashMap<TaxiTypes, Integer> viewTaxiPrices() {
        return databaseManager.getTaxiPrices();
    }


}
