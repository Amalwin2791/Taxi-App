package com.taxiapp.database.interfaces;

import com.taxiapp.library.Driver;
import com.taxiapp.library.History;

import java.util.List;

public interface DriverDBManager {
    List<History> viewDriverTripDetails(String id);
    String signUP(Driver newDriver);

}
