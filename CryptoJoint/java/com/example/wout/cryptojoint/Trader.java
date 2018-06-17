package com.example.wout.cryptojoint;

import java.io.IOException;
import java.lang.reflect.Array;
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
    private ArrayList<MoneyTrade> buys;
    private ArrayList<MoneyTrade> sells;

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
        buys = new ArrayList<>();
        sells = new ArrayList<>();
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

    public void setTrades(ArrayList<Trade> trades){
        this.trades = trades;
    }

    public void setBuys(ArrayList<MoneyTrade> buys){
        this.buys = buys;
    }

    public ArrayList<MoneyTrade> getBuys(){
        return buys;
    }

    public void setSells(ArrayList<MoneyTrade> sells){
        this.sells = sells;
    }

    public ArrayList<MoneyTrade> getSells(){
        return sells;
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

    public void buyCurrency(Currency currency, double amount){
        buys.add(new MoneyTrade(currency, amount));
    }

    public void updateBuys(){
        DollarValue dollarValue = new DollarValue();
        dollarValue.updateValues();

        for(MoneyTrade buy : buys) {
            ArrayList<Currency> currencyValues = client.currencyValues(buy.getCurrency().getName());
            System.out.println(buy.getCurrency().getName());
            double value = dollarValue.getValue(currencyValues);
            if (value == 0)
                value = dollarValue.updateValue(buy.getCurrency().getName());
            if (value != 0) {
                System.out.println(value);
                client.getWallet().addHolding(buy.getCurrency().getName(), buy.getAmount() / value);
                buys.remove(buy);
            }
        }
    }

    public void sellCurrency(Currency currency, double amount){
        sells.add(new MoneyTrade(currency, amount));
    }

    public void updateSells(){
        DollarValue dollarValue = new DollarValue();
        dollarValue.updateValues();

        for(MoneyTrade sell : sells) {
            ArrayList<Currency> currencyValues = client.currencyValues(sell.getCurrency().getName());
            System.out.println(sell.getCurrency().getName());
            double value = dollarValue.getValue(currencyValues);
            if (value == 0)
                value = dollarValue.updateValue(sell.getCurrency().getName());
            if (value != 0) {
                System.out.println(value);
                client.getWallet().setBalance(client.getWallet().getBalance() + sell.getAmount() * value);
                sells.remove(sell);
            }
        }
    }

    /**
     * Does a couple of functions every 2 seconds.
     */
    @Override
    public void run() {
        updateTrader();
        doTrades();
        updateBuys();
        updateSells();
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