package com.taxiapp.database.interfaces;

import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.route.Point;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface AdminDBManager {
    String addRoute(String pointName,int index, int distance);
    String addPath(int start, int end, Integer distance);
    String removePath(int start,int end);
    HashMap<String,Driver> viewWaitingList();
    String approveDriver(String id);
    HashMap<String, Driver> getDrivers();
    String removeDriver(String driverID);
    List<History> getTravelLogs(String id);
    ArrayList<Point> viewRoute();
    String addNewAdmin(String userName, String mobileNumber, String password);
    HashMap<TaxiTypes, Integer> getTaxiPrices();
}
