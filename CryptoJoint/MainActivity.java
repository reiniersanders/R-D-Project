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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new Client();

        //kijkt of er data geladen is (en laad deze meteen)
        if (!loadData()) {
            //maak nieuwe data aan
            List<Currency> currencies = new ArrayList();
            currencies.add(new Currency("btc", 7500.0));
            currencies.add(new Currency("eth", 500.0));
            currencies.add(new Currency("trx", 0.06));

            client.setCurrencies(currencies);
            Map<Currency, Double> wallet = new HashMap<>();
            client.setWallet(new Wallet(wallet, 0.0));
            client.getWallet().setHolding(currencies.get(1), 30.0);
        }

        //enkel om te testen
        TextView currenciesView = findViewById(R.id.Currencies);
        TextView walletView = findViewById(R.id.Wallet);

        Currency btc = client.getCurrencies().get(0);
        client.getWallet().setHolding(btc, client.getWallet().getHolding(btc) + 1);

        client.printCurrencies(currenciesView);
        System.out.println("");
        client.getWallet().printWallet(walletView, client.getCurrencies().get(2));

    }

    @Override
    protected void onPause() {
        super.onPause();
        client.getWallet().setBalance(client.getWallet().getBalance() + 5);
        saveData();
    }

    public boolean loadData(){
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
        return false;
    }

    public void saveData(){
        //sla de wallet en alle currencies met huidige waarde op
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
