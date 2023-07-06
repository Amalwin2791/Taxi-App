package com.taxiapp.database.interfaces;

import com.taxiapp.library.ElectronicWallet;

import java.util.Map;

public interface PaymentManager {
    Map<String , ElectronicWallet> getAllWallets();
    boolean payAmount(ElectronicWallet wallet, int fare);
    String addAmount(ElectronicWallet wallet , int amount);

}
