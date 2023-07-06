package com.taxiapp.application;

import com.taxiapp.application.enums.MainMenuOptions;
import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.application.enums.UserRoles;
import com.taxiapp.coordinators.CustomerService;
import com.taxiapp.coordinators.DriverService;
import com.taxiapp.database.Authenticator;
import com.taxiapp.utilities.InputReader;
import com.taxiapp.utilities.Validator;

public class MainMenu implements Menu {
    private  final DriverService driverService;
    private  final CustomerService customerService;

    public MainMenu(DriverService driverService, CustomerService customerService) {
        this.driverService = driverService;
        this.customerService = customerService;
    }

    public void start() {
        while (true){
            System.out.println("How would you like to login/Sign Up as: ");
            for(MainMenuOptions options : MainMenuOptions.values()){
                System.out.println(options.ordinal() + 1 + ". "+ options.name());
            }
            int choice = Validator.getNumberInRange(MainMenuOptions.values().length);
            MainMenuOptions option = MainMenuOptions.values()[choice - 1];
            switch (option){
                case ADMIN -> login(UserRoles.ADMIN);
                case CUSTOMER -> login(UserRoles.CUSTOMER);
                case DRIVER -> login(UserRoles.DRIVER);
                case SIGNUP_AS_CUSTOMER -> customerSignUP();
                case SIGNUP_AS_DRIVER -> driverSignUp();
                case QUIT -> {return;}
            }
        }

    }
      void login(UserRoles userRole){
        String userName = Validator.validatePhoneNumber(InputReader.scanStringWithMsg("Enter Your Mobile Number: "));
        String password = InputReader.scanStringWithMsg("Enter your password (8-20 char, at least 1 uppercase,1 number,1 special char,1 lower case)");
        Validator.checkPasswordIsValid(password);
        System.out.println(Authenticator.authenticate(userRole,userName,password));

    }
     void customerSignUP(){
        String name = Validator.nameIsValid(InputReader.scanStringWithMsg("Enter your Name: "));
        String mobileNumber;
         while (Authenticator.checkIfCustomerPresent(mobileNumber = Validator.validatePhoneNumber(InputReader.scanStringWithMsg("Enter your Mobile Number: ")))){
             System.out.println("This number is already present.");
        }
        String emailId= Validator.checkEmailIdIsValid(InputReader.scanStringWithMsg("Enter email ID: "));
        String password = Validator.checkPasswordIsValid(InputReader.scanStringWithMsg("Enter your password (8-20 char, at least 1 uppercase,1 number,1 special char,1 lower case)"));
        System.out.println(customerService.signUp(name,mobileNumber,password,emailId));
    }
     void driverSignUp(){
        String name = Validator.nameIsValid(InputReader.scanNextLine("Enter your Name: "));
         String mobileNumber;
         while (Authenticator.checkIfDriverPresent(mobileNumber = Validator.validatePhoneNumber(InputReader.scanStringWithMsg("Enter your Mobile Number: ")))){
             System.out.println("This number is already present.");
         }
         String licenseId = Validator.licenseIsValid(InputReader.scanStringWithMsg("Enter your license Id: (example: HR-06198500xxxxx)"));
        for(TaxiTypes types : TaxiTypes.values()){
            System.out.println(types.ordinal() + 1 + ". "+ types.name());
        }
        System.out.println("Enter Your type of taxi: ");
        int choice = Validator.getNumberInRange(TaxiTypes.values().length);
        TaxiTypes taxiType = TaxiTypes.values()[choice - 1];

        String password = Validator.checkPasswordIsValid(InputReader.scanStringWithMsg("Enter your password (8-20 char, at least 1 uppercase,1 number,1 special char,1 lower case)"));
        System.out.println(driverService.signUp(name,password,licenseId,mobileNumber,taxiType));
    }
}
