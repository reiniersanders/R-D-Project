package com.example.wout.traderapp;

import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;

public class Wallet implements Serializable{
    private Map<Currency, Double> wallet;
    private double balance;

    public Wallet(Map<Currency, Double> wallet, Double balance){
        this.wallet = wallet;
        this.balance = balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return balance;
    }

    public double getHolding(Currency currency){
        //verander currency in wallet
        if (wallet.get(currency) != null)
            return wallet.get(currency);
        else
            return 0;
    }

    public void setHolding(Currency currency, double amount){
        //voeg currency toe in wallet of verander bestaande currency in wallet
        if (wallet.get(currency) != null){
            wallet.put(currency, amount);
        }else{
            wallet.put(currency, amount);
        }
    }

    public void printWallet(TextView t){
        //moet veranderd worden afhankelijk van frontend
        String text = "";
        text += "balance: " + balance;
        text += "\ncurrencies in wallet:";
        for(Map.Entry<Currency, Double> currency : wallet.entrySet()){
            DecimalFormat moneyFormat = new DecimalFormat("#.00");
            DecimalFormat curFormat = new DecimalFormat("#0.########");
            text += "\n - " + curFormat.format(currency.getValue()) + " " + currency.getKey().getName() + " (" + moneyFormat.format(currency.getValue()*currency.getKey().getValue()) + " usd)";
        }
        t.setText(text);
    }

    public void printWallet(TextView t, Currency compareTo){
        //moet veranderd worden afhankelijk van frontend
        String text = "";
        DecimalFormat moneyFormat = new DecimalFormat("#.00");
        text += "balance: " + moneyFormat.format(balance) + " usd";
        text += "\ncurrencies in wallet:";
        for(Map.Entry<Currency, Double> currency : wallet.entrySet()){
            if (currency != compareTo) {
                DecimalFormat curFormat = new DecimalFormat("#0.########");
                double valueInUSD = currency.getValue()*currency.getKey().getValue();
                double valueInComparedCurrency = valueInUSD / compareTo.getValue();
                text += "\n - " + currency.getValue() + " " + currency.getKey().getName() + " (" + curFormat.format(valueInComparedCurrency) + " " + compareTo.getName() + ")";
            }else{
                text += "\n - " + currency.getValue() + " " + currency.getKey().getName();
            }
        }
        t.setText(text);
    }
}
