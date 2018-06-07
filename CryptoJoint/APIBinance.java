/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

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
    public ArrayList<CurrencyTuple> getSymbols() throws MalformedURLException, IOException{
        String symbolsUrl = "https://api.binance.com/api/v1/ticker/24hr";
        String charset = "UTF-8";
        String symbol = this.pair;
        URLConnection connection = new URL(symbolsUrl).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream stream = connection.getInputStream();
        
        return null;
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
