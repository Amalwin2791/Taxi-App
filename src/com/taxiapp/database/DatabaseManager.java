package com.taxiapp.database;

import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.database.interfaces.*;
import com.taxiapp.route.Point;
import com.taxiapp.library.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class DatabaseManager implements AdminDBManager, CustomerDBManager, DriverDBManager, BookingManager, AuthenticationManager,PaymentManager{
    private final Database database ;
    DatabaseManager(Database database) {
        this.database = database;
    }
    @Override
    public String addRoute( String pointName,int index, int distance) {
        return database.addRoute( pointName,  index,  distance);
    }

    @Override
    public String addPath(int start, int end, Integer distance) {
        return database.addPath(start,end,distance);
    }

    public String removePoint(int index) {
        return  database.removePoint(index);
    }

    @Override
    public String removePath(int start, int end) {
         return database.removePath(start,end);
     }

     @Override
    public HashMap<String,Driver> viewWaitingList() {
        return new HashMap<>(database.getWaitingList());
    }
    @Override
    public String approveDriver(String id) {
        database.getWaitingList().get(id).setStatus(true);
        database.getDriverDetails().put(id,database.getWaitingList().get(id));
        database.getTaxis().add(database.getWaitingList().get(id).getTaxi());
        database.getDriverTravelLog().put(id,new ArrayList<>());
        database.getWaitingList().remove(id);
        return "Approved!";
    }

    @Override
    public  HashMap<String, Driver> getDrivers() {
        return new  HashMap<>(database.getDriverDetails());
    }

    @Override
    public String removeDriver(String driverID) {
        database.getDriverDetails().remove(driverID);
        return "Removed";
    }

    @Override
    public List<History> getTravelLogs(String id) {
        return new ArrayList<>(database.getDriverTravelLog().get(id));
    }

    @Override
    public ArrayList<Point> viewRoute() {
        return database.getPoints();
    }

    @Override
    public List<Taxi> getTaxis() {
        return new ArrayList<>(database.getTaxis());
    }

    @Override
    public Map<String, Driver> getDriverDB() {
        return new HashMap<>(database.getDriverDetails());
    }

    @Override
    public void addToTravelLog(String customerId, Driver driver, History tripDetails) {
        database.getCustomerTravelLog().get(customerId).add(tripDetails);
        database.getDriverTravelLog().get(driver.getMobileNumber()).add(tripDetails);
    }

    @Override
    public Map<String, Admin> viewAdminDB() {
        return new HashMap<>(database.getAdmins());
    }

    @Override
    public Map<String, Customer>   viewCustomerDB() {
        return new HashMap<>(database.getCustomers());
    }

    @Override
    public Map<String, Driver> viewDriverDB() {
        return new HashMap<>(database.getDriverDetails());
    }

    @Override
    public List<History> viewCustomerTripDetails(String id) {
        return new ArrayList<>(database.getCustomerTravelLog().get(id));
    }

     @Override
     public List<History> viewDriverTripDetails(String id) {
         return new ArrayList<>(database.getDriverTravelLog().get(id));
     }

     @Override
    public String signUP(Driver newDriver) {
        database.getWaitingList().put(newDriver.getMobileNumber(),newDriver);
        database.getCustomers().put(newDriver.getMobileNumber(),new Customer(newDriver.getName(),newDriver.getMobileNumber(),newDriver.getPassword(),null));
        database.getCustomerTravelLog().put(newDriver.getMobileNumber(),new ArrayList<>());
        return "Successfully added!";
    }

    @Override
    public String signUP(Customer newCustomer) {
        database.getCustomers().put(newCustomer.getMobileNumber(),newCustomer);
        database.getCustomerTravelLog().put(newCustomer.getMobileNumber(),new ArrayList<>());
        return "Welcome";
    }
    public String addNewAdmin(String userName, String mobileNumber, String password){
        database.getAdmins().put(mobileNumber,new Admin(userName,mobileNumber,password));
        database.getCustomers().put(mobileNumber,new Customer(userName,mobileNumber,password,null));
        database.getCustomerTravelLog().put(mobileNumber,new ArrayList<>());
        return "New Admin Added";
    }

     @Override
     public HashMap<TaxiTypes, Integer> getTaxiPrices() {
         return database.getTaxiPrices();
     }


     @Override
     public Map<String, ElectronicWallet> getAllWallets() {
         return database.getWallets();
     }

     @Override
     public boolean payAmount(ElectronicWallet wallet, int fare) {
         if (wallet.getBalance()>= fare){
             wallet.reduceAmount(fare);
             return true;
         }
         else
             return false;
     }

     @Override
     public String addAmount(ElectronicWallet wallet, int amount) {
         wallet.addAmount(amount);
         return "Money credited";
     }

}
