package com.taxiapp.database.interfaces;

import com.taxiapp.library.Admin;
import com.taxiapp.library.Customer;
import com.taxiapp.library.Driver;
import com.taxiapp.library.ElectronicWallet;
import com.taxiapp.route.Point;

import java.util.ArrayList;
import java.util.Map;

public interface AuthenticationManager {
    Map<String, Admin> viewAdminDB();
    Map<String, Customer> viewCustomerDB();
    Map<String, Driver> viewDriverDB();
    Map<String, Driver> viewWaitingList();
    Map<String , ElectronicWallet> getAllWallets();
    ArrayList<Point> viewRoute();
}
