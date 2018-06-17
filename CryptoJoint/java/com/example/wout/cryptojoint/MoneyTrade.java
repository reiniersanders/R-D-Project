package com.example.wout.cryptojoint;

import java.io.Serializable;

public class MoneyTrade implements Serializable{
    private Currency currency;
    private double amount;

    public MoneyTrade(Currency currency, double amount){
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }
}