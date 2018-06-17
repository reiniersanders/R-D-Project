package com.example.wout.cryptojoint;

import java.io.Serializable;

/**
 *
 * @author Laurens
 */
public class CurrencyTuple implements Serializable{
    private Currency owned;
    private Currency notOwned;
    private double price;

    public CurrencyTuple(Currency owned, Currency notOwned) {
        this.owned = owned;
        this.notOwned = notOwned;
        this.price = 0.00;
    }

    public Currency getOwned() {
        return this.owned;
    }

    public Currency getNotOwned() {
        return this.notOwned;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
