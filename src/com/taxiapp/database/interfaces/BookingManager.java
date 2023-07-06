package com.taxiapp.database.interfaces;

import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.route.Point;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;
import com.taxiapp.library.Taxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BookingManager {
    ArrayList<Point> viewRoute();
    List<Taxi> getTaxis();
    Map<String, Driver> getDriverDB();
    void addToTravelLog(String customerId, Driver driver, History tripDetails);
    HashMap<TaxiTypes, Integer> getTaxiPrices();
}
