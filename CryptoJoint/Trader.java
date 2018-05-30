/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traderapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Ferran
 */
public class Trader extends TimerTask{
    private ArrayList<Currency> currencies;
    private static int id;
    private ArrayList<Trade> trades;
    private Map wallet;
    
    public Trader()
    {
        currencies = new ArrayList();
        id=1;
        trades = new ArrayList();
        wallet = new HashMap();
        start();
    }
    
    public void start()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, 2000);
    }
    
    public void removeTrade(Trade trade)
    {
        trades.remove(trade);
    }
    
    public void addWallet(Currency currency, double amount)
    {
        if (wallet.containsKey(currency))
        {
            amount=amount+(double)wallet.get(currency);
        }
        wallet.put(currency, amount);
    }
    
    public void showWallet()
    {
        for (Currency currency:currencies)
        {
            System.out.println("Currency: " + currency + ", amount: " + wallet.getOrDefault(currency, 0));
        }
    }
    
    public void updateTrader(ArrayList currencies)
    {
        this.currencies=currencies;
    }
    
    public void doTrades()
    {
        int length = trades.size();
        for(int i = 0; i<length; i++)
        {
            Trade trade = trades.get(i);
            System.out.println(trade);
            if(trade.doTrade())
            {
                System.out.println("Trade " + trade + " succesfull.");
                i--;
                length--;
            }
        }
        
    }
    
    public void Trademaker(Currency currency, double amount, Currency target, double value)
    {
        Trade trade = new Trade(this, currency, amount, target, value, id);
        trades.add(trade);
        id++;
    }

    @Override
    public void run() {
        System.out.println("hoi");
        doTrades();
        showWallet();
    }
}
