package com.example.wout.cryptojoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Trader trader;
    Client client;
    Updater updater;

    private double balance = 10000;
    private int option = 0;
    private Context context;
    private Activity activity;
    private CoordinatorLayout layout;
    private PopupWindow popupWindow;
    private ProgressBar progressBar;

    private TextView currencyView;
    private TextView walletView;
    private TextView balanceView;
    private TextView valueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new Client(this);
        updater = new Updater();
        trader = new Trader(updater, client,"Binance");
        client.setTrader(trader);

        final Map<String, Double> wallet = new HashMap<>();
        client.setWallet(new Wallet(wallet, balance));

        currencyView = findViewById(R.id.text_viewing_map);
        walletView = findViewById(R.id.text_holding_map);
        balanceView = findViewById(R.id.text_wallet_balance);
        valueView = findViewById(R.id.text_wallet_value);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

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
                final View addProductView = inflater.inflate(R.layout.popup_trade, null);
                popupWindow = new PopupWindow(addProductView, CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT, true);

                if(Build.VERSION.SDK_INT >= 21){
                    popupWindow.setElevation(5.0f);
                }

                RadioGroup radioGroup = addProductView.findViewById(R.id.radioGroupBuySell);
                Button cancelButton = addProductView.findViewById(R.id.buttonCancel);
                Button doneButton = addProductView.findViewById(R.id.buttonDone);
                final EditText editText = addProductView.findViewById(R.id.editText);
                final Spinner spinner = addProductView.findViewById(R.id.spinner);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int value) {
                        if(value == R.id.radioButtonBuy) {
                            option = 1;
                            List<String> buyList = new ArrayList<>();
                            for(int i = 0; i < client.getCurrencies().size(); i++) {
                                buyList.add(client.getCurrencies().get(i).getOwned().getName());
                            }
                            for(int i = 0; i < client.getCurrencies().size(); i++) {
                                buyList.add(client.getCurrencies().get(i).getNotOwned().getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_spinner_item, buyList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                        } else {
                            option = -1;
                            List<String> sellList = new ArrayList<>();
                            for(int i = 0; i < client.getCurrencies().size(); i++) {
                                sellList.add(client.getCurrencies().get(i).getOwned().getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_spinner_item, sellList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                        }
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (option == 0) {
                            Toast.makeText(context, "Please select an option.", Toast.LENGTH_SHORT).show();
                        }
                        if(option == 1) { // Buy
                            if(!editText.getText().toString().isEmpty()) {
                                option = 0;
                                client.buy(client.getCurrency("LTC"), Double.parseDouble(editText.getText().toString()));
                                popupWindow.dismiss();
                            } else {
                                Toast.makeText(context, "Please enter amount.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (option == -1) { // Sell
                            if(!editText.getText().toString().isEmpty()) {
                                option = 0;
                                client.sell(client.getCurrency("LTC"), Double.parseDouble(editText.getText().toString()));
                                Toast.makeText(context, "Sell order made.", Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                            } else {
                                Toast.makeText(context, "Please enter amount.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        option = 0;
                        updateViews();
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
        if (!loadData()) {
            System.out.println("new data");
            client = new Client(this);
            Updater updater = new Updater();
            trader = new Trader(updater, client, "Binance");
            client.setTrader(trader);

            client.setWallet(new Wallet(wallet, 10000.0));
        }else
            System.out.println("loaded data");

        updateViews();
    }

    /**
     *  this gets called whenever new trade information is received and the views should be updated
     */
    public void updateViews() {
        client.printCurrencies(currencyView, "BTC");
        client.getWallet().printWallet(walletView, "USDT", client);
        valueView.setText(client.getWallet().printWalletValue());
        balanceView.setText(client.getWallet().printBalance());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onStop(){
        super.onStop();
        saveData();
    }

    /**
     * @return returns if data is loaded successfully
     */

    public boolean loadData(){
        //not finished yet
        //laad alle data die opgeslagen staat op het apparaat, als er niks staat return false
        File file = new File(getDir("data", Context.MODE_PRIVATE), "cryptojoint");
        if (file.exists()) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));

                client = new Client(this);

                client.setCurrencies((ArrayList<CurrencyTuple>) inputStream.readObject());
                client.setWallet((Wallet) inputStream.readObject());


                Updater updater = new Updater();
                trader = new Trader(updater, client, "Binance");
                trader.setTrades((ArrayList<Trade>) inputStream.readObject());
                trader.setBuys((ArrayList<MoneyTrade>) inputStream.readObject());
                trader.setSells((ArrayList<MoneyTrade>) inputStream.readObject());

                client.setTrader(trader);

                updateViews();

                inputStream.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *  saves wallet and trader data to device
     */
    public void saveData(){
        File file = new File(getDir("data", Context.MODE_PRIVATE), "cryptojoint");
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
            outputStream.writeObject(trader.getTrades());
            outputStream.writeObject(trader.getBuys());
            outputStream.writeObject(trader.getSells());
            outputStream.flush();
            outputStream.close();
            System.out.println("data saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("not saved");
    }

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
