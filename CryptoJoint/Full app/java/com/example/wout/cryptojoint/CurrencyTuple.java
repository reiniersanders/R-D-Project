package com.example.wout.cryptojoint;

/**
 *
 * @author Laurens
 */
public class CurrencyTuple {
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
