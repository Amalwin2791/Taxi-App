package com.taxiapp.database;

import com.taxiapp.application.*;
import com.taxiapp.application.enums.UserRoles;
import com.taxiapp.database.interfaces.AuthenticationManager;
import com.taxiapp.route.Point;


public final class Authenticator {
    private final static AuthenticationManager databaseManager = ManagerFactory.getDatabaseManager();

    private Authenticator() {}

    public static String authenticate(UserRoles userRole, String userName, String password) {
        if (userRole.equals(UserRoles.ADMIN)) {
            if (databaseManager.viewAdminDB().containsKey(userName)) {
                if (databaseManager.viewAdminDB().get(userName).getPassword().equals(password)) {
                    Menu adminMenu = new AdminMenu(databaseManager.viewAdminDB().get(userName), ManagerFactory.getAdminService());
                    adminMenu.start();

                }
                else if(!databaseManager.viewAdminDB().get(userName).getPassword().equals(password)){
                    return "Enter Correct Password";
                }
            }
            else if(!databaseManager.viewAdminDB().containsKey(userName)){
                return "Enter Valid username";
            }

        }
        else if (userRole.equals(UserRoles.CUSTOMER)) {
            if (databaseManager.viewCustomerDB().containsKey(userName) ) {
                if (databaseManager.viewCustomerDB().get(userName).getPassword().equals(password)) {
                    Menu customerMenu = new CustomerMenu(databaseManager.viewCustomerDB().get(userName), ManagerFactory.getCustomerService(), ManagerFactory.getPaymentService());
                    customerMenu.start();
                }
                else if(!databaseManager.viewCustomerDB().get(userName).getPassword().equals(password)){
                    return "Enter Correct Password";
                }
            }
            else if(!databaseManager.viewCustomerDB().containsKey(userName)){
                return "Enter Valid username or SignUp";
            }

        } else {
            if (databaseManager.viewDriverDB().containsKey(userName)) {
                if (databaseManager.viewDriverDB().get(userName).getPassword().equals(password)) {
                    Menu driverMenu = new DriverMenu(databaseManager.viewDriverDB().get(userName), ManagerFactory.getDriverService());
                    driverMenu.start();
                }
                else if(!databaseManager.viewDriverDB().get(userName).getPassword().equals(password)){
                    return "Enter Correct Password";
                }
            }
            else if(!databaseManager.viewDriverDB().containsKey(userName)){
                return "Enter Valid username or SignUp";
            }
        }
        return "";
    }

    public static boolean checkIfCustomerPresent(String id) {
        return databaseManager.viewCustomerDB().containsKey(id);

    }

    public static boolean checkIfDriverPresent(String id) {
        return databaseManager.viewDriverDB().containsKey(id) || databaseManager.viewWaitingList().containsKey(id);

    }

    public static boolean checkIfAdminPresent(String id) {
        return databaseManager.viewAdminDB().containsKey(id);
    }

    public static int makePayment(String id, String pin, int fare) {
        if (!databaseManager.getAllWallets().containsKey(id)) {
            return 0;

        } else {
            if (databaseManager.getAllWallets().get(id).getPassword().equals(pin)) {
                Menu paymentMenu = new PaymentMenu(databaseManager.getAllWallets().get(id), ManagerFactory.getPaymentService(),fare);
                paymentMenu.start();
            }else if(!databaseManager.getAllWallets().get(id).getPassword().equals(pin)){
                return -1;
            }
        }
        return 1;
    }
    public static int addMoney(String id, String pin, int amount){
        if(!databaseManager.getAllWallets().containsKey(id)){
            return 0;
        }
        else {
            if (databaseManager.getAllWallets().get(id).getPassword().equals(pin)){
                PaymentMenu paymentMenu = new PaymentMenu(databaseManager.getAllWallets().get(id), ManagerFactory.getPaymentService(),amount);
                paymentMenu.addAmount();
            } else if (!databaseManager.getAllWallets().get(id).getPassword().equals(pin)) {
                return -1;
            }
        }
        return 1;
    }
    public static boolean checkIfPointAlreadyPresent(String pointName){
        for(Point name : databaseManager.viewRoute()){
            if (name.getPointName().equalsIgnoreCase(pointName)){
                return true;
            }
        }
        return false;
    }
}
