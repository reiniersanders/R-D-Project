package com.example.wout.cryptojoint;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    Trader trader;
    Client client;

    private Context context;
    private Activity activity;
    private CoordinatorLayout layout;
    private PopupWindow popupWindow;

    TextView currencyView;
    TextView walletView;
    TextView balanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyView = findViewById(R.id.text_viewing_map);
        walletView = findViewById(R.id.text_holding_map);
        balanceView = findViewById(R.id.text_wallet_balance);

        context = getApplicationContext();
        activity = MainActivity.this;
        layout = (CoordinatorLayout) findViewById(R.id.layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View addProductView = inflater.inflate(R.layout.popup_trade, null);
                popupWindow = new PopupWindow(addProductView, CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                if(Build.VERSION.SDK_INT >= 21){
                    popupWindow.setElevation(5.0f);
                }
                Button cancelButton = (Button) addProductView.findViewById(R.id.buttonCancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        client = new Client(this);
        Updater updater = new Updater();
        trader = new Trader(updater, client,"Binance");
        client.setTrader(trader);

        Map<String, Double> wallet = new HashMap<>();
        client.setWallet(new Wallet(wallet, 10000.0, client)); //10000 usdt as start budget
        client.getWallet().addHolding("BTC", 50);

        updateViews();
    }

    /**
     *  this gets called whenever new trade information is received and the views should be updated
     */
    public void updateViews() {
        currencyView.setText(client.printCurrencies("BTC"));
        client.getWallet().printWallet(walletView, "LTC");
        balanceView.setText(client.getWallet().printBalance());
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
    /*
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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh){
            updateViews();
        }
        return super.onOptionsItemSelected(item);
    }
}