package com.taxiapp.coordinators;

import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.database.PathFinder;
import com.taxiapp.database.interfaces.BookingManager;
import com.taxiapp.library.Driver;
import com.taxiapp.library.Feedback;
import com.taxiapp.library.History;
import com.taxiapp.library.Taxi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class RideManager  {
    private final BookingManager databaseManager;
    RideManager(BookingManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    private String  id ;
    private Driver driver;
    private Taxi bookedTaxi;
    private int earning ;
    private String nextFreeTime ;
    private int newCurrentSpot ;
    private int source ;
    private int destination;
    private String pickUpTime;
    private TaxiTypes taxiPref;
    int min = Integer.MAX_VALUE;
    private int fare;
    public Driver bookTaxi(String customerId, int pickupPoint, int dropPoint, String pickupTime, TaxiTypes taxiType){

        int distanceBetweenPickUpAndDrop ;
        String[] time = pickupTime.split("[.]");
        int minutes = Integer.parseInt(time[1]);
        source=pickupPoint;
        destination=dropPoint;
        pickUpTime=pickupTime;
        id=customerId;
        taxiPref=taxiType;

        List<Taxi> freeTaxis = getFreeTaxis(databaseManager.getTaxis(),Double.parseDouble(pickupTime));
        if(freeTaxis.isEmpty())
        {
            return null;
        }

        for(Taxi taxi : freeTaxis) {
            int distanceBetweenCustomerAndDriver = PathFinder.shortestPathBetween(taxi.getCurrentSpot(),pickupPoint);
            if(distanceBetweenCustomerAndDriver<min){
                bookedTaxi = taxi;
                distanceBetweenPickUpAndDrop = PathFinder.shortestPathBetween( pickupPoint,dropPoint);
                if(pickupPoint == taxi.getCurrentSpot()){
                    earning = distanceBetweenPickUpAndDrop * databaseManager.getTaxiPrices().get(taxiPref);
                    fare = earning;
                }
                else {
                    int distanceBetweenPickupAndCurrent = distanceBetweenCustomerAndDriver * 5;
                    earning = ((distanceBetweenPickUpAndDrop) * databaseManager.getTaxiPrices().get(taxiPref)) - distanceBetweenPickupAndCurrent;
                    fare =((distanceBetweenPickUpAndDrop) * databaseManager.getTaxiPrices().get(taxiPref));
                }
                int dropTime = minutes + (distanceBetweenPickUpAndDrop*5);
                nextFreeTime = calculateTime(pickupTime,dropTime);
                newCurrentSpot = dropPoint;
                min = distanceBetweenCustomerAndDriver;
            }

        }
        driver=databaseManager.getDriverDB().get(bookedTaxi.getTaxiID());
        return driver;
    }
    public Driver rentTaxi(String customerId, int pickUpPoint, String startTime, String hoursToRent, TaxiTypes taxiType){
        id=customerId;
        taxiPref=taxiType;
        source=pickUpPoint;
        destination=pickUpPoint;
        pickUpTime=startTime;
        List<Taxi> freeTaxis = getFreeTaxis(databaseManager.getTaxis(),Double.parseDouble(startTime));
        if(freeTaxis.isEmpty())
        {
            return null;
        }
        for(Taxi taxi : freeTaxis){
            int distanceBetweenCustomerAndDriver = PathFinder.shortestPathBetween(taxi.getCurrentSpot(),pickUpPoint);
            if(distanceBetweenCustomerAndDriver<min){
                bookedTaxi = taxi;
                earning = Integer.parseInt(hoursToRent) * databaseManager.getTaxiPrices().get(taxiPref) * 10;
                nextFreeTime = calculateTime(startTime,Integer.parseInt(hoursToRent)*60);
                newCurrentSpot = pickUpPoint;
                min = distanceBetweenCustomerAndDriver;
            }
        }
        driver=databaseManager.getDriverDB().get(bookedTaxi.getTaxiID());
        return driver;
    }

    private List<Taxi> getFreeTaxis(List<Taxi> taxis, double pickupTime)
    {
        List<Taxi> freeTaxis = new ArrayList<>();
        for(Taxi taxi : taxis)
        {
            if(Double.parseDouble(taxi.getFreeTime()) <= pickupTime && !taxi.isBooked() && taxi.getTaxiType().equals(taxiPref))
                freeTaxis.add(taxi);
        }
        freeTaxis.sort(Comparator.comparingInt(Taxi::getTotalEarnings));
        return freeTaxis;
    }
    public void setTripDetails(String feedback,double rating){
        driver.setRating((driver.getRating()+rating)/2);
        databaseManager.addToTravelLog(id, driver, new History(databaseManager.viewRoute().get(source).getPointName(),
                databaseManager.viewRoute().get(destination).getPointName(),pickUpTime,nextFreeTime,fare,id,
                driver.getMobileNumber(),new Feedback(feedback,rating)));
    }
    public int getFare(){
        return fare;
    }
    public String setTaxiDetails(){
        setDetails(bookedTaxi,newCurrentSpot,nextFreeTime,bookedTaxi.getTotalEarnings() + earning);
        return "Ride Confirmed";
    }
    private void setDetails(Taxi taxi,int currentSpot, String freeTime, int totalEarnings) {
        taxi.setCurrentSpot(currentSpot);
        taxi.setFreeTime(freeTime);
    }

    private String calculateTime(String pickUpTime, int dropTime){
        String[] time = pickUpTime.split("\\.");
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        hours += (dropTime / 60) % 24;
        minutes = (minutes + dropTime % 60) % 60;

        StringBuilder result = new StringBuilder();
        result.append(hours < 10 ? "0" + hours : hours).append(".").append(minutes < 10 ? "0" + minutes : minutes);

        return result.toString();

    }
}
