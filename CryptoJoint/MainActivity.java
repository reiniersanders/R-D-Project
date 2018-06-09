package com.example.wout.traderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Client client;
    Trader trader;
    Updater updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client = new Client(this);
        updater = new Updater();
        trader = new Trader(updater, client,"Binance");
        client.setTrader(trader);

        //kijkt of er data geladen is (en laad deze meteen)
        if (!loadData()) {
            Map<String, Double> wallet = new HashMap<>();
            client.setWallet(new Wallet(wallet, 0.0, client));
        }

        //enkel om te testen
        /*
        updateViews();
        TextView currenciesView = findViewById(R.id.Currencies);
        TextView walletView = findViewById(R.id.Wallet);

        client.printCurrencies(currenciesView);
        System.out.println("");
        client.getWallet().printWallet(walletView, "USDT");
        */
    }

    /**
     *  this gets called whenever new trade information is received and the views should be updated
     */
    public void updateViews(){
        TextView currenciesView = findViewById(R.id.Currencies);
        TextView walletView = findViewById(R.id.Wallet);

        client.printCurrencies(currenciesView);
        System.out.println("");
        client.getWallet().printWallet(walletView, "USDT");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //saveData();
    }

    /**
     * @return returns if data is loaded successfully
     */
    public boolean loadData(){
        //not finished yet
        return false;
        /*
        //laad alle data die opgeslagen staat op het apparaat, als er niks staat return false
        File file = new File(getDir("data", Context.MODE_PRIVATE), "wallet");
        if (file.exists()) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                client.setCurrencies((List<Currency>) inputStream.readObject());
                client.setWallet((Wallet) inputStream.readObject());
                inputStream.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;*/
    }

    /**
     *  saves wallet and trader data to device
     */
    public void saveData(){
        //not finished yet
        File file = new File(getDir("data", Context.MODE_PRIVATE), "wallet");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(client.getCurrencies());
            outputStream.writeObject(client.getWallet());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
