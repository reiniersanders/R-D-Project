package com.example.wout.cryptojoint;

import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private ArrayList<CurrencyTuple> currencies;
    private Wallet wallet;
    private MainActivity mainActivity;
    private Trader trader;

    /**
     * @param mainActivity  the MainActivity of this client
     */
    public Client(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        currencies = new ArrayList<>();
    }

    /**
     * updates views
     */
    public void updateViews(){
        mainActivity.updateViews();
    }

    public MainActivity getMainActivity(){
        return mainActivity;
    }

    /**
     * @param trader    the Trader of this client
     */
    public void setTrader(Trader trader){
        this.trader = trader;
    }

    /**
     * @param wallet    the Wallet of this client
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWallet(){
        return wallet;
    }

    /**
     * @param currencies    sets the list with CurrencyTuples of this client
     */
    public void setCurrencies(ArrayList<CurrencyTuple> currencies){
        //set alle currencies
        this.currencies = currencies;
    }

    public ArrayList<CurrencyTuple> getCurrencies(){
        //get alle currencies
        return currencies;
    }

    /**
     * @param t     the textfield that the values of the currencies (compared to dollar) need to be printed to
     * @param printAs currency to be compared to
     */
    public void printCurrencies(TextView t, String printAs){
        //moet veranderd worden afhankelijk van frontend
        ArrayList<Currency> currencyValues = currencyValues(printAs);
        String text = "";
        DecimalFormat moneyFormat = new DecimalFormat("#0.00");
        for (Currency currency : currencyValues) {
            text += currency.getName() + " = " + currency.getValue() + " " + printAs + "\n";
        }
        t.setText(text);
    }


    /**
     * Creates an arraylist with all the currencytuples with USDT as not owned.
     * This means you get the dollar values of all the currencies.
     *
     * @param currencyname the name of the currency to which the other currencies are compared to
     *
     * @return This is the arraylist of all the currencies with their dollar values.
     */
    public ArrayList<Currency> currencyValues(String currencyname)
    {
        ArrayList<Currency> currencyvalues = new ArrayList();
        for (CurrencyTuple currency : currencies)
        {
            if(currency.getNotOwned().getName().equals(currencyname))
                currencyvalues.add(new Currency(currency.getOwned().getName(), currency.getPrice()));
            else if (currency.getOwned().getName().equals(currencyname))
                currencyvalues.add(new Currency(currency.getNotOwned().getName(), (1/currency.getPrice())));
        }
        return currencyvalues;
    }

    /**
     * @param currencyName  name of currency
     * @return              returns object of currency with given currencyName
     */
    public Currency getCurrency(String currencyName){
        for(CurrencyTuple currencyTuple : currencies){
            if (currencyTuple.getNotOwned().getName().equals(currencyName))
                return currencyTuple.getNotOwned();
            else if (currencyTuple.getOwned().getName().equals(currencyName))
                return currencyTuple.getOwned();
        }
        return null;
    }

    /**
     * @param currency  currency that needs to be bought
     * @param amount    amount that needs to be bought
     */
    public void buy(Currency currency, double amount){
        //koop x aantal currency met geld van balance
        if (wallet.getBalance() > amount) {
            wallet.setBalance(wallet.getBalance() - amount);
            trader.buyCurrency(currency, amount);
            Toast.makeText(mainActivity, "Buy order made.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(mainActivity, "Insufficient funds.", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param currency  currency that needs to be sold
     * @param amount    amount that needs to be sold
     */
    public void sell(Currency currency, double amount){
        //verkoop x aantal currency met geld van balance)
        if (wallet.getHolding(currency.getName()) > amount) {
            wallet.addHolding(currency.getName(), -amount);
            trader.sellCurrency(currency, amount);
            Toast.makeText(mainActivity, "Sell order made.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mainActivity, "Exceeding available amount.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param toSell        currency that needs to be sold
     * @param amountSell    amount that needs to be sold
     * @param toBuy         currency that needs to be bought
     * @param amountBuy     The value at which you want to conduct the trade.
     *                      If this is equal to 0 conduct the trade at the next update.
     */
    public void trade(Currency toSell, double amountSell, Currency toBuy, double amountBuy){
        //trade currencies
        if (wallet.getHolding(toSell.getName()) > amountSell) {
            wallet.addHolding(toSell.getName(), -amountSell);
            trader.Trademaker(true, toSell, amountSell, toBuy, amountBuy);
        }
    }
}
