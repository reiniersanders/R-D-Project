package com.example.wout.cryptojoint;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection extends AsyncTask {
    private Client client;

    public Connection(Client client){
        this.client = client;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            APIBinance api = new APIBinance("BTCUSDT", "https://api.binance.com/api/v3/ticker/price");
            client.setCurrencies(api.getSymbols());
        } catch (IOException ex) {
            Logger.getLogger(Trader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}