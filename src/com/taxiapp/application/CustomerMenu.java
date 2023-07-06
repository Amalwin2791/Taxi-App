package com.taxiapp.application;


import com.taxiapp.application.enums.Banks;
import com.taxiapp.application.enums.CustomerMenuOptions;
import com.taxiapp.application.enums.PaymentOptions;
import com.taxiapp.application.enums.TaxiTypes;
import com.taxiapp.coordinators.CustomerService;
import com.taxiapp.coordinators.PaymentService;
import com.taxiapp.database.Authenticator;
import com.taxiapp.route.Point;
import com.taxiapp.library.Customer;
import com.taxiapp.library.Driver;
import com.taxiapp.library.History;
import com.taxiapp.utilities.InputReader;
import com.taxiapp.utilities.Validator;

import java.util.Random;

public class CustomerMenu implements Menu {

    private final Customer customer;
    private final CustomerService customerService;
    private final PaymentService paymentService;
    private Driver driver;
    public CustomerMenu(Customer customer, CustomerService customerService, PaymentService paymentService) {
        this.customer = customer;
        this.customerService = customerService;
        this.paymentService = paymentService;
    }
    public void start(){
        System.out.println();
        System.out.println("Welcome "+ customer.getName());
        while (true){
            for(CustomerMenuOptions options : CustomerMenuOptions.values()){
                System.out.println(options.ordinal() + 1 +". "+ options.name());
            }
            System.out.println("Enter your Choice: ");
            int choice = Validator.getNumberInRange(CustomerMenuOptions.values().length);
            CustomerMenuOptions option = CustomerMenuOptions.values()[choice-1];
            switch (option){
                case VIEW_PROFILE -> viewMyProfile();
                case BOOK_A_TAXI -> bookTaxi();
                case RENT_A_TAXI -> rentTaxi();
                case CREATE_WALLET -> newWallet();
                case VIEW_MY_RIDES -> viewMyRides();
                case VIEW_WALLET_BALANCE -> viewBalance();
                case ADD_MONEY_TO_WALLET -> addMoneyToWallet();
                case QUIT -> {return;}
            }
        }
    }
    private void viewMyProfile(){
        System.out.println("Name: "+ customer.getName());
        System.out.println("Mobile Number: "+customer.getMobileNumber());
        System.out.println("Email ID: "+customer.getEmail());
    }
    private void bookTaxi(){
        displayRoute();
        System.out.println("Enter the index of the PickUP point: ");
        int pickUpPoint = Validator.getNumberInRange(customerService.viewRoute().size());
        System.out.println("Enter the index of the Drop point: (other than pickUp point)");
        int dropPoint = Validator.getValidLocation(customerService.viewRoute().size(),pickUpPoint);
        String pickUpTime;
        while(!Validator.timeFormatIsValid(pickUpTime=Validator.checkTimeIsValid(InputReader.scanNextLine("Enter PickUpTime: (in the format 'HH.MM')")))){
            System.out.println("Enter time as per format");
        }
        printTaxiTypes();
        int choice = Validator.getNumberInRange(TaxiTypes.values().length);
        TaxiTypes taxiType = TaxiTypes.values()[choice - 1];
        driver = customerService.bookTaxi(customer.getMobileNumber(),pickUpPoint-1,dropPoint-1,pickUpTime,taxiType);
        if(driver != null){
            printMyRide();
        }
        else
            System.out.println("No Taxis Available");

    }
    private boolean cancelRide(){
        String decision = Validator.checkIfYesOrNo(InputReader.scanStringWithMsg("Enter Y to confirm Ride or N to cancel ride"));
        if(decision.equalsIgnoreCase("y")){
            System.out.println(customerService.confirmRide());
            return true;
        }
        else{
            System.out.println("Your ride has been canceled");
            return false;
        }
    }
    private void enterFeedback(){
        String feedback = InputReader.scanNextLine("Enter your feedback about this ride: ");
        System.out.println("Enter your rating for this ride: (rating must be between 1 to 5)");
        double rating=Validator.getRatingInRange(5.0);
        customerService.giveFeedback(feedback,rating);
    }
    private void viewMyRides(){
        int i=0;
        if(!customerService.viewTrips(customer.getMobileNumber()).isEmpty()){
            for(History trip : customerService.viewTrips(customer.getMobileNumber())){
                System.out.format("  %13s %11s %13s %11s  %8s\n","PickUp Point","Drop Point","PickUp Time","Drop Time","Fare");
                System.out.format("%d.  %16s %16s %6s %6s %6d\n",++i, trip.getPickUpPoint(),trip.getDropPoint(),trip.getPickUpTime(),trip.getDropTime(),trip.getEarnings());
                System.out.println("Feedback:");
                System.out.println(trip.getFeedback().getCustomerFeedback());
                System.out.format("Rating: %.2f",trip.getFeedback().getRating());
                System.out.println();
            }
        }
        else
            System.out.println("No trips available") ;
    }
    private void rentTaxi(){
        displayRoute();
        System.out.println("Enter the index of the PickUp point: ");
        int pickUpPoint = Validator.getNumberInRange(customerService.viewRoute().size());
        String pickUpTime = Validator.checkTimeIsValid(InputReader.scanNextLine("Enter PickUpTime: (in the format 'HH.MM')"));
        String time ;
        while(!Validator.timeFormatIsValid(time=Validator.checkTimeIsValid(InputReader.scanNextLine("Enter PickUpTime: (in the format 'HH.MM')")))){
            System.out.println("Enter time as per format");
        }
        printTaxiTypes();
        int choice = Validator.getNumberInRange(TaxiTypes.values().length);
        TaxiTypes taxiType = TaxiTypes.values()[choice - 1];
        driver=customerService.rentTaxi(customer.getMobileNumber(),pickUpPoint-1,pickUpTime,time,taxiType);
        if(driver != null){
            printMyRide();
        }
        else
            System.out.println("No Taxis Available");
    }

    private void displayRoute(){
        System.out.println();
        System.out.println("\tPoints: ");
        int i=1;
        for(Point point : customerService.viewRoute()){
            System.out.println(i+ ". "+point.getPointName());
            ++i;
        }
        System.out.println();
    }
    private void printMyRide(){
        System.out.println("Your OTP: "+ new Random().nextInt(1000,9999));
        int rideId = new Random().nextInt(1000,9999999);
        System.out.println("Ride ID: "+ rideId);
        System.out.println("Driver Mobile Number: "+ driver.getMobileNumber());
        System.out.println("Driver Name: "+ driver.getName());
        System.out.println("Taxi type: "+driver.getTaxi().getTaxiType());
        System.out.println("Rating: "+driver.getRating());
        System.out.println("Fare: "+customerService.getFare());
        if(cancelRide()){
            payFare();
            enterFeedback();
        }
    }
    private void payFare(){
        System.out.println("Enter the mode of transaction: ");
        for(PaymentOptions payment : PaymentOptions.values()){
            System.out.println(payment.ordinal() + 1 +". "+ payment.name());
        }
        int choice = Validator.getNumberInRange(PaymentOptions.values().length);
        PaymentOptions paymentType = PaymentOptions.values()[choice - 1];
        switch (paymentType){
            case CASH -> System.out.println("Pay Rs."+customerService.getFare());
            case THROUGH_WALLET -> transactionViaWallet();
            case UPI -> netBanking();
        }
    }
    private void transactionViaWallet(){
        String pin = Validator.validatePin(InputReader.scanStringWithMsg("Enter your Wallet Pin:(4 digits) "));
        int result =Authenticator.makePayment(customer.getMobileNumber(),pin,customerService.getFare());
        if(result == 0){
            System.out.println("There is no wallet present.");
            System.out.println();
            System.out.println("would you like to create a wallet?");
            String choice = Validator.checkIfYesOrNo(InputReader.scanStringWithMsg("Enter Y to create and N to continue"));
            if (choice.equalsIgnoreCase("y")){
                newWallet();
            }
        }

        else if(result==-1)  {
            System.out.println("Enter Valid pin.");
            transactionViaWallet();
        }
    }
    private void netBanking(){
        bankGateway();
        System.out.println("Payment Successful!");
    }
    private void newWallet(){
        String pin = Validator.validatePin(InputReader.scanStringWithMsg("Enter a 4 digits pin.(only numbers)"));
        paymentService.createNewWallet(customer.getMobileNumber(), pin);
        transactionViaWallet();
    }
    private void addMoneyToWallet(){
        System.out.println("Enter an amount less than 5000: (must me a whole number) ");
        int amount = Validator.getNumberInRange(5000);
        String pin = Validator.validatePin(InputReader.scanStringWithMsg("Enter your Wallet Pin: (4 digits) "));
        int result = Authenticator.addMoney(customer.getMobileNumber(),pin,amount);
        if(result==0){
            System.out.println("No wallets present");
        } else if (result==-1) {
            System.out.println("Enter Valid pin");
        }
    }
    private void viewBalance(){
        if(paymentService.getWallets().containsKey(customer.getMobileNumber())){
            System.out.println("your remaining amount in wallet is: "+ paymentService.viewBalance(customer.getMobileNumber()));
        }
        else
            System.out.println("Create a Wallet to view balance.");

    }
    private void printTaxiTypes(){
        for(TaxiTypes types : TaxiTypes.values()){
            System.out.println(types.ordinal() + 1 + ". "+ types.name());
        }
        System.out.println("Enter Your taxi preference: ");
    }
    private void bankGateway(){
        for(Banks banks : Banks.values()){
            System.out.println(banks.ordinal() + 1 + ". "+ banks.name());
        }
        System.out.println("Enter your bank: ");
        int choice =Validator.getNumberInRange(Banks.values().length);
        Banks bank = Banks.values()[choice-1];
        System.out.println("Welcome to "+ bank.name() + " Payment Gateway");
        Validator.validateAccNumber(InputReader.scanStringWithMsg("Enter your acc number: (8-19 digits)"));
        Validator.validatePin(InputReader.scanStringWithMsg("Enter your pin (4 digits)"));
    }
}
