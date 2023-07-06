package com.taxiapp.database;

import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.library.*;
import com.taxiapp.route.Point;

import java.util.ArrayList;

class Test {
    private static final Route route = Database.getInstance().getRoute();
    private static final Database database = Database.getInstance();
     static{
        Point guindy = route.addPoint("Guindy");
        Point thomasMount = route.addPoint("St.Thomas Mount");
        Point pazhavanthangal = route.addPoint("Pazhavanthangal");
        Point meenambakkam = route.addPoint("Meenambakkam");
        Point trisulam = route.addPoint("Trisulam");

        route.addPath(guindy, thomasMount, 10);
        route.addPath(thomasMount, pazhavanthangal, 20);
        route.addPath(pazhavanthangal, meenambakkam, 33);
        route.addPath(pazhavanthangal, trisulam, 9);
        route.addPath(meenambakkam, trisulam, 4);
        route.addPath(trisulam, thomasMount, 5);

        //customers
        database.getCustomers().put("9999999990",new Customer("Bala","9999999990","$Amalwin27",null));
        database.getCustomers().put("9444250553",new Customer("Amalwin","9444250553","$Amalwin27",null));
        database.getCustomers().put("9876543210",new Customer("Kumar","9876543210","$Amalwin27","amal@gmail.com"));//
        database.getCustomers().put("9999999998",new Customer("John","9999999998","$Amalwin27",null));
        database.getCustomers().put("9999999988",new Customer("kiran","9999999988","$Amalwin27",null));

        //waiting list
        database.getWaitingList().put("9999999990",new Driver("Bala","$Amalwin27","MH-09-2008-0003369","9999999990",new Taxi("9999999990", TaxiTypes.MICRO)));

        //drivers
        database.getDriverDetails().put("9999999998",new Driver("John","$Amalwin27","TN-09-2008-0003369","9999999998",new Taxi("9999999998",TaxiTypes.SUV)));
        database.getDriverDetails().put("9999999988",new Driver("kiran","$Amalwin27","TN-09-2008-0002369","9999999988",new Taxi("9999999988",TaxiTypes.SEDAN)));

        //taxis
        database.getTaxis().add(new Taxi("9999999998",TaxiTypes.SUV));
        database.getTaxis().add(new Taxi("9999999988",TaxiTypes.SEDAN));


        //admins
        database.getAdmins().put("9444250553",new Admin("Amalwin","9444250553","$Amalwin27"));//

        //driver travel log
        database.getDriverTravelLog().put("9999999998",new ArrayList<>());
        database.getDriverTravelLog().put("9999999988",new ArrayList<>());

        //customer travel log
        database.getCustomerTravelLog().put("9444250553",new ArrayList<>());
        database.getCustomerTravelLog().put("9876543210",new ArrayList<>());
        database.getCustomerTravelLog().put("9999999990",new ArrayList<>());


        //taxi prices
        database.getTaxiPrices().put(TaxiTypes.SUV,40);
        database.getTaxiPrices().put(TaxiTypes.SEDAN,30);
        database.getTaxiPrices().put(TaxiTypes.MICRO,20);

        //wallets
        database.getWallets().put("9876543210",new ElectronicWallet("9876543210","1234",5000));
        database.getWallets().put("9444250553",new ElectronicWallet("9444250553","1234",0));
    }
}
