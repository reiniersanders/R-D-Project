package com.example.wout.traderapp;

import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class Client {
    private List<Currency> currencies;
    private Wallet wallet;

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWallet(){
        return wallet;
    }

    public void setCurrencies(List<Currency> currencies){
        //set alle currencies
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies(){
        //get alle currencies
        return currencies;
    }

    public void printCurrencies(TextView t){
        //moet veranderd worden afhankelijk van frontend
        String text = "";
        text += "currencies:";
        DecimalFormat moneyFormat = new DecimalFormat("#0.00");
        for(Currency currency : currencies){
            text += "\n " + currency.getName() + ": " + moneyFormat.format(currency.getValue()) + " usd";
        }
        t.setText(text);
    }

    public void printCurrencies(TextView t, Currency compareTo){
        //moet veranderd worden afhankelijk van frontend
        String text = "";
        text += "currencies:";
        for(Currency currency : currencies){
            if (currency != compareTo) {
                DecimalFormat curFormat = new DecimalFormat("#0.########");
                double valueInUSD = currency.getValue();
                double valueInComparedCurrency = valueInUSD/compareTo.getValue();
                text += "\n 1 " + currency.getName() + "  same as " + curFormat.format(valueInComparedCurrency) + " " + compareTo.getName();
            }
        }
        t.setText(text);
    }

    public void buy(Currency currency, double amount){
        //koop x aantal currency met geld van balance
    }

    public void sell(Currency currency, double amount){
        //verkoop x aantal currency met geld van balance
    }

    public void trade(){
        //trade currencies
    }
}
