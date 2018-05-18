/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptojoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author Laurens
 */
public class API {
    private String pair;
    private String url;
    
    public API(String pair, String url){
        this.pair = pair;
        this.url = url;
    }
    
    public String getPair(){
        return this.pair;
    }
    
    //Makes a Get request
    public String makeCall() throws UnsupportedEncodingException, MalformedURLException, IOException{
        String charset = "UTF-8";
        String symbol = this.pair;

        String query = String.format("symbol=%s", 
        URLEncoder.encode(symbol, charset));
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();
        return response.toString();
    } 
}
