package com.taxiapp.database.interfaces;

import com.taxiapp.route.Point;
import com.taxiapp.library.Customer;
import com.taxiapp.library.History;

import java.util.ArrayList;
import java.util.List;

public interface CustomerDBManager {
    List<History> viewCustomerTripDetails(String id);
    String signUP(Customer newCustomer);
    ArrayList<Point> viewRoute();
}
