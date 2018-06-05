/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.app;

import java.util.ArrayList;

/**
 *
 * @author Ferran
 */
public class Trade {
    public boolean buy;
    public Trader trader;
    public CurrencyTuple currencytuple;
    public double amount;
    public double targetvalue;
    public double amounttarget;
    public int id;
    /**
     * Constructor of Trade
     * 
     * @param buy           Indicates whether it is a buy or sell trade.
     *                          true if it is a buy trade.
     *                          false if it is a sell trade.
     * @param trader        The trader which conducts this trade.
     * @param start         The currency which you are going to trade.  
     * @param amount        The amount of the currency which you are going to trade.
     * @param target        The currency you want to get after this trade.
     * @param targetvalue   The value at which you want to conduct the trade.
     *                          If this is equal to 0 conduct the trade at the next update.
     * @param id            The trade id. This is unique for every trade.
     */
    public Trade(boolean buy, Trader trader, Currency start, double amount, Currency target, double targetvalue, int id)
    {
        this.buy=buy;
        this.trader=trader;
        this.currencytuple=new CurrencyTuple(start, target);
        this.amount=amount;
        this.targetvalue=targetvalue;
        this.id=id;
    }
    /**
     * Updates the values of the two currencies in this trade.
     */
    public void updateValues()
    {
        ArrayList<CurrencyTuple> currencies = trader.getCurrencyList();
        for (CurrencyTuple currency: currencies)
        {
            if (currencytuple.getOwned().getName().equals(currency.getOwned().getName()) && 
                currencytuple.getNotOwned().getName().equals(currency.getNotOwned().getName()))
                currencytuple.setPrice(currency.getPrice());
        }
    }
    /**
     * Simple getter.
     * @return  The id of the trade.
     */
    public int getId()
    {
        return id;
    }
    
    private double currencyAmount()
    {
        return (currencytuple.getPrice()*amount);
    }
    /**
     * Conducts the trade if the circumstances allow it.
     * @return  True if the trade has been conducted succesfully.
     *          False if the trade has not been conducted.
     */
    public boolean doTrade()
    {
        if (buy);
        {
            if (targetvalue==0)
            {
                trader.addWallet(currencytuple.getNotOwned(), currencyAmount());
                trader.removeTrade(this);
                return true;
            }
            if (currencytuple.getPrice()<= targetvalue)
            {
                trader.addWallet(currencytuple.getNotOwned(), currencyAmount());
                trader.removeTrade(this);
                return true;
            }
        }
        if (!buy)
        {
            if (targetvalue==0)
            {
                trader.addWallet(currencytuple.getNotOwned(), currencyAmount());
                trader.removeTrade(this);
                return true;
            }
            if (currencytuple.getPrice()<= targetvalue)
            {
                trader.addWallet(currencytuple.getNotOwned(), currencyAmount());
                trader.removeTrade(this);
                return true;
            }
        }
        return false;
    }
    /**
     * Simple toString.
     * @return The trade in string format.
     */
    @Override
    public String toString()
    {
        return amount + " " + currencytuple.getOwned() + " for " + currencytuple.getNotOwned() + " with id: " + id;
    }
}
