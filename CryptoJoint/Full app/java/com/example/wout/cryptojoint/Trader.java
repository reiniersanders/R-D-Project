package com.example.wout.cryptojoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ferran
 */
public class Trader extends TimerTask{
    private Updater updater;
    private String exchange;
    private static int id;
    private ArrayList<Trade> trades;
    private Wallet wallet;
    private Client client;
    private Connection connection;

    /**
     * Constructor of Trader.
     * Only to be used once.
     *
     * @param updater    The updater of this trader.
     * @param exchange   The exchange of this trader.
     * @param client     The client of this trader.
     */
    public Trader(Updater updater, Client client, String exchange)
    {
        this.updater=updater;
        this.exchange=exchange;

        this.client=client;

        connection = new Connection(client);
        connection.execute();


        id=1;
        trades = new ArrayList();
        wallet = client.getWallet();
        start();
    }

    /**
     * @param updater the updater of this trader
     */
    public void setUpdater(Updater updater){
        this.updater = updater;
    }

    /**
     * @param client the updater of this trader
     */
    public void setClient(Client client){
        this.client = client;
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
        wallet.addHolding(currency.getName(), amount);
    }
    /**
     * Changes the list of currencies that the trader has at the moment
     * with another list of currencies.
     * Also updates the values of all the trades.
     *
     */
    public void updateTrader()
    {
        ArrayList<CurrencyTuple> currencies = client.getCurrencies();
        client.setCurrencies(updater.GetUpdate(currencies, exchange));
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
     * Simple getter.
     * @return A list of all the trades.
     */
    public ArrayList<Trade> getTrades()
    {
        return trades;
    }
    /**
     * Simple getter.
     * @return The wallet of the client.
     */
    public Wallet getWallet()
    {
        return client.getWallet();
    }
    /**
     * Simple getter.
     * @return The currency list of the client.
     */
    public ArrayList<CurrencyTuple> getCurrencies()
    {
        return client.getCurrencies();
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

    public void updateHoldings(){
        DollarValue dollarValue = new DollarValue();
        dollarValue.updateValues();

        ArrayList<Currency> holdingValues = new ArrayList<>();
        ArrayList<String> holdings = client.getWallet().getHoldings();
        for(String holding : holdings){
            ArrayList<Currency> currencyValues = client.currencyValues(holding);
            double value = dollarValue.getValue(currencyValues);
            if (value == 0)
                value = dollarValue.updateValue(holding);
            holdingValues.add(new Currency(holding, value));
        }

        client.getWallet().updateDollarValues(holdingValues);
    }

    /**
     * Does a couple of functions every 2 seconds.
     */
    @Override
    public void run() {
        updateTrader();
        doTrades();
        updateHoldings();

        try {
            synchronized (this) {
                wait(500);

                client.getMainActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        client.updateViews();
                    }
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}