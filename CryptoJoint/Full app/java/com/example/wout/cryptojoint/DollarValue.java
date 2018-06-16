package com.example.wout.cryptojoint;

import java.io.IOException;
import java.util.ArrayList;

public class DollarValue {
    APIBinance api;
    double btcValue;
    double ethValue;

    public double getValue(ArrayList<Currency> currencyValues){
        for(Currency currency : currencyValues){
            if (currency.getName().equals("BTC"))
                return (1 / currency.getValue()) * btcValue;
            else if (currency.getName().equals("ETH"))
                return (1 / currency.getValue()) * ethValue;
        }
        return 0;
    }

    public void updateValues(){
        btcValue = updateValue("BTC");
        ethValue = updateValue("ETH");
    }

    public double updateValue(String currency){
        api = new APIBinance(currency + "USDT", "https://api.binance.com/api/v3/ticker/price");
        try {
            return api.makeCall();
        } catch (IOException e) {
            return 0;
        }
    }

}
