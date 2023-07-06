package com.taxiapp.database;

import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.route.Point;
import com.taxiapp.library.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Database {
    private static Database instance = null;
    private final List<Taxi> taxis=  new ArrayList<>(); ;
    private final Map<String,Driver> driverDetails=new HashMap<>();
    private final Map<String,Driver> waitingList= new HashMap<>();
    private final Map<String , Customer> customers= new HashMap<>();
    private final Map<String, ArrayList<History>> customerTravelLog= new HashMap<>();
    private final Map<String, ArrayList<History>> driverTravelLog= new HashMap<>();
    private final Map<String, Admin> admins = new HashMap<>() ;
    private final Route route= new Route();
    private final Map<String, ElectronicWallet> wallets= new HashMap<>();
    private final HashMap<TaxiTypes, Integer> taxiPrices= new HashMap<>();
    private Database() {
    }
    static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    static {
        new Test();
    }

    List<Taxi> getTaxis() {
        return taxis;
    }
    Map<String,Admin> getAdmins(){return admins;}
    Route getRoute(){
        return route;
    }
    ArrayList<Point> getPoints(){
        return new ArrayList<>(route.getPoints());
    }
    Map<String, Driver> getWaitingList() {
           return waitingList;
       }
    Map<String , Customer> getCustomers(){
        return customers;
    }
    Map<String,Driver> getDriverDetails(){
        return driverDetails ;
    }

    HashMap<TaxiTypes, Integer> getTaxiPrices() {
           return taxiPrices;
    }
    String addRoute(String pointName, int position, int distance) {
           Point source = new Point(pointName);
           this.route.getPoints().add(source);
           Point destination = route.getPoints().get(position-1);
           source.addPath(destination, distance);
           destination.addPath(source, distance);
           return "Successfully Added!";
    }
    String addPath(int start, int end, Integer distance) {
        Point source = route.getPoints().get(start-1);
        Point destination = route.getPoints().get(end-1);
        source.addPath(destination, distance);
        destination.addPath(source, distance);
        return "Successfully Added!";
    }
    String removePath(int start, int end){
        Point source= route.getPoints().get(start-1);
        Point destination = route.getPoints().get(end-1);
        source.removePath(destination);
        destination.removePath(source);
        return "Successfully Removed!";
    }
    String removePoint(int index){
        Point point =  route.getPoints().get(index);
        this.route.getPoints().remove(point);
        return "Successfully Removed!";
    }

    Map<String, ElectronicWallet> getWallets() {
           return wallets;
       }
    Map<String, ArrayList<History>> getCustomerTravelLog() {
           return customerTravelLog;
       }

    Map<String, ArrayList<History>> getDriverTravelLog() {
           return driverTravelLog;
    }


}
