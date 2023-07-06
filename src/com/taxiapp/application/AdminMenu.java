package com.taxiapp.application;

import com.taxiapp.application.enums.AdminMenuOptions;
import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.coordinators.AdminService;
import com.taxiapp.database.Authenticator;
import com.taxiapp.route.Point;
import com.taxiapp.library.Admin;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;
import com.taxiapp.utilities.InputReader;
import com.taxiapp.utilities.Validator;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;


public class AdminMenu implements Menu {
    private  final Admin admin ;
    private final AdminService adminService;
    public AdminMenu(Admin admin, AdminService adminService) {
        this.admin = admin;
        this.adminService=adminService;
    }

    public void start(){
        System.out.println();
        System.out.println("Welcome "+ admin.getUserName());
        while (true){
            for(AdminMenuOptions options : AdminMenuOptions.values()){
                System.out.println(options.ordinal() + 1 +". "+ options.name());
            }
            System.out.println("Enter your Choice: ");
            int choice = Validator.getNumberInRange(AdminMenuOptions.values().length);
            AdminMenuOptions option = AdminMenuOptions.values()[choice-1];
            switch (option){
                case VIEW_ROUTE -> displayRoute();
                case ADD_POINTS -> addPoints();
                case ADD_PATH -> addPath();
                case APPROVE_DRIVERS -> approveDriver();
                case REMOVE_DRIVERS -> removeDriver();
                case VIEW_DRIVERS -> viewDrivers();
                case VIEW_TRIPS -> viewTrips();
                case VIEW_DRIVER_POSITION -> viewDriverPosition();
                case ADD_ADMINS -> addAdmins();
                case SET_TAXI_PRICE -> setTaxiPrice();
                case QUIT -> {return;}
            }
        }
    }

        private void addPath(){
            displayRoute();
            System.out.println("Enter the index of start of path: ");
            int start = Validator.getNumberInRange(adminService.viewRoute().size());
            System.out.println("Enter the index of end of path: ");
            int end = Validator.getValidLocation(adminService.viewRoute().size(),start);
            System.out.println("Enter the distance between start and the end: (max distance of 100km)");
            Integer distance = Validator.getNumberInRange(100);
            System.out.println(adminService.addPath(start,end,distance));
        }


     private void approveDriver(){
        if(!adminService.viewWaitingList().isEmpty()){
            displayDriver(adminService.viewWaitingList().values());
            String id = Validator.validateId(InputReader.scanStringWithMsg("Enter Driver ID: "));
            if(adminService.viewWaitingList().containsKey(id)){
                System.out.println(adminService.approveDrivers(id));
            } else if (!adminService.viewWaitingList().containsKey(id)) {
                System.out.println("Enter Valid Driver ID");
            }

        }
        else
            System.out.println("Waiting list is Empty");
    }
    private void removeDriver(){
        if(!adminService.viewDrivers().isEmpty()){
            displayDriver(adminService.viewDrivers().values());
            String choice = Validator.validateId(InputReader.scanNextLine("Enter Driver ID: "));
            if(adminService.viewDrivers().containsKey(choice)){
                System.out.println(adminService.removeDrivers(choice));
            } else if (!adminService.viewDrivers().containsKey(choice)) {
                System.out.println("Enter a valid Driver ID");
            }
        }
        else
            System.out.println("No Drivers Available");
    }
    private void addPoints(){
        displayRoute();
        String point ;
        while(Authenticator.checkIfPointAlreadyPresent(point =Validator.pointIsValid(InputReader.scanNextLine("Enter the name of the Point: ")))){
            System.out.println("Point already exists.");
        }
        System.out.println("Enter the index to which the new point should be connected: ");
        int index = Validator.getNumberInRange(adminService.viewRoute().size());
        System.out.println("Enter the distance between the 2 points: (max distance of 100km )");
        int distance = Validator.getNumberInRange(100);
        System.out.println(adminService.addPoints(point,index,distance));
    }
    private void viewDrivers(){
        if (!adminService.viewDrivers().values().isEmpty()){
            displayDriver(adminService.viewDrivers().values());
        }
        else
            System.out.println("No Drivers available");

    }
    private void viewDriverPosition(){
        int i=0;
        System.out.format("   %-12s  %-10s","Driver Name","Position\n");
        if(!adminService.viewDrivers().isEmpty()){
            for(Driver driver : adminService.viewDrivers().values()){
                System.out.format(" %d %-15s\t%-15s \n",++i,driver.getName(),adminService.viewRoute().get(driver.getTaxi().getCurrentSpot()).getPointName());

            }
        } else if (adminService.viewDrivers().isEmpty()) {
            System.out.println("No Drivers Available");
        }
        System.out.println();
    }
    private void viewTrips(){
        int i=0;
        if(!adminService.viewDrivers().isEmpty()){
            displayDriver(adminService.viewDrivers().values());
            String choice = Validator.validateId(InputReader.scanNextLine("Enter Driver ID: "));
            if(adminService.viewDrivers().containsKey(choice)){
                if(!adminService.viewTripDetails(choice).isEmpty()){
                    System.out.format("  %13s %11s %13s %11s %12s %8s","PickUp Point","Drop Point","PickUp Time","Drop Time","Customer ID","Earnings\n");
                    for(History trip : adminService.viewTripDetails(choice)){
                        System.out.format("%d.  %16s %16s %6s %6s %12s %6d\n",++i, trip.getPickUpPoint(),trip.getDropPoint(),trip.getPickUpTime(),trip.getDropTime(),trip.getCustomerID(),trip.getEarnings());

                    }
                }
                else
                    System.out.println("No trips yet");
            }
            else
                System.out.println("Enter a Valid Driver ID ");

        }


    }
    private void displayDriver(Collection<Driver> drivers){
        int i=0;
        System.out.format("  \t%-16s%-10s\t   %-21s %-16s %-8s","Driver ID","Driver Name","license Number","Mobile Number","Taxi Type \n");
        for(Driver driver : drivers){
            System.out.format("%2d. %-17s %-10s %20s \t\t%12s   %8s\n",++i,driver.getMobileNumber(),driver.getName(),driver.getLicenseId(),driver.getMobileNumber(),driver.getTaxi().getTaxiType());
        }
    }
    private void displayRoute(){
        System.out.println();
        System.out.println("\tPoints: ");
        int i=1;
        for(Point point : adminService.viewRoute()){
            System.out.println(i+ ". "+point.getPointName());
            ++i;
        }
        System.out.println();
    }
    private void setTaxiPrice(){
        for(TaxiTypes types : TaxiTypes.values()){
            System.out.println(types.ordinal() + 1 + ". "+ types.name());
        }
        System.out.println("Enter the taxi type: ");
        int choice = Validator.getNumberInRange(TaxiTypes.values().length);
        TaxiTypes taxiType = TaxiTypes.values()[choice - 1];
        System.out.println("Enter the new price per km:(max of 50 must be whole number)");
        int newPrice = Validator.getNumberInRange(50);
        adminService.viewTaxiPrices().put(taxiType,newPrice);
    }
    private void addAdmins(){
        String userName = Validator.nameIsValid(InputReader.scanNextLine("Enter Admin Name: "));
        String mobileNumber;
        while (Authenticator.checkIfAdminPresent(mobileNumber = Validator.validatePhoneNumber(InputReader.scanStringWithMsg("Enter your Mobile Number: ")))){
            System.out.println("This number is already present.");
        }
        String password = passwordGenerator();
        System.out.println("password: " + password);
        adminService.addNewAdmin(userName,mobileNumber,password);
    }

    private String passwordGenerator(){
        final String characters = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "!@#$%^&*()_+-=[]{}|;':\"<>,.?/";
        StringBuilder password = new StringBuilder();
        final Random random = new SecureRandom();
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }




}
