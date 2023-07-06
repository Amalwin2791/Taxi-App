package com.taxiapp.coordinators;

import com.taxiapp.database.interfaces.PaymentManager;
import com.taxiapp.library.ElectronicWallet;

import java.util.Map;

public class PaymentService {
    private final PaymentManager paymentManager;
    public PaymentService(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    public boolean payAmount(ElectronicWallet wallet, int fare) {
      return paymentManager.payAmount(wallet,fare);
    }

    public String addAmount(ElectronicWallet wallet , int amount) {
        return paymentManager.addAmount(wallet, amount);
    }

    public String createNewWallet(String customerId, String pin) {
        paymentManager.getAllWallets().put(customerId,new ElectronicWallet(customerId,pin,0));
        return "Successfully created";
    }
    public Map<String,ElectronicWallet> getWallets(){
        return paymentManager.getAllWallets();
    }

    public int viewBalance(String id) {
        if(paymentManager.getAllWallets().containsKey(id)){
            return paymentManager.getAllWallets().get(id).getBalance();
        }
        return 0;
    }

}
