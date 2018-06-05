/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.app;

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
    private Updater updater;
    private String exchange;
    private ArrayList<CurrencyTuple> currencies;
    private static int id;
    private ArrayList<Trade> trades;
    private Map wallet;
    /**
    * Constructor of Trader.
    * Only to be used once.
    * 
    * @param updater    The updater of this trader.
    * @param exchange   The exchange of this trader.
    */
    public Trader(Updater updater, String exchange)
    {
        this.updater=updater;
        this.exchange=exchange;
        currencies = updater.GetSymbols(exchange);
        id=1;
        trades = new ArrayList();
        wallet = new HashMap();
        start();
    }
    /**
     * Starts a timer that does run() every 2 seconds.
     */
    public void start()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, 2000);
    }
    /**
     * Removes a trade from the list of trades.
     * 
     * @param trade The trade that has to be removed from the list.
     */
    public void removeTrade(Trade trade)
    {
        trades.remove(trade);
    }
    /**
     * Adds an amount of a currency to the wallet of the trader.
     * 
     * @param currency  The currency which is to be added to the wallet.
     * @param amount    The amount of the currency which iss to be added to the wallet.
     */
    public void addWallet(Currency currency, double amount)
    {
        if (wallet.containsKey(currency))
        {
            amount=amount+(double)wallet.get(currency);
        }
        wallet.put(currency, amount);
    }
    /**
     * Changes the list of currencies that the trader has at the moment
     * with another list of currencies.
     * Also updates the values of all the trades.
     * 
     */
    public void updateTrader()
    {
        this.currencies=updater.GetUpdate(currencies, exchange);
        for (Trade trade: trades)
        {
            trade.updateValues();
        }
    }
    /**
     * For all the trades in the list trades try to conduct the trade.
     */
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
    /**
     * Creates an arraylist with all the currencytuples with USDT as not owned.
     * This means you get the dollar values of all the currencies.
     * 
     * @return This is the arraylist of all the currencies with their dollar values.
     */
    public ArrayList<Currency> currencyValues()
    {
        ArrayList<Currency> currencyvalues = new ArrayList();
        for (CurrencyTuple currency:currencies)
        {
            if(currency.getNotOwned().getName().equals("USDT"))
                currencyvalues.add(new Currency(currency.getOwned().getName(), currency.getPrice()));
        }
        return currencyvalues;
    }
    
    public void updateWallet()
    {
        
    }
    
    public ArrayList<CurrencyTuple> getCurrencyList()
    {
        return currencies;
    }
    /**
     * Makes a new trade and adds it to the list of trades.
     * This can be both a buy trade or a sell trade.
     * 
     * @param buy       Indicates whether it is a buy or sell trade.
     *                  true if it is a buy trade.
     *                  falsse if it is a sell trade.
     * @param currency  The currency which you are going to trade.
     * @param amount    The amount of the currency which you are going to trade.
     * @param target    The currency you want to get after this trade.
     * @param value     The value at which you want to conduct the trade.
     *                  If this is equal to 0 conduct the trade at the next update.
     */
    public void Trademaker(boolean buy, Currency currency, double amount, Currency target, double value)
    {
        Trade trade = new Trade(buy, this, currency, amount, target, value, id);
        trades.add(trade);
        id++;
    }
    /**
     * Does a couple of functions every 2 seconds.
     */
    @Override
    public void run() {
        updateTrader();
        doTrades();
        updateWallet();
    }

}
