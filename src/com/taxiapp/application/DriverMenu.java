package com.taxiapp.application;

import com.taxiapp.application.enums.AdminMenuOptions;
import com.taxiapp.application.enums.DriverMenuOptions;
import com.taxiapp.coordinators.DriverService;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;
import com.taxiapp.utilities.InputReader;
import com.taxiapp.utilities.Validator;

public class DriverMenu  implements Menu {

    private final Driver driver;
    private final DriverService driverService;

    public DriverMenu(Driver driver, DriverService driverService) {
        this.driver = driver;
        this.driverService = driverService;
    }

    public void start(){
        System.out.println();
        System.out.println("Welcome "+ driver.getName());
        while (true){
            for (DriverMenuOptions options : DriverMenuOptions.values()){
                System.out.println(options.ordinal() + 1 +". "+ options.name());
            }
            System.out.println();
            System.out.println("Enter your Choice: ");
            int choice = Validator.getNumberInRange(AdminMenuOptions.values().length);
            DriverMenuOptions option = DriverMenuOptions.values()[choice-1];
            switch (option){
                case VIEW_MY_PROFILE -> viewMyProfile();
                case VIEW_MY_TRIPS -> viewMyTrips();
                case EMERGENCY_STOP -> emergencyStop();
                case QUIT -> {return;}
            }
        }
    }
    private void viewMyProfile(){
        System.out.println("Name: " + driver.getName());
        System.out.println("Mobile Number: "+ driver.getMobileNumber());
        System.out.println("License Number: "+ driver.getLicenseId());
        System.out.format("My Rating: %.2f\n",driver.getRating());
        System.out.println("My Taxi Type: "+ driver.getTaxi().getTaxiType());
    }
    private void emergencyStop(){
        String choice = Validator.checkIfYesOrNo(InputReader.scanStringWithMsg("Enter Y for emergency stop: "));
        if(choice.equalsIgnoreCase("y")){
            driver.getTaxi().setBooked(true);
        }
    }
    private void viewMyTrips(){
        int i=0;
        if(!driverService.viewTrips(driver.getMobileNumber()).isEmpty()){
            for(History trip : driverService.viewTrips(driver.getMobileNumber())){
                System.out.format("  %13s %11s %13s %11s %12s %8s\n","PickUp Point","Drop Point","PickUp Time","Drop Time","Customer ID","Earnings");
                System.out.format("%d.  %16s %16s %6s %6s %12s %6d\n",++i, trip.getPickUpPoint(),trip.getDropPoint(),trip.getPickUpTime(),trip.getDropTime(),trip.getCustomerID(),trip.getEarnings());
                System.out.println("Feedback:");
                System.out.println(trip.getFeedback().getCustomerFeedback());
                System.out.format("Rating: %.2f\n",trip.getFeedback().getRating());

            }
        }
        else
            System.out.println("No trips available") ;

    }
}
