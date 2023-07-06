package com.taxiapp.coordinators;


import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.database.interfaces.DriverDBManager;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;
import com.taxiapp.library.Taxi;

import java.util.List;

public class DriverService {
    private final DriverDBManager databaseManager;

    public DriverService(DriverDBManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public String signUp(String name, String password, String licenseId, String mobileNumber, TaxiTypes type) {
        return databaseManager.signUP(new Driver(name, password,  licenseId,  mobileNumber, new Taxi(mobileNumber,type)));
    }

    public List<History> viewTrips(String id) {
        return databaseManager.viewDriverTripDetails(id);

    }
}
