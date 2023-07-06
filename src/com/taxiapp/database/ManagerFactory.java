package com.taxiapp.database;


import com.taxiapp.coordinators.AdminService;
import com.taxiapp.coordinators.CustomerService;
import com.taxiapp.coordinators.DriverService;
import com.taxiapp.coordinators.PaymentService;


public final class ManagerFactory {
    public static DatabaseManager getDatabaseManager(){
        return new DatabaseManager(Database.getInstance());
    }
    public static AdminService getAdminService(){return new AdminService(getDatabaseManager());}
    public static DriverService getDriverService(){
        return new DriverService(getDatabaseManager());
    }
    public static CustomerService getCustomerService(){
        return new CustomerService(getDatabaseManager());
    }
    public static PaymentService getPaymentService(){
        return new PaymentService(getDatabaseManager());
    }

}
