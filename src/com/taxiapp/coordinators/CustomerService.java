package com.taxiapp.coordinators;


import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.database.ManagerFactory;
import com.taxiapp.database.interfaces.CustomerDBManager;
import com.taxiapp.route.Point;
import com.taxiapp.library.Customer;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;

import java.util.ArrayList;
import java.util.List;


public class CustomerService {


    private final RideManager booking = new RideManager(ManagerFactory.getDatabaseManager());
    private final CustomerDBManager databaseManager;

    public CustomerService(CustomerDBManager databaseManager) {
        this.databaseManager=databaseManager;

    }

    public String signUp(String name, String mobileNumber,String password, String email) {
        return databaseManager.signUP(new Customer(name, mobileNumber,password,email));
    }

    public ArrayList<Point> viewRoute() {
        return databaseManager.viewRoute();
    }

    public Driver bookTaxi(String customerId, int pickUpPoint, int dropPoint, String pickupTime, TaxiTypes taxiType) {
        return booking.bookTaxi(customerId,pickUpPoint,dropPoint,pickupTime,taxiType);
    }
    public void giveFeedback(String feedback, double rating) {
        booking.setTripDetails(feedback,rating);
    }


    public String confirmRide(){
        return booking.setTaxiDetails();
    }

    public List<History> viewTrips(String id) {
        return databaseManager.viewCustomerTripDetails(id);
    }

    public Driver rentTaxi(String id,int pickUpPoint, String pickUpTime, String hoursToRent, TaxiTypes taxiType) {
        return booking.rentTaxi(id,pickUpPoint,pickUpTime,hoursToRent,taxiType);
    }

    public int getFare() {
        return booking.getFare();
    }

}
