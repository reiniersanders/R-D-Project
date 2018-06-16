package com.example.wout.cryptojoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 *
 * @author Laurens
 */
public class APIBinance implements API {
    private String pair;
    private String url;

    public APIBinance(String pair, String url){
        this.pair = pair;
        this.url = url;
    }

    //TODO Make sure the return is correcty formatted
    @Override
    public ArrayList<CurrencyTuple> getSymbols() throws MalformedURLException, IOException{
        String symbolsUrl = "https://api.binance.com/api/v1/ticker/24hr";
        String charset = "UTF-8";
        String symbol = this.pair;
        URLConnection connection = new URL(symbolsUrl).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream stream = connection.getInputStream();
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = stream.read(buffer)) > 0) {
            responseBody.write(buffer, 0, bytesRead);
        }
        String responseString = responseBody.toString();
        int position = 0;
        ArrayList<CurrencyTuple> toReturn = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            position = responseString.indexOf("symbol", position + 6);
            String symbols = responseString.substring(position + 9, position + 15);
            String symbolOwned = symbols.substring(0, 3);
            String symbolNotOwned = symbols.substring(3, 6);
            if (responseString.substring(position+9, position + 16).contains("\"")
                    || responseString.substring(position+9, position + 16).contains("USD")){
                if (symbolOwned.contains("USD")) {
                    symbolOwned = symbolOwned.concat("T");
                }
                if (symbolNotOwned.contains("USD")) {
                    symbolOwned = symbolNotOwned.concat("T");
                }
                Currency CurrencyOwned = new Currency(symbolOwned, 0.0);
                Currency CurrencyNotOwned = new Currency(symbolNotOwned, 0.0);
                CurrencyTuple tuple = new CurrencyTuple(CurrencyOwned, CurrencyNotOwned);
                System.out.println(CurrencyOwned.getName() + " - " + CurrencyNotOwned.getName());
                toReturn.add(tuple);
            }
        }
        return toReturn;
    }

    //Makes a Get request for a price
    @Override
    public Double makeCall() throws UnsupportedEncodingException, MalformedURLException, IOException{
        String charset = "UTF-8";
        String symbol = this.pair;
        String query = String.format("?symbol=%s",
                URLEncoder.encode(symbol, charset));
        URLConnection connection = new URL(this.url + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream stream = connection.getInputStream();
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = stream.read(buffer)) > 0) {
            responseBody.write(buffer, 0, bytesRead);
        }
        String responseString = responseBody.toString();
        int index = responseString.indexOf(","); //a response only contains 1 comma
        return Double.parseDouble(responseString.substring(index + 10, responseString.lastIndexOf('"')));
    }
}