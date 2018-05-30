/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traderapp;

/**
 *
 * @author Ferran
 */
public class Trade {
    public Trader trader;
    public Currency start;
    public Currency target;
    public double amount;
    public double targetvalue;
    public double amounttarget;
    public int id;
    
    public Trade(Trader trader, Currency start, double amount, Currency target, double targetvalue, int id)
    {
        this.trader=trader;
        this.start=start;
        this.amount=amount;
        this.target=target;
        this.targetvalue=targetvalue;
        this.id=id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public double currencyAmount()
    {
        return (start.getValue()*amount)/(target.getValue());
    }
    
    public boolean doTrade()
    {
        if (targetvalue==0)
        {
            trader.addWallet(target, currencyAmount());
            trader.removeTrade(this);
            return true;
        }
        if (target.getValue()<= targetvalue)
        {
            trader.addWallet(target, currencyAmount());
            trader.removeTrade(this);
            return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return amount + " " + start + " for " + target + " with id: " + id;
    }
}
