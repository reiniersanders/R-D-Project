package com.example.wout.cryptojoint;

import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class Wallet implements Serializable{
    private Map<String, Double> wallet;
    private double balance;
    private ArrayList<Currency> holdingValues;

    /**
     * @param wallet    a map that contains the holdings of the wallet
     * @param balance   the balance of the wallet
     * @param client    the Client of this wallet
     */
    public Wallet(Map<String, Double> wallet, Double balance){
        this.wallet = wallet;
        this.balance = balance;
        holdingValues = new ArrayList<>();
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return balance;
    }

    /**
     * @param currency  string of currency
     * @return          returns how many of that currency is in wallet
     */
    public double getHolding(String currency){
        //verander currency in wallet
        if (wallet.get(currency) != null)
            return wallet.get(currency);
        else
            return 0;
    }

    /**
     * @param currency  string of currency
     * @param amount    adds amount of currency to wallet
     */
    public void addHolding(String currency, double amount){
        setHolding(currency, getHolding(currency) + amount);
    }

    /**
     * @param currency  string of currency
     * @param amount    sets amount of currency in wallet
     */
    public void setHolding(String currency, double amount){
        //voeg currency toe in wallet of verander bestaande currency in wallet
        wallet.put(currency, amount);
    }

    /**
     * @param currencyName  string of currency
     * @param valueAs       string of currency to which currency should be compared to
     * @return              returns how much one currency is worth in valueAs
     */
    public Double getValue(String currencyName, String valueAs, Client client){
        ArrayList<Currency> currencies = client.currencyValues(valueAs);
        for(Currency currency : currencies){
            if (currency.getName().equals(currencyName))
                return currency.getValue();
        }
        return null;
    }

    /**
     * @param t         textview that wallet should be printed to
     * @param compareTo currency that wallet holdings should be compared to/USDT gives dollar value of currencies in wallet
     */
    public void printWallet(TextView t, String compareTo, Client client){
        //moet veranderd worden afhankelijk van frontend
        String text = "";
        if (compareTo == "USDT"){
            for(Currency currency : holdingValues){
                text += " - " + wallet.get(currency.getName()) + " " + currency.getName() + " ($" + currency.getValue()*wallet.get(currency.getName()) + ")\n";
            }
        }else{
            for (Map.Entry<String, Double> currency : wallet.entrySet()) {
                if (currency.getKey() != compareTo) {
                    if (getValue(currency.getKey(), compareTo, client) == null)
                        text += " - " + currency.getValue() + " " + currency.getKey() + " (unknown value) \n";
                    else
                        text += " - " + currency.getValue() + " " + currency.getKey() + " (" + currency.getValue() * getValue(currency.getKey(), compareTo, client) + " " + compareTo + ")\n";
                } else
                    text += " - " + currency.getValue() + " " + currency.getKey() + "\n";
            }
        }
        t.setText(text);
    }

    public ArrayList<String> getHoldings(){
        ArrayList<String> holdings = new ArrayList<>();
        for(Map.Entry<String, Double> currency : wallet.entrySet()){
            holdings.add(currency.getKey());
        }
        return holdings;
    }

    public void updateDollarValues(ArrayList<Currency> holdingValues){
        this.holdingValues = holdingValues;
    }

    public String printBalance(){
        DecimalFormat moneyFormat = new DecimalFormat("#.00");
        return "$" + moneyFormat.format(balance);
    }

    public String printWalletValue(){
        double value = balance;
        for(Currency holding : holdingValues){
            value += holding.getValue() * wallet.get(holding.getName());
        }
        DecimalFormat moneyFormat = new DecimalFormat("#.00");
        return "$" + moneyFormat.format(value);
    }
}