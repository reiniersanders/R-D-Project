package com.example.wout.cryptojoint;

import java.io.IOException;
import java.util.ArrayList;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Laurens
 */
public class APIFetcher {
    private APIBinance binance;

    public APIFetcher(){
    }

    /**
     *  gets the format for an exchange API, since symbol formatting is different
     * per exchange, this structure is needed
     * @param toBuy The currency which is to be bought
     * @param toSell The currency to be sold
     * @param Exchange the Exchange on which to buy/sell
     * @return object which inherits API
     */
    public API GetFormat(Currency toBuy, Currency toSell, String Exchange){
        switch (Exchange) {
            case "Binance": return MakeBinanceAPI((toSell.getName() + toBuy.getName()));
            default: return null;
        }
    }

    public ArrayList<CurrencyTuple> getSymbols(String Exchange) throws IOException{
        switch (Exchange) {
            case "Binance": return binance.getSymbols();
        }
        return null;
    }

    public APIBinance MakeBinanceAPI(String Endpoint){
        return new APIBinance(Endpoint.toUpperCase(),  "https://api.binance.com/api/v3/ticker/price");
    }
}