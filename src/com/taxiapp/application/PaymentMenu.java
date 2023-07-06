package com.taxiapp.application;

import com.taxiapp.coordinators.PaymentService;
import com.taxiapp.library.ElectronicWallet;
import com.taxiapp.utilities.InputReader;
import com.taxiapp.utilities.Validator;


public class PaymentMenu implements Menu {
    private final ElectronicWallet wallet;
    private final PaymentService paymentService;
    private final int fare;

    public PaymentMenu(ElectronicWallet wallet, PaymentService paymentService, int fare) {
        this.wallet = wallet;
        this.paymentService = paymentService;
        this.fare = fare;
    }

    public void start() {
        System.out.println("Available Balance: "+wallet.getBalance());
        String choice = Validator.checkIfYesOrNo(InputReader.scanStringWithMsg("Would you like add more money or continue with the transaction? \n Enter Y for adding money else Enter N to continue: "));
        if (choice.equalsIgnoreCase("N")){
            if(paymentService.payAmount(wallet,fare)){
                System.out.println("Amount paid: "+fare);
                System.out.println("Payment Successful!");
            }
            else  {
                System.out.println("insufficient balance ");
                String newChoice = Validator.checkIfYesOrNo(InputReader.scanStringWithMsg("Enter Y to retry transaction or N to pay by cash: "));
                if(newChoice.equalsIgnoreCase("Y")){
                    start();
                }
                else if(newChoice.equalsIgnoreCase("N")){
                    System.out.println("Pay Rs."+ fare+" as cash.");
                }

            }

        } else if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Enter an amount  less than 5000");
            int amount = Validator.getNumberInRange(5000);
            addAmount(amount);
            start();
        }

    }
    private void addAmount(int amount){
        System.out.println(paymentService.addAmount(wallet,amount));
    }
    public void addAmount(){
        System.out.println(paymentService.addAmount(wallet,fare));
    }
}
